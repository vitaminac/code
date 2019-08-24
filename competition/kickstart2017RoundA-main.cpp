#define DEBUG
#include "cheatsheet.h"

constexpr int MOD = 1000000007;

// https://code.google.com/codejam/contest/8284486/dashboard
// https://blog.csdn.net/kyoma/article/details/60591058
// https://tandy123.github.io/2017/06/16/kickstart-2017-roundA/
// https://stackoverflow.com/questions/57640454/square-counting-of-the-grid
int main()
{
    ios::sync_with_stdio(false);
    freopen("A-large-practice.in", "r", stdin);
    freopen("ans.out", "w", stdout);

    int T;
    uint R, C;
    __uint128_t n, m, y;
    cin >> T;

    REP(t, T)
    {
        Dbg(t);
        cin >> R >> C;
        Dbg(R); // the number of dots in each row
        Dbg(C); // column of the grid
        if (R <= C)
        {
            n = R;
            m = C;
        }
        else
        {
            n = C;
            m = R;
        }
        y = DIV(MUL(MUL(MUL(SUB(n, 1, MOD), n, MOD), ADD(n, 1, MOD), MOD), SUB(2 * m, n, MOD), MOD), 12, MOD);
        cout << "Case #" << t << ": " << (unsigned)(y) << endl;
    }

    return 0;
}
