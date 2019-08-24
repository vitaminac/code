#include <bits/stdc++.h>

using namespace std;

#ifdef DEBUG
#define Dbg(x) cerr << __LINE__ << ":" << #x << "=" << x << endl;
#else
#define Dbg(x)
#endif

#define LL long long

const double E = 1e-8;
const double PI = acos(-1);

#define FOR(i, N) for (int i = 0; i < N; i++)
#define REP(i, N) for (int i = 1; i <= N; i++)

// https://www.cnblogs.com/linyujun/p/5194184.html
#define ADD(x, y, p) (x % p + y % p)
#define SUB(x, y, p) (x % p - y % p)
#define MUL(x, y, p) ((x % p) * (y % p))

LL pow_mod(LL a, LL b, LL p)
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