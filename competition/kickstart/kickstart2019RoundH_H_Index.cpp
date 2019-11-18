//#define DEBUG
#include "cheatsheet.h"

constexpr int MAX_N = 100010;
int T, N, A[MAX_N];

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050edd/00000000001a274e
int main()
{
    INIT_IO;

#ifdef DEBUG
    freopen("kickstart2019RoundH_H_Index.in", "r", stdin);
    freopen("answer.out", "w", stdout);
#endif

    int t, i, j, tmp, h, higher;

    cin >> T;

    REP(t, T)
    {
        cin >> N;
        FILL(A, 0, N);

        cout << "Case #" << t << ": ";

        h = 0;
        higher = 0;

        FOR(i, N)
        {
            cin >> tmp;
            A[tmp]++;

            if (tmp > h)
                ++higher;

            if (higher > h)
            {
                while (higher > h)
                {
                    higher -= A[++h];
                }
            }

            cout << h << " ";
        }

        cout << endl;
    }

    return 0;
}
