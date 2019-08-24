//#define DEBUG
#include "cheatsheet.h"

// https://code.google.com/codejam/contest/6304486/dashboard#s=p2
// https://codeforces.com/blog/entry/46881
int main()
{
    ios::sync_with_stdio(false);
    freopen("C-Large-practice.in", "r", stdin);
    freopen("ans.out", "w", stdout);

    int T;
    cin >> T;

    LOOP_UNTIL_EQUALS(t, T)
    {
        Dbg(t);
        uint L;
        uint R;
        cin >> L >> R;
        Dbg(L);
        Dbg(R);
        uint n = min(L, R);
        Dbg(n);
        cout << "Case #" << t << ": " << (n * (n + 1)) / 2 << endl;
    }

    return 0;
}
