//#define DEBUG
#include "cheatsheet.h"

constexpr int MAX_N = 1e5 + 10;

int T, N, M, Q, answers[MAX_N];
bool bad[MAX_N];

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050e02/000000000018fd0d
int main()
{
    INIT_IO;

#ifdef DEBUG
    freopen("kickstart2019RoundG_BookReading.in", "r", stdin);
    freopen("answer.out", "w", stdout);
#endif

    int t, i, p, q;
    LLU total;
    cin >> T;

    REP(t, T)
    {
        cin >> N >> M >> Q;

        Dbg(N, M, Q);

        FILL(bad, false, N + 1)
        FOR(i, M)
        {
            cin >> p;
            bad[p] = true;
        }

        FILL(answers, -1, N + 1);
        total = 0;
        FOR(i, Q)
        {
            cin >> q;
            if (answers[q] == -1)
            {
                answers[q] = 0;
                for (int j = q; j <= N; j += q)
                {
                    if (!bad[j])
                    {
                        answers[q] += 1;
                    }
                }
            }
            total += answers[q];
        }
        Dbg(total);
        cout << "Case #" << t << ": " << total << endl;
    }

    return 0;
}
