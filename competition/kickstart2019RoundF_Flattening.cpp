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
    freopen("test.in", "r", stdin);
    freopen("ans.out", "w", stdout);
#endif

    int t, i, j, group;
    cin >> T;

    REP(t, T)
    {
        cin >> N >> K;

        Dbg(t, N, K);

        for (i = 1; i <= N; ++i)
        {
            cin >> A[i];
        }

        for (i = 1; i <= N; ++i)
        {
            MSET(H, 0);
            int n_same = 0;
            for (j = i; j <= N; ++j)
            {
                ++H[A[j]];
                maxi(n_same, H[A[j]]);
                cost[i][j] = j - i + 1 - n_same;
            }
        }

        FILL_M(dp, INF, MAX_N, MAX_N);

        dp[0][0] = 0;

        for (int group = 0; group < N; ++group)
        {
            for (i = 1; i <= N; ++i)
            {
                for (j = i; j <= N; ++j)
                {
                    mini(dp[group + 1][j], dp[group][i - 1] + cost[i][j]);
                }
            }
        }

        ++K;
        K = min(K, N);
        cout << "Case #" << t << ": " << dp[K][N] << endl;
    }

    return 0;
}
