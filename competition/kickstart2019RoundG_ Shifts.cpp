#define DEBUG
#include "cheatsheet.h"

constexpr int MAX_N = 20 + 10;

int T, N, H, A[MAX_N], B[MAX_N], Total_A, Total_B;

int backtracking(int happiness_A, int happiness_B, int i)
{
    if (happiness_A < H || happiness_B < H)
    {
        return 0;
    }
    else
    {
        if (i == N)
        {
            return 1;
        }
        else
        {
            return backtracking(happiness_A - A[i], happiness_B, i + 1) + backtracking(happiness_A, happiness_B - B[i], i + 1) + backtracking(happiness_A, happiness_B, i + 1);
        }
    }
}

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050e02/000000000018fd5e
int main()
{
    INIT_IO;

#ifdef DEBUG
    freopen("test.in", "r", stdin);
    freopen("ans.out", "w", stdout);
#endif

    int t, i, answer;

    cin >> T;

    REP(t, T)
    {
        cin >> N >> H;

        Dbg(t, N, H);

        Total_A = 0;
        FOR(i, N)
        {
            cin >> A[i];
            Total_A += A[i];
        }

        Total_B = 0;
        FOR(i, N)
        {
            cin >> B[i];
            Total_B += B[i];
        }

        answer = backtracking(Total_A, Total_B, 0);

        Dbg(answer);

        cout << "Case #" << t << ": " << answer << endl;
    }

    return 0;
}
