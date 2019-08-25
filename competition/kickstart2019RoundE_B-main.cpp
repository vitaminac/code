#define DEBUG
#include "cheatsheet.h"

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050edb/00000000001707b8
constexpr int NMAX = 10000;
typedef struct slot
{
    int C;
    int E;
    double f;
} slot;

slot slots[NMAX];

int main()
{
    ios::sync_with_stdio(false);

#ifdef DEBUG
    freopen("test.in", "r", stdin);
    freopen("ans.out", "w", stdout);
#endif

    LLU T, D, S, A, B;
    vector<slot *> ordered_slots;
    double remain_C, remain_E;

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
            ordered_slots[s] = slots + s;
        }

        sort(ordered_slots.begin(), ordered_slots.begin() + S, [](const slot *left, const slot *right) {
            if (left->E == 0)
            {
                return true;
            }
            else if (right->E == 0)
            {
                return false;
            }
            else
            {
                return (((double)left->C) / left->E) > (((double)right->C) / right->E);
            }
        });

        cout << "Case #" << t << ": ";

        FOR(d, D)
        {
            cin >> A;
            cin >> B;

            FOR(s, S)
            {
                slots[s].f = 1; // reset remaining portion
            }

            FOR(s, S)
            {
                remain_C = ordered_slots[s]->f * ordered_slots[s]->C;
                if (A > remain_C)
                {
                    ordered_slots[s]->f = 0;
                    A -= remain_C;
                }
                else
                {
                    ordered_slots[s]->f -= ((double)A) / ordered_slots[s]->C;
                    A = 0;
                }

                remain_E = ordered_slots[S - s - 1]->f * ordered_slots[S - s - 1]->E;
                if (B > remain_E)
                {
                    ordered_slots[S - s - 1]->f = 0;
                    B -= remain_E;
                }
                else
                {
                    ordered_slots[S - s - 1]->f -= ((double)B) / ordered_slots[S - s - 1]->E;
                    B = 0;
                }
            }
            cout << ((A <= 0 && B <= 0) ? 'Y' : 'N');
        }

        cout << endl;
    }

    return 0;
}
