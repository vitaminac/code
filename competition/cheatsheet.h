#include <bits/stdc++.h>

// NAMESPACES
using namespace std;

// DEBUG
#ifdef DEBUG
#define Dbg(x) cerr << __LINE__ << ":" << #x << "=" << (x) << endl;
#define Test(action) action
#else
#define Dbg(x)
#define Test(action)
#endif

// TYPES
typedef uint64_t LLU;
typedef int64_t LL;

// IO
// EMPTY

// CONSTANTs
constexpr double E = 1e-8;
constexpr double PI = 3.14159265358979323;
constexpr int INF = 1 << 31 - 1;

// LOOP
#define FOR(i, N) for (i = 0; i < N; i++)
#define REP(i, N) for (i = 1; i <= N; i++)

// ARITHMETIC
#define IS_ODD(n) (n & 1)
#define DOUBLE(n) (n << 1)
#define HALF(n) (n >> 1)
#define COUNT_DIGITS(N) (floor(log10(N)) + 1)
#define SWAP(a, b) \
    a ^= b;        \
    b ^= a;        \
    a ^= b;
inline constexpr bool is_power_of_two(int x)
{
    return x && (!(x & (x - 1)));
}
inline bool equals(double a, double b) { return fabs(a - b) < 1e-9; }

// STRING
inline size_t length(char *str)
{
    size_t count;
    for (count = 0; str[count]; ++count)
        ;
    return count;
}

// MODULAR ARITHMETIC
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

// MEMORY OPERATION
#define MSET(arr, val) memset(arr, val, sizeof(arr));

// USEFUL DATASTRUCTURES
typedef pair<int, int> PII;
#define pb emplace_back
#define mp make_pair
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