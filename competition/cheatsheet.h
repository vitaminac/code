/**
 * Author: Gao
 * Reference:
 * https://www.quora.com/What-template-do-you-use-in-competitive-programming
 * https://github.com/helloproclub/competitive-programming-cheat-sheet
 **/

// OPTIMIZATIONS
// #pragma comment(linker, "/stack:200000000")
// #pragma GCC optimize("O3")
// #pragma GCC optimize("O3fast")
// #pragma GCC optimize("Ofast,unroll-loops,no-stack-protector,fast-math")
// #pragma GCC target("sse,sse2,sse3,ssse3,sse4,popcnt,abm,mmx,avx")
// #pragma GCC optimize("unroll-loops")

#include <bits/stdc++.h>
#include <ext/pb_ds/assoc_container.hpp>
#include <ext/pb_ds/tree_policy.hpp>

// NAMESPACES
using namespace std;
using namespace __gnu_pbds;

// DEBUG
#ifdef DEBUG
template <typename Arg>
void log(const char *name, Arg &&arg)
{
    cerr << name << " = " << arg << std::endl;
}
template <typename Arg1, typename... Args>
void log(const char *names, Arg1 &&arg1, Args &&... args)
{
    const char *comma = strchr(names + 1, ',');
    cerr.write(names, comma - names) << " = " << arg1;
    log(comma, args...);
}
#define Dbg(...)                                    \
    cerr << __FUNCTION__ << ":" << __LINE__ << ":"; \
    log(#__VA_ARGS__, __VA_ARGS__)
#define Test(action) action
#else
#define Dbg(...)
#define Test(action)
#endif

// TYPES
typedef uint64_t LLU;
typedef int64_t LL;
typedef __int128_t LLL;
typedef __uint128_t LLLU;

// IO
// Speed up I/O Operation
#define INIT_IO                  \
    ios::sync_with_stdio(false); \
    cin.tie(nullptr);

// CONSTANTS
constexpr double E = 1e-8;
constexpr double PI = 3.14159265358979323;
constexpr int INF = 1 << 31 - 1;
constexpr int NINF = 1 << 31;
constexpr LL ONE = 1;

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
#define SWAP_EVEN_NUMBER(n) (((n + 1) ^ 1) - 1)
inline constexpr bool is_power_of_two(int x)
{
    return x && (!(x & (x - 1)));
}
inline bool equals(double a, double b) { return fabs(a - b) < 1e-9; }
template <class n, class s>
void mini(n &p, s y)
{
    if (p > y)
        p = y;
}
template <class n, class s>
void maxi(n &p, s y)
{
    if (p < y)
        p = y;
}

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
#define FILL(arr, val, size)       \
    for (int i = 0; i < size; i++) \
        arr[i] = val;

#define FILL_M(arr, val, rows, cols)   \
    for (int i = 0; i < rows; i++)     \
        for (int j = 0; j < cols; j++) \
            arr[i][j] = val;

// USEFUL DATASTRUCTURES
typedef pair<int, int> PII;
template <typename T>
using HashSet = std::unordered_set<T>;
template <typename T>
using TreeSet = std::set<T>;
template <typename Key, typename Value>
using HashMap = std::unordered_map<Key, Value>;
template <typename Key, typename Value>
using TreeMap = std::map<Key, Value>;
#define CONTAINS(SET, ELEMENT) (SET.find(ELEMENT) != SET.end())
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