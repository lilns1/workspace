import matplotlib.pyplot as plt
import pandas as pd

# 数据
data = {
    "Year": [2017, 2018, 2019, 2020, 2021, 2022, 2023],
    "Average Income (USD)": [65221, 68368, 69918, 70536, 74274, 78743, 82234],
    "Average Listing Price (USD)": [403176.58, 392005.5, 404645.08, 436164.67, 459956.17, 510217.92, 485886.92],
    "Housing Price Change (Index)": [1.0, 1.0237, 1.0329, 1.2139, 1.2956, 1.1877, 1.2869],
    "Unemployment Rate (%)": [4.3167, 4.0583, 4.0917, 7.0667, 4.7917, 2.9083, 2.9917]
}
df = pd.DataFrame(data)

# 创建子图
fig, axes = plt.subplots(4, 1, figsize=(12, 12), sharex=True)

# 自定义颜色和线型
colors = ["#1f77b4", "#2ca02c", "#d62728", "#9467bd"]
linestyles = ["-", "--", ":", "-."]
markers = ["o", "s", "^", "D"]

# ------------------------- 绘制每个子图 -------------------------
for i, (col, ax) in enumerate(zip(df.columns[1:], axes)):
    # 绘制曲线并添加标签
    line = ax.plot(
        df["Year"], df[col],
        color=colors[i],
        linestyle=linestyles[i],
        marker=markers[i],
        linewidth=2,
        markersize=8,
        markeredgecolor="white",
        label=col  # 直接使用列名作为标签
    )

    # 设置坐标轴标签
    ax.set_ylabel(col, fontsize=12, fontweight="bold", color=colors[i])
    ax.tick_params(axis='y', labelsize=10, colors=colors[i])

    # 添加左上角图例（直接使用列名）
    ax.legend(
        loc='upper left',  # 左上角
        fontsize=10,  # 字体大小
        frameon=True,  # 显示边框
        framealpha=0.8,  # 边框透明度
        edgecolor='black'  # 边框颜色
    )

    # 网格和背景
    ax.grid(True, linestyle="--", alpha=0.6, color="#dddddd")
    ax.set_facecolor("#f8f8f8")  # 浅灰色背景

axes[-1].set_xlabel("Year", fontsize=12, fontweight="bold")
plt.xticks(df["Year"], fontsize=10)
plt.tight_layout()
plt.subplots_adjust(hspace=0.3)  # 调整子图间距
plt.show()