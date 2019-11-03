//#define DEBUG
#include "cheatsheet.h"

constexpr int MAX_N = 110;
constexpr int MAX_A = 1010;

int T, N, K, A[MAX_N], H[MAX_A], cost[MAX_N][MAX_N], dp[MAX_N][MAX_N];

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050edc/000000000018666c
int main()
{
    INIT_IO;

#ifdef DEBUG
    freopen("kickstart2019RoundF_Flattening.in", "r", stdin);
    freopen("answer.out", "w", stdout);
#endif

    int t, i, j, group;
    cin >> T;

    REP(t, T)
    {
        cin >> N >> K;

        Dbg(t, N, K);

        for (i = 0; i < N; i++)
        {
            cin >> A[i];
        }

        for (i = 0; i < N; i++)
        {
            MSET(H, 0);
            int n_same = 0;
            for (j = i; j < N; j++)
            {
                ++H[A[j]];
                maxi(n_same, H[A[j]]);
                cost[i][j] = j - i + 1 - n_same;
            }
        }

        FILL_M(dp, INF, MAX_N, MAX_N);

        dp[0][0] = 0;
        K = min(K + 1, N);
        Dbg(K);

        for (int group = 1; group <= K; group++)
        {
            for (i = 0; i < N; i++)
            {
                dp[group][0] = 0;
                for (j = i; j < N; j++)
                {
                    mini(dp[group][j + 1], dp[group - 1][i] + cost[i][j]);
                }
            }
        }

        Dbg(dp[K][N]);
        cout << "Case #" << t << ": " << dp[K][N] << endl;
    }

    return 0;
}