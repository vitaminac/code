#define DEBUG
#include "cheatsheet.h"

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050edb/00000000001707b8
constexpr int NMAX = 100000;
typedef struct slot
{
    int C;
    int E;
    double rate; // cache property to speed up the sort operation
} slot;

slot slots[NMAX];

int main()
{
    ios::sync_with_stdio(false);

#ifdef DEBUG
    freopen("test.in", "r", stdin);
    freopen("ans.out", "w", stdout);
#endif

    int T, D, S, A, B, s, t, d;
    double f;
    vector<slot *> ordered_slots;

    ordered_slots.resize(NMAX);
    cin >> T;

    REP(t, T)
    {
        Dbg(t);
        cin >> D >> S;

        FOR(s, S)
        {
            cin >> slots[s].C;
            cin >> slots[s].E;
            if (slots[s].E == 0)
            {
                slots[s].rate = INF;
            }
            else
            {
                slots[s].rate = ((double)slots[s].C) / slots[s].E;
            }
            ordered_slots[s] = slots + s;
        }

        sort(ordered_slots.begin(), ordered_slots.begin() + S, [](const slot *left, const slot *right) { return left->rate > right->rate; });

        cout << "Case #" << t << ": ";

        FOR(d, D)
        {
            cin >> A;
            cin >> B;
            f = 1;

            for (s = 0; s < S; s++)
            {
                if (A > ordered_slots[s]->C)
                {
                    A -= ordered_slots[s]->C;
                }
                else
                {
                    f = 1.0 - ((double)A) / ordered_slots[s]->C;
                    A = 0;
                    break;
                }
            }

            if (s < S)
            {
                for (B -= f * ordered_slots[s]->E, s += 1; s < S; s++)
                {
                    if (B > ordered_slots[s]->E)
                    {
                        B -= ordered_slots[s]->E;
                    }
                    else
                    {
                        B = 0;
                        break;
                    }
                }
            }

            cout << ((A <= 0 && B <= 0) ? 'Y' : 'N');
        }

        cout << endl;
    }

    return 0;
}
