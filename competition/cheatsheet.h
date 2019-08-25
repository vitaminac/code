#include <bits/stdc++.h>

using namespace std;

#ifdef DEBUG
#define Dbg(x) cerr << __LINE__ << ":" << #x << "=" << (x) << endl;
#define Test(action) action
#else
#define Dbg(x)
#define Test(action)
#endif

#define LLU uint64_t
#define LL int64_t

constexpr double E = 1e-8;
constexpr double PI = 3.14159265358979323;
constexpr int INF = 1 << 31 - 1;

#define FOR(i, N) for (int i = 0; i < N; i++)
#define REP(i, N) for (int i = 1; i <= N; i++)

// https://www.cnblogs.com/linyujun/p/5194184.html
#define ADD(x, y, p) ((x + y) % p)
#define SUB(x, y, p) ((x - y) % p)
#define MUL(x, y, p) ((x * y) % p)
LLU fast_power_mod(LLU a, LLU b, LLU p)
{ //a的b次方求余p
    LLU ret = 1;
    while (b)
    {
        if (b & 1)
            ret = (ret * a) % p;
        a = (a * a) % p;
        b >>= 1;
    }
    return ret;
}
#define FERMAT(a, p) fast_power_mod(a, p - 2, p) //费马求a关于b的逆元
#define MOD_INV(a, p) FERMAT(a, p)
#define DIV(x, y, p) MUL(x, MOD_INV(y, p), p)

#define MSET(arr, val) memset(arr, val, sizeof(arr));

template <typename T>
struct UnionFind
{
    vector<T> parent;
    void init(T size)
    {
        parent.resize(size + 1);
        for (T i = 0; i < parent.size(); i++)
            parent[i] = i;
    }

    void merge(T x, T y)
    {
        parent[find(x)] = find(y);
    }

    int find(T x)
    {
        return x == parent[x] ? x : parent[x] = find(parent[x]);
    }

    bool together(T x, T y)
    {
        return find(x) == find(y);
    }
};