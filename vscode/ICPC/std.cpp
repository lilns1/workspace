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
const int N = 107, M = N * N;
const int inf = 0x3f3f3f3f;
const ll INF = 1e17;
const double eps = 1e-10;
const ll mod = 1e9+7;

int n;
double w[N][N], la[N], lb[N], upd[N];
bool va[N], vb[N];
int match[N];
int last[N];
struct {
    int x, y;
}a[N], b[N];

bool dfs(int x, int fa) {
    va[x] = 1;
    for (int y = 1; y <= n; y ++) 
        if (!vb[y])
            if (fabs(la[x] + lb[y] - w[x][y]) < eps) {
                vb[y] = 1; last[y] = fa;
                if (!match[y] || dfs(match[y], y)) {
                    match[y] = x;
                    return true;
                }
            } else if (upd[y] > la[x] + lb[y] - w[x][y] + eps) {
                upd[y] = la[x] + lb[y] - w[x][y];
                last[y] = fa;   
            }
            return false;
}

void KM() {
    for (int i = 1; i <= n; i ++) {
        la[i] = -1e100;
        lb[i] = 0;
        for (int j = 1; j <= n; j ++) {
            la[i] = max(la[i], w[i][j]);
        }
    }

    for (int i = 1; i <= n; i ++) {
        memset(va, 0, sizeof va);
        memset(vb, 0, sizeof vb)    ;
        for (int j = 1; j <= n; j ++) upd[j] = 1e10;
        int st = 0; match[0] = i;
        while (match[st]) {
            double delta = 1e10;
            if (dfs(match[st], st)) break;
            for (int j = 1; j <= n; j ++) 
                if (!vb[j] && delta > upd[j]) {
                    delta = upd[j];
                    st = j;
                }
            for (int j = 1; j <= n; j ++) {
                if (va[j]) la[j] -= delta;
                if (vb[j]) lb[j] += delta; else upd[j] -= delta;
            }
            vb[st] = true;
        }
        while (st) {
            match[st] = match[last[st]];
            st = last[st];
        }
    }
}

void solve() {
    cin >> n;
    for (int i = 1; i <= n; i ++) {
        cin >> b[i].x >> b[i].y;
    }
    for (int i = 1; i <= n; i ++) {
        cin >> a[i].x >> a[i].y;
    }
    for (int i = 1; i <= n; i ++) {
        for (int j = 1; j <= n; j ++) {
            w[i][j] = -sqrt((a[i].x - b[j].x) * (a[i].x - b[j].x) + (a[i].y - b[j].y) * (a[i].y - b[j].y));
        }
    }
    KM();
    for (int i = 1; i <= n; i ++) {
        cout << match[i] << '\n';
    }
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
