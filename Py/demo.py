import numpy as np
import matplotlib.pyplot as plt
from pymoo.algorithms.moo.nsga2 import NSGA2
from pymoo.core.problem import Problem
from pymoo.optimize import minimize
from pymoo.termination import get_termination

# ====================== 全局系数配置 ======================
F1_COEFFS = [-45.668, 3.4943]
COEFF_F2 = [0.3552, 1e-6, -5.007e-13]
F3_COEFFS = [1.1648, 2.06147]

F1_COEFFS = [x * 0.998125 for x in F1_COEFFS]
COEFF_F2[0] += 0.3

plt.rcParams['font.sans-serif'] = ['SimHei']
plt.rcParams['axes.unicode_minus'] = False
# ====================== 问题定义 ======================
class CustomProblem(Problem):
    def __init__(self):
        """三目标优化问题定义（1最小化+2最大化）"""
        super().__init__(
            n_var=1,
            n_obj=3,
            n_constr=0,
            xl=np.array([7.68e5]),
            xu=np.array([1.278e6])  # 注意这里上限改为150万
        )

    def _evaluate(self, X, out, *args, **kwargs):
        x = X[:, 0]

        # 目标1：最小化（保持原式）
        f1 = F1_COEFFS[0] + F1_COEFFS[1] * np.log(x + 1)

        # 目标2：最大化原式 → 转换为最小化问题
        f2_original = COEFF_F2[0] + COEFF_F2[1] * x + COEFF_F2[2] * (x ** 2)
        f2 = -f2_original  # 关键修改

        # 目标3：最大化原式 → 转换为最小化问题
        f3_original = np.exp(F3_COEFFS[0] * np.log(x) + F3_COEFFS[1])
        f3 = -f3_original  # 关键修改

        out["F"] = np.column_stack([f1, f2, f3])


# ====================== 算法配置 ======================
algorithm = NSGA2(
    pop_size=100,
    crossover_prob=0.8,
    mutation_prob=0.3,  # 增大变异概率
    eliminate_duplicates=True
)

# ====================== 优化执行 ======================
problem = CustomProblem()
res = minimize(
    problem,
    algorithm,
    get_termination("n_gen", 100),  # 增加代数
    seed=42,
    verbose=True,
    save_history=False
)

# ====================== 可视化部分 ======================

plt.figure(figsize=(15, 5))
x_vals = np.linspace(1e5, 2e6, 200)  # 调整x范围

# 目标1可视化（最小化）
plt.subplot(131)
plt.plot(x_vals, F1_COEFFS[0] + F1_COEFFS[1] * np.log(x_vals + 1))
plt.scatter(res.X, res.F[:, 0], s=10, c='red', alpha=0.6)
plt.xlabel('N')
plt.ylabel('V')
plt.grid(alpha=0.3)
plt.legend()

# 目标2可视化（显示原始值，越大越好）
plt.subplot(132)
original_f2 = -res.F[:, 1]  # 恢复原始值
plt.plot(x_vals, COEFF_F2[0] + COEFF_F2[1] * x_vals + COEFF_F2[2] * (x_vals ** 2))
plt.scatter(res.X, original_f2, s=10, c='orange', alpha=0.6)
plt.xlabel('N')
plt.ylabel('S')
plt.grid(alpha=0.3)
plt.legend()

# 目标3可视化（显示原始值，越大越好）
plt.subplot(133)
original_f3 = -res.F[:, 2]  # 恢复原始值
plt.plot(x_vals, np.exp(F3_COEFFS[0] * np.log(x_vals) + F3_COEFFS[1]))
plt.scatter(res.X, original_f3, s=10, c='cyan', alpha=0.6)
plt.xlabel('N')
plt.ylabel('E')
plt.grid(alpha=0.3)
plt.legend()

plt.tight_layout()
plt.show()


# ====================== 结果分析 ======================
print("\n优化结果分析：")
print(f"变量范围：{res.X.min() / 1e4:.1f}万 至 {res.X.max() / 1e4:.1f}万")

print("\n各目标原始值范围：")
print(f"f₁（越小越好）: [{res.F[:, 0].min():.2f}, {res.F[:, 0].max():.2f}]")
print(f"f₂（越大越好）: [{original_f2.min():.4f}, {original_f2.max():.4f}]")
print(f"f₃（越大越好）: [{original_f3.min():.4f}, {original_f3.max():.4f}]")

typical_solutions = []

for sol_type, x in zip(['最小值解', '均值解', '最大值解'],
                       [res.X.min(axis=0),
                        res.X.mean(axis=0),
                        res.X.max(axis=0)]):
    x_val = x[0]
    f1 = F1_COEFFS[0] + F1_COEFFS[1] * np.log(x_val + 1)
    f2 = COEFF_F2[0] + COEFF_F2[1] * x_val + COEFF_F2[2] * x_val ** 2
    f3 = np.exp(F3_COEFFS[0] * np.log(x_val) + F3_COEFFS[1])
    composite = f2 * f3 / f1  # 综合指标

    # 存储到字典
    solution = {
        'type': sol_type,
        'x': x_val,
        'f1': f1,
        'f2': f2,
        'f3': f3,
        'composite': composite
    }
    typical_solutions.append(solution)

# ====================== 后续使用示例 ======================
# 示例1：生成Markdown表格
print("\n## 典型解分析")
print("| 解类型 | x(万) | f1 | f2 | f3 | 综合指标 |")
print("|--------|-------|----|----|----|----------|")
for sol in typical_solutions:
    print(f"| {sol['type']} | {sol['x'] / 1e4:.1f} | {sol['f1']:.2f} | "
          f"{sol['f2']:.4f} | {sol['f3']:.4f} | {sol['composite']:.4f} |")

from pymoo.visualization.scatter import Scatter

Scatter().add(res.F).show( )

