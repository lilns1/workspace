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
const int N = 5e4+7, M = 3e5+7;
const int inf = 0x3f3f3f3f;
const ll INF = 1e17;
const int eps = 1e-8;
const ll mod = 1e9+7;

int n, s[N];
vector<PII> g[N];


signed main()
{
    ios::sync_with_stdio(0), cin.tie(0);
    cout.tie(0);
    int tt = 1;
    // cin >> tt;
    while(tt --) solve();
    return 0;
}