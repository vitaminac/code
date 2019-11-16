//#define DEBUG
#include "cheatsheet.h"

constexpr int MAX_N = 1010;
constexpr int HIGHEST_BIT = 50;

int T, N;
LL M, A[MAX_N], cost_set[HIGHEST_BIT], cost_unset[HIGHEST_BIT], minimums[HIGHEST_BIT];

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050e02/000000000018fe36
int main()
{
    INIT_IO;

#ifdef DEBUG
    freopen("kickstart2019RoundG_The_Equation.in", "r", stdin);
    freopen("answer.out", "w", stdout);
#endif

    int t, i, j;
    LL pivot;

    cin >> T;

    REP(t, T)
    {
        cin >> N >> M;

        Dbg(N, M);

        FOR(i, N)
        {
            cin >> A[i];
        }

        minimums[0] = 0;
        for (i = 0; i < HIGHEST_BIT; i++)
        {
            pivot = ONE << i;
            cost_set[i] = cost_unset[i] = 0;
            FOR(j, N)
            {
                if (A[j] & pivot)
                {
                    cost_unset[i] += pivot;
                }
                else
                {
                    cost_set[i] += pivot;
                }
            }
            minimums[i + 1] = minimums[i] + min(cost_set[i], cost_unset[i]);
        }

        LL sum = 0;
        LL answer = 0;
        for (i = HIGHEST_BIT - 1; i >= 0; i--)
        {
            pivot = ONE << i;
            if (sum + cost_set[i] + minimums[i] <= M)
            {
                answer |= pivot;
                sum += cost_set[i];
            }
            else
            {
                sum += cost_unset[i];
            }

            if (sum > M)
            {
                answer = -1;
                break;
            }
        }

        Dbg(answer);

        cout << "Case #" << t << ": " << answer << endl;
    }

    return 0;
}
