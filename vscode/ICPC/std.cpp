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
using PII = pair<int, ll>;
const int N = 1e5+7, M = 5e4+7;
const int inf = 0x3f3f3f3f;
const ll INF = 1e17;
const int eps = 1e-8;
const ll mod = 1e9+7;

int n, c[N], p[N], k[N], in[N], rt;
ll f[N][3];
vector<int> g[N];

void dfs(int x) {
    vector <int> dif; // 存儿子白-黑
    ll sum = 0, cw, cb;
    if (c[x] == 0) cw = 0, cb = p[x];
    else cw = p[x], cb = 0;
    for (const auto &y : g[x]) {
        dfs(y);
        k[x] += k[y];
        if (k[y] & 1) {
            sum += f[y][2];
        } else {
            sum += f[y][1];
            dif.push_back(f[y][0] - f[y][1]);
        }
    }
    sort(dif.begin(), dif.end());
    int sz = dif.size();
    if (!sz) {
        f[x][0] = sum + cw;
        f[x][1] = sum + cb;
        return;
    }
    if (sz & 1) {
        int mid = sz / 2;
        for (int i = 0; i < mid; i ++) {
            sum += dif[i];
        }
        f[x][2] = min(sum + cw, sum + dif[mid] + cb);
    } else {
        int mid = sz / 2;
        for (int i = 0; i < mid; i ++) {
            sum += dif[i];
        }
        f[x][0] = min(sum + cw, sum + dif[mid] + cb);
        f[x][1] = min(sum + cb, sum - dif[mid - 1] + cw);
    }
}

void solve() {
    memset(f, 0x3f, sizeof f);
    cin >> n;
    for (int i = 1; i <= n; i ++) {
        cin >> c[i] >> p[i] >> k[i];
        for (int j = 1; j <= k[i]; j ++) {
            int s;
            cin >> s;
            g[i].push_back(s);
            in[s] ++;
        }
    }
    for (int i = 1; i <= n; i ++) if (!in[i]) {
        rt = i;
        break;
    }
    dfs(rt);
    cout << min(f[rt][0], min(f[rt][1], f[rt][2])) << '\n';
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