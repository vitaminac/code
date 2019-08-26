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

bool solve(const int A, const int B)
{
    if (A > ac_C[S] || B > ac_E[S])
        return false;
    int index = lower_bound(ac_C, ac_C + S + 1, A) - ac_C - 1;
    LL need_coding = A - ac_C[index];
    LL need_eating = B - (ac_E[S] - ac_E[index + 1]);
    LL max_available_coding = slots[index].coding;
    LL max_available_eating = slots[index].eating;
    return (max_available_coding - need_coding) * max_available_eating >= need_eating * max_available_coding; // find the error, here must be multiplication rather than division
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