#define DEBUG
#include "cheatsheet.h"

int T, M, t, m;

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050edb/0000000000170721
int main()
{
    INIT_IO;

#ifdef DEBUG
    freopen("test.in", "r", stdin);
    freopen("ans.out", "w", stdout);
#endif

    LLU N, C, D, answer;
    UnionFind<LLU> us;
    cin >> T;

    REP(t, T)
    {
        Dbg(t);
        cin >> N >> M;
        answer = (N - 1) * 2;

        us.init(N);
        FOR(m, M)
        {
            cin >> C >> D;
            if (!us.together(C, D))
            {
                answer -= 1;
                us.merge(C, D);
            }
        }

        cout << "Case #" << t << ": " << answer << endl;
    }

    return 0;
}
