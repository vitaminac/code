//#define DEBUG
#include "cheatsheet.h"

int T, N, S, C, t, i, j, answer;

// https://codingcompetitions.withgoogle.com/kickstart/round/0000000000050edc/00000000001864bc
int main()
{
    INIT_IO;

#ifdef DEBUG
    freopen("kickstart2019RoundF_TeachMe.in", "r", stdin);
    freopen("answer.out", "w", stdout);
#endif

    cin >> T;

    REP(t, T)
    {
        cin >> N >> S;

        Dbg(t, N, S);

        cout << "Case #" << t << ": " << answer << endl;
    }

    return 0;
}