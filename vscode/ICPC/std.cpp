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
const int N = 5e4+7, M = 5e4+7;
const int inf = 0x3f3f3f3f;
const ll INF = 1e17;
const int eps = 1e-8;
const ll mod = 1e9+7;

int n, dfn[N], low[N], idx, c[N], ins[N], cnt, in[N];
vector<int> g[N], ng[N];
stack<int> st;

void tarjan(int x) {
    dfn[x] = low[x] = ++ idx;
    st.push(x);
    ins[x] = 1;
    for (const auto &y : g[x]) {
        if (!dfn[y]) {
            tarjan(y);
            low[x] = min(low[x], low[y]);
        } else if (ins[y])
            low[x] = min(low[x], dfn[y]);
    }
    if (dfn[x] == low[x]) {
        cnt ++;
        int z;
        do {
            z = st.top();
            c[z] = cnt;
            ins[z] = 0;
            st.pop();
        } while (z != x);
    }
}

void solve() {
    cin >> n;
    for (int i = 1; i <= n; i ++) {
        int v;
        while (cin >> v, v) {
            g[i].push_back(v);
        }
    }
    for (int i = 1; i <= n; i ++) if (!dfn[i]) {
        tarjan(i);
    }
    for (int x = 1; x <= n; x ++) 
        for (const auto &y : g[x]) {
            if (c[x] != c[y]) ng[c[x]].push_back(c[y]), in[c[y]] ++;
        }
    int ans1 = 0, ans2 = 0;
    for (int i = 1; i <= cnt; i ++) {
        if (!in[i]) ans1 ++;
        if (ng[i].size() == 0) ans2 ++;
    }
    cout << ans1 << '\n';
    if (cnt != 1) {
        cout << max(ans1, ans2) << '\n';
    } else cout << "0\n";
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