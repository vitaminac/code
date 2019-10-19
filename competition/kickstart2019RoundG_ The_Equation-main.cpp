#define DEBUG
#include "cheatsheet.h"

constexpr int MAX_N = 1010;
constexpr LL INITIAL_PIVOT = 1LL << 50;

int T, N, M, A[MAX_N];

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050e02/000000000018fe36
int main()
{
    INIT_IO;

#ifdef DEBUG
    freopen("test.in", "r", stdin);
    freopen("ans.out", "w", stdout);
#endif

    int t, i, j;
    LL m, prev_m, answer, pivot, next_answer, mask;
    cin >> T;

    REP(t, T)
    {
        cin >> N >> M;

        Dbg(N, M);

        FOR(i, N)
        {
            cin >> A[i];
        }

        pivot = INITIAL_PIVOT;

        answer = 0;

        REP(j, 51)
        {
            m = 0;
            prev_m = 0;
            mask |= pivot;
            next_answer = answer | pivot;
            FOR(i, N)
            {
                prev_m += (A[i] & mask) ^ answer;
                m += (A[i] & mask) ^ next_answer;
            }
            if (m <= M || m <= prev_m)
            {
                answer = next_answer;
            }
            pivot >>= 1;
        }

        Dbg(answer);

        if (m > M && prev_m > M)
        {
            answer = -1;
        }

        cout << "Case #" << t << ": " << answer << endl;
    }

    return 0;
}
