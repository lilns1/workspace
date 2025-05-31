#include <iostream>
#include <cstdio>
#include <cstring>
#include <algorithm>
#include <vector>
#include <cmath>
using namespace std;
using ll = long long;

const int N = 1e5 + 7;

int n, m, k;
int w[N], len;

struct Query {
    int id, l, r;
    ll res;
}q[N];

struct Change {
    int id, l, r, t;
};
vector<Change> change[N];

ll ans[N];
int f[N], g[N];

int count_bin(int x) {
    int res = 0;
    while (x) res += x & 1, x >>= 1;
    return res;
}

int get(int x) {
    return x / len;
}

int main() {
    ios::sync_with_stdio(0), cin.tie(0), cout.tie(0);

    cin >> n >> m >> k;
    vector<int> num;
    for (int i = 0; i < (1 << 14); i ++) 
        if (count_bin(i) == k) num.push_back(i);
    
    len = sqrt(n);

    for (int i = 1; i <= n; i ++) cin >> w[i];

    for (int i = 1; i <= n; i ++) {
        for (int y : num) g[y ^ w[i]] ++;
        f[i] = g[w[i + 1]];
    }

    for (int i = 1; i <= m; i ++) {
        int l, r;
        cin >> l >> r;
        q[i] = {i, l, r, 0};
    }
    
    sort(q + 1, q + 1 + m, [](const Query &a, const Query &b) {
        int i = get(a.l), j = get(b.l);
        if (i != j) return i < j;
        return a.r < b.r;
    });

    for (int i = 1, L = 1, R = 0; i <= m; i ++) {
        int l = q[i].l, r = q[i].r;
        ll &res = q[i].res;
        if (R < r) change[L - 1].push_back({i, R + 1, r, -1});
        while (R < r) res += f[R ++];
        if (R > r) change[L - 1].push_back({i, r + 1, R, 1});
        while (R > r) res -= f[-- R];
        if (L < l) change[R].push_back({i, L, l - 1, -1});  
        while (L < l) res += f[L - 1] + !k, L ++;
        if (L > l) change[R].push_back({i, l, L - 1, 1});
        while (L > l) res -= f[L - 2] + !k, L --;
    }

    memset(g, 0, sizeof g);
    for (int i = 1; i <= n; i ++) {
        for (int y : num) g[w[i] ^ y] ++;

        for (auto &rg : change[i]) {
            int id = rg.id, l = rg.l, r = rg.r, t = rg.t;
            for (int j = l; j <= r; j ++)
                q[id].res += g[w[j]] * t; 
        }
    }

    for (int i = 2; i <= m; i ++) q[i].res += q[i - 1].res;
    for (int i = 1; i <= m; i ++) ans[q[i].id] = q[i].res;
    for (int i = 1; i <= m; i ++) cout << ans[i] << '\n';

    return 0;
}