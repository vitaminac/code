//#define DEBUG
#include "cheatsheet.h"

// https://code.google.com/codejam/contest/6304486/dashboard#s=p1
// https://en.wikipedia.org/wiki/Bertrand%27s_ballot_theorem
int main()
{
    ios::sync_with_stdio(false);
    freopen("B-large-practice.in", "r", stdin);
    freopen("ans.out", "w", stdout);

    int T;
    cin >> T;

    LOOP_UNTIL_EQUALS(i, T)
    {
        Dbg(i);
        int N;
        int M;
        cin >> N >> M;
        Dbg(N);
        Dbg(M);
        double y = (double)(N - M) / (N + M);
        cout << "Case #" << i << ": " << y << endl;
    }

    return 0;
}
