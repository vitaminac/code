#include <bits/stdc++.h>

using namespace std;

#ifdef DEBUG
#define Dbg(x) cerr << __LINE__ << ":" << #x << "=" << x << endl;
#else
#define Dbg(x)
#endif

#define LL unsigned long long int

constexpr double E = 1e-8;
constexpr double PI = 3.14159265358979323;
constexpr int INF = 1 << 29;

#define FOR(i, N) for (int i = 0; i < N; i++)
#define REP(i, N) for (int i = 1; i <= N; i++)

// https://www.cnblogs.com/linyujun/p/5194184.html
#define ADD(x, y, p) ((x + y) % p)
#define SUB(x, y, p) ((x - y) % p)
#define MUL(x, y, p) ((x * y) % p)
LL fast_power_mod(LL a, LL b, LL p)
{ //a的b次方求余p
    LL ret = 1;
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