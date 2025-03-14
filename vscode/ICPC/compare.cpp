#include<cstdio>
#include<cstring>
#include<iostream>
#include<algorithm>
#include<queue>
using namespace std;

typedef long long LL;

const int N = 100010, M = 300010, INF = 0x3f3f3f3f;

int n, m;
struct Edge
{
    int a, b, w;
    bool used;
    bool operator< (const Edge &t) const
    {
        return w < t.w;
    }
}edge[M];
int p[N];
int h[N], e[M], w[M], ne[M], idx;
int depth[N], fa[N][17], d1[N][17], d2[N][17];//log2(1e5)=16  d1最大边 d2次大边

void add(int a,int b,int c)
{
    e[idx] = b,ne[idx] = h[a],w[idx] = c,h[a] = idx++;
}

int find(int x)
{
    if(x!=p[x])p[x]= find(p[x]);
    return p[x];
}

LL kruskal()
{
    for (int i = 1; i <= n; i ++ ) p[i] = i;
    sort(edge, edge + m);
    LL res = 0;
    for (int i = 0; i < m; i ++ )
    {
        int a = find(edge[i].a), b = find(edge[i].b), w = edge[i].w;
        if (a != b)
        {
            p[a] = b;
            res += w;
            edge[i].used = true;
        }
    }

    return res;
}

void build()
{
    memset(h,-1,sizeof h);
    for(int i = 0;i<m;i++)
    {
        if(edge[i].used)
        {
            int a = edge[i].a,b = edge[i].b,w = edge[i].w;
            add(a,b,w),add(b,a,w);
        }
    }
}

void bfs()
{
    memset(depth,0x3f,sizeof depth);
    depth[0] = 0,depth[1] = 1;//哨兵0 根节点1
    queue<int> q;
    q.push(1);
    while(q.size())
    {
        int t = q.front();
        q.pop();//日常漏
        for(int i = h[t];~i;i=ne[i])
        {
            int j = e[i];
            // j没有被遍历过
            if(depth[j]>depth[t]+1)
            {
                depth[j] = depth[t]+1;
                q.push(j);
                fa[j][0] = t;
                d1[j][0] = w[i],d2[j][0] = -INF;
                for(int k = 1;k<=16;k++)
                {
/*
                         →   →
                       o---o---o
                       j  anc  
        d1[i,k-1],d2[i,k-1]  d1[anc,k-1],d2[anc,k-1]
*/
                    int anc = fa[j][k - 1];
                    fa[j][k] = fa[anc][k - 1];
                    int distance[4] = {d1[j][k - 1], d2[j][k - 1], d1[anc][k - 1], d2[anc][k - 1]};
                    //初始化d1[j][k]和d2[j][k]
                    d1[j][k] = d2[j][k] = -INF;
                    for (int u = 0; u < 4; u ++ )
                    {
                        int d = distance[u];
                        // 更新最大值d1和次大值d2
                        if (d > d1[j][k]) d2[j][k] = d1[j][k], d1[j][k] = d;
                        // 严格次大值
                        else if (d != d1[j][k] && d > d2[j][k]) d2[j][k] = d;
                    }
                }
            }
        }
    }
}
// lca求出a, b之间的最大边权与次大边权
int lca(int a,int b,int w)
{
    static int distance[N * 2];
    int cnt = 0;
    // a和b中取深度更深的作为a先跳
    if (depth[a] < depth[b]) swap(a, b); 
    for (int k = 16; k >= 0; k -- )
    // 如果a 跳2^k后的深度比b深度大 则a继续跳
    // 直到两者深度相同 depth[a] == depth[b]
        if (depth[fa[a][k]] >= depth[b])
        {
            distance[cnt ++ ] = d1[a][k];
            distance[cnt ++ ] = d2[a][k];
            a = fa[a][k];
        }
    // 如果a和b深度相同 但此时不是同一个点 两个同时继续向上跳
    if (a != b)
    {
        for (int k = 16; k >= 0; k -- )
            if (fa[a][k] != fa[b][k])
            {
                distance[cnt ++ ] = d1[a][k];
                distance[cnt ++ ] = d2[a][k];
                distance[cnt ++ ] = d1[b][k];
                distance[cnt ++ ] = d2[b][k];
                a = fa[a][k], b = fa[b][k];
            }
        // 此时a和b到lca下同一层 所以还要各跳1步=跳2^0步
        distance[cnt ++ ] = d1[a][0];
        distance[cnt ++ ] = d1[b][0];
    }
    // 找a,b两点距离的最大值dist1和次大值dist2
    int dist1 = -INF, dist2 = -INF;
    for (int i = 0; i < cnt; i ++ )
    {
        int d = distance[i];
        if (d > dist1) dist2 = dist1, dist1 = d;
        else if (d != dist1 && d > dist2) dist2 = d;
    }
    // ⭐ dist1和dist2是a和b之间的最大边权和次大边权  所以可以用w替换而仍然保持生成树(包含所有节点)
    // 因为加入w这条边 原来的树会形成环 

    // 删除环中边权最大的边（如果最大的边和加入的边相等，那么删去次大边）。
    // 如果w>这条路的最大边 w替换dist1
    if (w > dist1) return w - dist1;
    // 否则w==dist1 w替换dist2
    if (w > dist2) return w - dist2;
    // 不加这个return INF也是可以的 
    // ⭐因为非树边w的值域是一定≥dist1 否则在之前kruskal求最小生成树的时候把w替换dist1连接a和b就得到一个更小的生成树了 矛盾
    // 所以最坏情况是w==dist1
    // return INF;
}

int main()
{
    scanf("%d%d", &n, &m);
    for (int i = 0; i < m; i ++ )
    {
        int a, b, c;
        scanf("%d%d%d", &a, &b, &c);
        edge[i] = {a, b, c};
    }
    // kruskal建最小树(把用到的边标记)
    int sum = kruskal();
    // 对标记的边建图
    build();

    bfs();

    int res = 1e9;
    //从前往后枚举非树边
    for (int i = 0; i < m; i ++ )
        if (!edge[i].used)
        {
            int a = edge[i].a, b = edge[i].b, w = edge[i].w;
            // lca(a,b,w) 返回用w替换w[i] 的差值  = w-w[i]
            res = min(res, sum + lca(a, b, w));
        }
    printf("%lld\n", sum);

    return 0;
}
