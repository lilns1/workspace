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
const int N = 5e4+7, M = 5e4+7;
const int inf = 0x3f3f3f3f;
const ll INF = 1e17;
const int eps = 1e-8;
const ll mod = 1e9+7;

int n, m, dfn[N], low[N], idx, c[N], cnt, ins[N], top, flag, sz[N];
int dis[N], vis[N];
PII st[N];
int head[N], ver[M], nxt[M], wt[M], tot, from[M];
vector<PII> g[N];
priority_queue<PII> q;



void add(int x, int y, int w) {
    nxt[++ tot] = head[x];
    head[x] = tot;
    ver[tot] = y;
    wt[tot] = w;
    from[tot] = x;
} 

void tarjan(int x, int eid) 
{
    dfn[x] = low[x] = ++ idx;
    st[++ top] = make_pair(x, wt[eid]);
    ins[x] = 1;
    for (int i = head[x]; i; i = nxt[i]) {
        int y = ver[i], w = wt[i];
        if (!dfn[y]) {
            tarjan(y, i);
            low[x] = min(low[x], low[y]);
        } else if (ins[y]) low[x] = min(low[x], dfn[y]);
    }
    if (dfn[x] == low[x]) {
        cnt ++;
        int z, w;
        do {
            z = st[top].xx, w = st[top --].yy;
            if (w) flag = 1;
            ins[z] = 0;
            c[z] = cnt;
            sz[cnt] ++;
        } while(z != x);
    }
}

void solve()
{
    cin >> n >> m;
    for (int i = 1; i <= m; i ++) {
        int t, a, b;
        cin >> t >> a >> b;
        if (t == 1) {
            add(a, b, 0);
            add(b, a, 0);
        }
        if (t == 2) {
            add(a, b, 1);
        } // d[b] - d[a] >= 1;
        if (t == 3) {
            add(b, a, 0);
        } // d[a] - d[b] >= 0;
        if (t == 4) {
            add(b, a, 1);
        } // d[a] - d[b] >= 1;
        if (t == 5) {
            add(a, b, 0);
        }  // d[b] - d[a] >= 0;
    }
    for (int i = 1; i <= n; i ++) if (!dfn[i]) {
        tarjan(i, 0);
    }
    if (flag) {
        cout << "-1\n";
        return;
    }
    for (int i = 1; i <= cnt; i ++) {
        g[0].push_back({i, 1});
    }
    for (int i = 1; i <= tot; i ++) {
        int x = from[i], y = ver[i], w = wt[i];
        if (c[x] != c[y]) g[c[x]].push_back({c[y], w});
    }
    memset(dis, -0x3f, sizeof dis);
    dis[0] = 0;
    q.push({dis[0], 0});
    while (q.size()) {
        int x = q.top().yy;
        q.pop();
        if (vis[x]) continue;
        vis[x] = 1;
        for (const auto &[y, w] : g[x]) {
            if (dis[x] + w > dis[y]) {
                dis[y] = dis[x] + 1;
                q.push({dis[y], y});
            }
        }
    }
    ll ans = 0;
    for (int i = 1; i <= n; i ++) {
        ans += (ll)sz[i] * dis[i];
    }
    cout << ans << '\n';
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