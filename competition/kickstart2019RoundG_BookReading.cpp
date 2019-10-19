#define DEBUG
#include "cheatsheet.h"

int T, N, M, Q;
HashSet<int> P;

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050e02/000000000018fd0d
int main()
{
    INIT_IO;

#ifdef DEBUG
    freopen("test.in", "r", stdin);
    freopen("ans.out", "w", stdout);
#endif

    int t, i, tmp, answer;
    cin >> T;

    REP(t, T)
    {
        cin >> N >> M >> Q;

        Dbg(N, M, Q);

        P.clear();

        FOR(i, M)
        {
            cin >> tmp;
            P.insert(tmp);
        }

        answer = 0;

        FOR(i, Q)
        {
            cin >> tmp;
            answer += N / tmp;
            for (int torn : P)
            {
                if (torn % tmp == 0)
                {
                    answer -= 1;
                }
            }
        }
        Dbg(answer);
        cout << "Case #" << t << ": " << answer << endl;
    }

    return 0;
}
