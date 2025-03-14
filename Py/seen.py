import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches

# ====================== Global Settings ======================
F1_COEFFS = [-45.668, 3.4943]
COEFF_F2 = [0.3552, 1e-6, -5.007e-13]
F3_COEFFS = [1.1648, 2.06147]

ORIGINAL_XL = 5e5
ORIGINAL_XU = 1.5e6
N_TESTS = 50
SAMPLES_PER_TEST = 10

plt.rcParams['font.sans-serif'] = ['SimHei']
plt.rcParams['axes.unicode_minus'] = False


# ====================== Perturbation Test ======================
def run_perturbation_tests():
    np.random.seed(42)
    all_results = {'x': [], 'f1': [], 'f2': [], 'f3': []}

    for _ in range(N_TESTS):
        xl = ORIGINAL_XL * np.random.uniform(0.95, 1.05)
        xu = ORIGINAL_XU * np.random.uniform(0.95, 1.05)
        xl, xu = sorted([xl, xu])

        p_f1 = [c * np.random.uniform(0.99, 1.01) for c in F1_COEFFS]
        p_f2 = [c * np.random.uniform(0.95, 1.05) for c in COEFF_F2]
        p_f3 = [c * np.random.uniform(0.99, 1.01) for c in F3_COEFFS]

        x_samples = np.random.uniform(xl, xu, SAMPLES_PER_TEST)

        all_results['x'].extend(x_samples)
        all_results['f1'].extend(p_f1[0] + p_f1[1] * np.log(x_samples + 1))
        all_results['f2'].extend(p_f2[0] + p_f2[1] * x_samples + p_f2[2] * (x_samples ** 2))
        all_results['f3'].extend(np.exp(p_f3[0] * np.log(x_samples) + p_f3[1]))

    return all_results


# ====================== Visualization ======================
def plot_results(results):
    fig, axs = plt.subplots(1, 3, figsize=(18, 5))
    x_line = np.linspace(1e5, 3e6, 100)

    # Scatter plot parameters
    scatter_params = {
        's': 4,
        'alpha': 0.3,
        'color': 'r',
        'label': 'Perturbed Samples'  # Modified label
    }
    band_alpha = 0.2

    # ===== Objective 1 =====
    ax = axs[0]
    line_f1, = ax.plot(x_line, F1_COEFFS[0] + F1_COEFFS[1] * np.log(x_line + 1),
                       'b-', lw=1, label='Original Function')
    scatter_f1 = ax.scatter(results['x'], results['f1'], **scatter_params)
    plot_confidence_band(ax, results['x'], results['f1'], 'blue', band_alpha)
    conf_patch_f1 = mpatches.Patch(color='blue', alpha=band_alpha, label='95% Confidence Band')
    ax.legend(handles=[line_f1, scatter_f1, conf_patch_f1], loc='lower right')
    ax.set_ylabel('V', rotation=0, labelpad=1)
    ax.title.set_text('V')
    ax.grid(alpha=0.3)

    # ===== Objective 2 =====
    ax = axs[1]
    line_f2, = ax.plot(x_line, COEFF_F2[0] + COEFF_F2[1] * x_line + COEFF_F2[2] * (x_line ** 2),
                       'g-', lw=1, label='Original Function')
    scatter_f2 = ax.scatter(results['x'], results['f2'], **scatter_params)
    plot_confidence_band(ax, results['x'], results['f2'], 'green', band_alpha)
    conf_patch_f2 = mpatches.Patch(color='green', alpha=band_alpha, label='95% Confidence Band')
    ax.legend(handles=[line_f2, scatter_f2, conf_patch_f2], loc='lower right')
    ax.title.set_text('S')
    ax.grid(alpha=0.3)

    # ===== Objective 3 =====
    ax = axs[2]
    line_f3, = ax.plot(x_line, np.exp(F3_COEFFS[0] * np.log(x_line) + F3_COEFFS[1]),
                       'm-', lw=1, label='Original Function')
    scatter_f3 = ax.scatter(results['x'], results['f3'], **scatter_params)
    plot_confidence_band(ax, results['x'], results['f3'], 'purple', band_alpha)
    conf_patch_f3 = mpatches.Patch(color='purple', alpha=band_alpha, label='95% Confidence Band')
    ax.legend(handles=[line_f3, scatter_f3, conf_patch_f3], loc='lower right')
    ax.title.set_text('E')
    ax.grid(alpha=0.3)

    # Add common X label
    fig.text(0.5, 0.04, 'I (Investment)', ha='center', va='center', fontsize=12)  # Modified label

    plt.tight_layout()
    plt.subplots_adjust(bottom=0.15)
    plt.show()


def plot_confidence_band(ax, x, y, color, alpha):
    """Plot 95% confidence band"""
    sorted_idx = np.argsort(x)
    x_sorted = np.array(x)[sorted_idx]
    y_sorted = np.array(y)[sorted_idx]

    window_size = 50
    x_win, lower, upper = [], [], []
    for i in range(0, len(x_sorted), window_size // 2):
        if i + window_size > len(x_sorted):
            break
        window = y_sorted[i:i + window_size]
        x_win.append(np.median(x_sorted[i:i + window_size]))
        lower.append(np.percentile(window, 7.5))
        upper.append(np.percentile(window, 92.5))

    ax.fill_between(x_win, lower, upper, color=color, alpha=alpha)


# ====================== Execution ======================
if __name__ == "__main__":
    test_results = run_perturbation_tests()
    plot_results(test_results)