#define DEBUG
#include "cheatsheet.h"

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050edb/00000000001707b8
constexpr int NMAX = 100000;
typedef struct slot
{
    int C;
    int E;
    int ac_C;
    int ac_E;
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

    int T, D, S, A, B, s, t, d, A_index, B_index;
    double remains, need;
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
        auto begin = ordered_slots.begin();
        auto end = begin + S;
        sort(begin, end, [](const slot *left, const slot *right) { return left->rate > right->rate; });

        ordered_slots[0]->ac_C = ordered_slots[0]->C;
        ordered_slots[S - 1]->ac_E = ordered_slots[S - 1]->E;
        for (int i = S - 2; i >= 0; i--)
        {
            ordered_slots[i]->ac_E = ordered_slots[i + 1]->ac_E + ordered_slots[i]->E;
            ordered_slots[S - i - 1]->ac_C = ordered_slots[S - i - 2]->ac_C + ordered_slots[S - i - 1]->C;
        }

        Test(
            for (auto it = ordered_slots.begin(); it != ordered_slots.begin() + S; it++) {
                std::stringstream ss;
                ss << '{' << "C:" << (*it)->C << ", E:" << (*it)->E << ", ac_C:" << (*it)->ac_C << ", ac_E:" << (*it)->ac_E << ", rate:" << (*it)->rate << '}';
                Dbg(ss.str());
            });

        cout << "Case #" << t << ": ";

        FOR(d, D)
        {
            cin >> A;
            cin >> B;

            auto A_ptr = lower_bound(begin, end, A, [](const slot *left, const int val) { return left->ac_C < val; });
            auto rbegin = ordered_slots.rbegin() + (NMAX - S);
            auto rend = ordered_slots.rend();
            auto B_ptr = lower_bound(rbegin, rend, B, [](const slot *left, const int val) { return left->ac_E < val; });

            if (A_ptr == end || B_ptr == rend)
            {
                cout << 'N';
            }
            else
            {
                Dbg((*A_ptr)->ac_C);
                Dbg((*B_ptr)->ac_E);
                A_index = A_ptr - begin;
                B_index = rend - B_ptr - 1;
                if (A_index < B_index)
                {
                    cout << 'Y';
                }
                else if (A_index == B_index)
                {
                    remains = ((double)(ordered_slots[A_index]->ac_C - A)) / ordered_slots[A_index]->C;
                    Dbg(remains);
                    need = 1.0 - ((double)(ordered_slots[B_index]->ac_E - B)) / ordered_slots[B_index]->E;
                    Dbg(need);
                    if (need <= remains)
                    {
                        cout << 'Y';
                    }
                    else
                    {
                        cout << 'N';
                    }
                }
                else
                {
                    cout << 'N';
                }
            }
        }
        cout << endl;
    }

    return 0;
}