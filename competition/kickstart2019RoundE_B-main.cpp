#define DEBUG
#include "cheatsheet.h"

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050edb/00000000001707b8
constexpr int NMAX = 100010;

#define coding first
#define eating second
PII slots[NMAX];
int ac_C[NMAX];
int ac_E[NMAX];

int T, D, S, A, B;

bool cmp(const PII left, const PII right) { return left.coding * right.eating > right.coding * left.eating; }

LL crs(LL x1, LL y1, LL x2, LL y2, LL x3, LL y3)
{
    return (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1);
}

bool solve(const int A, const int B)
{
    if (A > ac_C[S])
        return false;
    int index = lower_bound(ac_C, ac_C + S + 1, A) - ac_C;
    if (index)
        index--;
    return crs(A - ac_C[index], B - (ac_E[S] - ac_E[index + 1]), slots[index].coding, 0, 0, slots[index].eating) >= 0;
}

int main()
{
    // Speed up I/O Operation
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifdef DEBUG
    freopen("test.in", "r", stdin);
    freopen("ans.out", "w", stdout);
#endif

    int t, s, d;

    cin >> T;

    REP(t, T)
    {
        Dbg(t);
        cin >> D >> S;

        FOR(s, S)
        {
            cin >> slots[s].coding >> slots[s].eating;
        }

        sort(slots, slots + S, cmp);

        ac_C[0] = 0;
        ac_E[0] = 0;
        for (int i = 0; i < S; i++)
        {
            ac_C[i + 1] = ac_C[i] + slots[i].coding;
            ac_E[i + 1] = ac_E[i] + slots[i].eating;
        }

        cout << "Case #" << t << ": ";

        FOR(d, D)
        {
            cin >> A >> B;
            cout << (solve(A, B) ? 'Y' : 'N');
        }
        cout << endl;
    }

    return 0;
}