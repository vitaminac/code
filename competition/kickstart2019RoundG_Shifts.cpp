//#define DEBUG
#include "cheatsheet.h"

constexpr int MAX_N = 20;
constexpr int COMBINATION = 1 << MAX_N;

int T, N, H, A[MAX_N], B[MAX_N];
LL combinations[COMBINATION];

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050e02/000000000018fd5e
int main()
{
    INIT_IO;

#ifdef DEBUG
    freopen("kickstart2019RoundG_Shifts.in", "r", stdin);
    freopen("answer.out", "w", stdout);
#endif

    int t, i;
    LL n_combination, mask, answer;

    cin >> T;

    Dbg(T);

    REP(t, T)
    {
        cin >> N >> H;

        Dbg(t, N, H);

        n_combination = 1 << N;
        Dbg(n_combination);
        FILL(combinations, 0, n_combination);

        FOR(i, N)
        {
            cin >> A[i];
        }

        FOR(i, N)
        {
            cin >> B[i];
        }

        for (mask = 0; mask < n_combination; ++mask)
        {
            LL sum = 0;
            for (int i = 0; i < N; ++i)
            {
                if (!(mask & (1 << i)))
                {
                    sum += B[i];
                }
            }
            if (sum >= H)
            {
                combinations[mask] = 1;
            }
        }

        for (int i = 0; i < N; ++i)
        {
            for (int mask = 0; mask < n_combination; ++mask)
            {
                if (mask & (1 << i))
                {
                    combinations[mask] += combinations[mask ^ (1 << i)];
                }
            }
        }

        answer = 0;
        for (mask = 0; mask < n_combination; ++mask)
        {
            LL sum = 0;
            for (int i = 0; i < N; ++i)
            {
                if (mask & (1 << i))
                {
                    sum += A[i];
                }
            }
            if (sum >= H)
            {
                answer += combinations[mask];
            }
        }

        Dbg(answer);

        cout << "Case #" << t << ": " << answer << endl;
    }

    return 0;
}
