import numpy as np
import matplotlib.pyplot as plt
from pymoo.algorithms.moo.nsga2 import NSGA2
from pymoo.core.problem import Problem
from pymoo.optimize import minimize
from pymoo.termination import get_termination
from pymoo.indicators.hv import Hypervolume
from mpl_toolkits.mplot3d import Axes3D

# ====================== 全局系数配置 ======================
ORIGINAL_F1 = [-45.668, 3.4943]
ORIGINAL_F2 = [0.3552, 1e-6, -5.007e-13]
ORIGINAL_F3 = [1.1648, 2.06147]
ORIGINAL_XL = 5e5
ORIGINAL_XU = 1.5e6

plt.rcParams['font.sans-serif'] = ['SimHei']
plt.rcParams['axes.unicode_minus'] = False

plt.rcParams['font.family'] = 'Roboto'
plt.rcParams['axes.titlesize'] = 16
plt.rcParams['axes.titleweight'] = 'bold'


# ====================== 扰动函数 ======================
def perturb_coeffs(coeffs):
    return [c * np.random.uniform(0.95, 1.05) for c in coeffs]


def perturb_bounds(xl, xu):
    new_xl = xl * np.random.uniform(0.95, 1.05)
    new_xu = xu * np.random.uniform(0.95, 1.05)
    return np.array([min(new_xl, new_xu)]), np.array([max(new_xl, new_xu)])


# ====================== 问题定义 ======================
class CustomProblem(Problem):
    def __init__(self, F1, F2, F3, xl, xu):
        super().__init__(n_var=1, n_obj=3, n_constr=0, xl=xl, xu=xu)
        self.F1 = F1
        self.F2 = F2
        self.F3 = F3

    def _evaluate(self, X, out, *args, **kwargs):
        x = X[:, 0]
        f1 = self.F1[0] + self.F1[1] * np.log(x + 1)
        f2 = -(self.F2[0] + self.F2[1] * x + self.F2[2] * (x ** 2))  # 转换为最小化
        f3 = -np.exp(self.F3[0] * np.log(x) + self.F3[1])  # 转换为最小化
        out["F"] = np.column_stack([f1, f2, f3])


# ====================== 实验配置 ======================
algorithm = NSGA2(pop_size=100, crossover_prob=0.8, mutation_prob=0.3)
n_experiments = 100
all_F = []

# ====================== 执行实验 ======================
for _ in range(n_experiments):
    # 生成扰动参数
    F1 = perturb_coeffs(ORIGINAL_F1)
    F2 = perturb_coeffs(ORIGINAL_F2)
    F3 = perturb_coeffs(ORIGINAL_F3)
    xl, xu = perturb_bounds(ORIGINAL_XL, ORIGINAL_XU)

    # 运行优化
    problem = CustomProblem(F1, F2, F3, xl, xu)
    res = minimize(problem, algorithm, get_termination("n_gen", 50), verbose=False)

    # 收集解集并还原目标方向
    F_original = np.column_stack([
        res.F[:, 0],
        -res.F[:, 1],  # 还原最大化目标
        -res.F[:, 2]  # 还原最大化目标
    ])
    all_F.append(F_original)

# ====================== 结果可视化 ======================
# 合并所有解集
all_F_combined = np.concatenate(all_F)

# 三维散点图
fig = plt.figure(figsize=(12, 8))
ax = fig.add_subplot(111, projection='3d')
sc = ax.scatter(all_F_combined[:, 0], all_F_combined[:, 1], all_F_combined[:, 2],
                c=all_F_combined[:, 0], cmap='viridis', alpha=0.6)
ax.set_xlabel('V', fontsize=16)
ax.set_ylabel('S', fontsize=16)
ax.set_zlabel('E', fontsize=16)
plt.colorbar(sc, label='f1 Value')
plt.title("Pareto Solutions from 100 Experiments", fontsize=14)
plt.show()

# ====================== 覆盖率计算 ======================
# 计算超体积指标
ref_point = [
    all_F_combined[:, 0].max() * 1.1,  # f1的最差值
    all_F_combined[:, 1].min() * 0.9,  # f2的最差值
    all_F_combined[:, 2].min() * 0.9  # f3的最差值
]

ind = Hypervolume(ref_point=ref_point)
hv_values = [ind.do(F) for F in all_F]

print(f"Average Hypervolume: {np.mean(hv_values):.2e}")
print(f"Hypervolume Range: [{np.min(hv_values):.2e}, {np.max(hv_values):.2e}]")

# 覆盖率可视化
plt.figure(figsize=(8, 5))
plt.hist(hv_values, bins=20, edgecolor='k', alpha=0.7)
plt.xlabel('Hypervolume Value', fontsize=12)
plt.ylabel('Frequency', fontsize=12)
plt.title('Hypervolume Distribution Across 100 Experiments', fontsize=14)
plt.grid(alpha=0.3)
plt.show()