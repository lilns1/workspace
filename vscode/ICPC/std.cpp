// #pragma GCC optimize(2)
#include<bits/stdc++.h>
#define xx first
#define yy second
// #define ls u * 2
// #define rs u * 2 + 1
// #define int long long
using namespace std;
using ll = long long;
using ld = long double;
using PII = pair<int, int>;
const int N = 207, M = N * N;
const int inf = 0x3f3f3f3f;
const ll INF = 1e17;
const double eps = 1e-8;
const ll mod = 1e9+7;

int n, m, match[M];
double t1, t2, v;
PII en[N], tw[N];
bool vis[M];
vector<int> g[N];

double dis(const PII &a, const PII &b) {
    return sqrt((a.xx - b.xx) * (a.xx - b.xx) + (a.yy - b.yy) * (a.yy - b.yy));
}

bool find(int x) {
    for (const auto &y : g[x]) {
        if (vis[y]) continue;
        vis[y] = 1;
        int t = match[y];
        if (t == -1 || find(t)) {
            match[y] = x;
            return true;
        }
    }
    return false;
} 

bool check(double mid) {
    for (int i = 1; i <= m; i ++) g[i].clear();

    for (int i = 1; i <= m; i ++) {
        for (int j = 1; j <= n; j ++) {
            double p = t1 + t2, t = t1 + dis(en[i], tw[j]) / v;
            int idx = 0;
            while (t < mid && idx < m) {
                idx ++;
                g[i].push_back((j - 1) * m + idx);
                t += p;
            }
        }
    }
    memset(match, -1, sizeof match);
    for (int i = 1; i <= m; i ++) {
        memset(vis, 0, sizeof vis);
        if (!find(i)) return false;
    }
    return true;
}

void solve() {
    cin >> n >> m >> t1 >> t2 >> v;
    t1 /= 60;
    for (int i = 1; i <= m; i ++) {
        int x, y;
        cin >> x >> y;
        en[i] = {x, y};
    }
    for (int i = 1; i <= n; i ++) {
        int x, y;
        cin >> x >> y;
        tw[i] = {x, y};
    }
    double l = t1, r = 1e5;
    while (r - l > eps) {
        double mid = (l + r) / 2;
        if (check(mid)) r = mid;
        else l = mid;
    }
    cout << fixed << setprecision(6) << l << '\n';
}


signed main()
{
    ios::sync_with_stdio(0), cin.tie(0);
    cout.tie(0);
    int tt = 1;
    // cin >> tt;
    while(tt --) solve();
    return 0;
}
