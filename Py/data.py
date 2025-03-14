import matplotlib.pyplot as plt

# Data
years = [2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023]
values1 = [71830777, 75068444, 79294933, 82318420, 92094125, 103225389, 62723855, 78383383, 119520965, 134631332]  # Dollars
values2 = [961000, 983000, 1015000, 1072000, 1151000, 1306000, 943576, 117000, 1167000, 1670000]  # Number of people

# Create figure and primary axis
fig, ax1 = plt.subplots(figsize=(12, 6))

# Plot Value 1 (Dollars) on the primary y-axis
ax1.plot(years, values1, color='blue', marker='o', linestyle='-', linewidth=2, label='Revenue (USD)')
ax1.set_xlabel('Year', fontsize=12, fontweight='bold')
ax1.set_ylabel('Revenue (USD)', fontsize=12, fontweight='bold', color='blue')
ax1.tick_params(axis='y', labelcolor='blue', labelsize=10)

# Set y-axis limits for ax1 (Revenue)
ax1.set_ylim(0, max(values1) * 1.1)
ax1.set_yticks(range(0, int(max(values1) * 1.1) + 1, int(max(values1) * 1.1 / 5)))

# Create a secondary y-axis for Value 2 (Number of people)
ax2 = ax1.twinx()
ax2.plot(years, values2, color='red', marker='s', linestyle='--', linewidth=2, label='Number of People')
ax2.set_ylabel('Number of People', fontsize=12, fontweight='bold', color='red')
ax2.tick_params(axis='y', labelcolor='red', labelsize=10)

# Set y-axis limits for ax2 (Number of People)
ax2.set_ylim(0, max(values2) * 1.1)
ax2.set_yticks(range(0, int(max(values2) * 1.1) + 1, int(max(values2) * 1.1 / 5)))


# Combine legends from both axes
lines1, labels1 = ax1.get_legend_handles_labels()
lines2, labels2 = ax2.get_legend_handles_labels()
ax1.legend(
    lines1 + lines2,
    labels1 + labels2,
    loc='upper left',
    fontsize=12,  # 增大图例字体大小
    frameon=True,  # 添加边框
    framealpha=0.8,  # 边框透明度
    edgecolor='black'  # 边框颜色
)

# Add grid
ax1.grid(True, linestyle='--', alpha=0.6)

# Show plot
plt.tight_layout()
plt.show()