#define DEBUG
#include "cheatsheet.h"

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050edb/0000000000170721
int main()
{
    ios::sync_with_stdio(false);

#ifdef DEBUG
    freopen("test.in", "r", stdin);
    freopen("ans.out", "w", stdout);
#endif

    LLU T, N, M, C, D, answer;
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
