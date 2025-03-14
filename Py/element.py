import numpy as np
import matplotlib.pyplot as plt

n = 945000
v = 2.41
m = 0.8531
j = 71700688.1942
base = 1500
scale = np.linspace(base * 0.5, base * 5, 100)

def vp(x):
    return 1 / (1 + (x / 2000) * v)

def mp(x):
    return m + 0.3 * (x / (x + 1000))

fig, ax1 = plt.subplots(figsize=(10, 6))

# ------------------------- 左侧坐标轴：v -------------------------
color_vp = "tab:blue"
ax1.set_xlabel('I', fontsize=16)
ax1.set_ylabel('EC', fontsize=16, color=color_vp)
ax1.plot(scale, vp(scale), color=color_vp, linewidth=2, label="impact of I on EC")
ax1.tick_params(axis='y', labelcolor=color_vp, labelsize=16)

# ------------------------- 右侧坐标轴：m -------------------------
ax2 = ax1.twinx()
color_m = "tab:red"
ax2.set_ylabel('S', fontsize=16, color=color_m)
ax2.plot(scale, mp(scale), color=color_m, linewidth=2, linestyle='--', label="impact of I on S")
ax2.tick_params(axis='y', labelcolor=color_m, labelsize=16)

# ------------------------- 图表美化 -------------------------

# ------------------------- 图表美化 -------------------------

# 合并图例
lines, labels = ax1.get_legend_handles_labels()
lines2, labels2 = ax2.get_legend_handles_labels()

# 关键修改：将图例放在图表内部的右下角
plt.legend(
    lines + lines2,
    labels + labels2,
    loc='lower right',          # 定位到右下角
    bbox_to_anchor=(0.95, 0.1), # 微调位置（基于轴坐标系，x: 0~1, y: 0~1）
    ncol=1,                     # 单列显示
    fontsize=12,                # 调整字体大小
    frameon=True,               # 显示边框
    framealpha=0.8              # 边框透明度
)

ax1.grid(True, linestyle="--", alpha=0.5)
fig.tight_layout()
plt.show()