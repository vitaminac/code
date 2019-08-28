//#define DEBUG
#include "cheatsheet.h"

constexpr int MOD = 1000000007;

// https://code.google.com/codejam/contest/8284486/dashboard
// https://blog.csdn.net/kyoma/article/details/60591058
// https://tandy123.github.io/2017/06/16/kickstart-2017-roundA/
// https://stackoverflow.com/questions/57640454/square-counting-of-the-grid
int main()
{
    INIT_IO;
    
    freopen("A-large-practice.in", "r", stdin);
    freopen("ans.out", "w", stdout);

    int T;
    uint64_t n, m, y;
    cin >> T;

    REP(t, T)
    {
        Dbg(t);
        cin >> n >> m;
        Dbg(n); // the number of dots in each row
        Dbg(m); // column of the grid
        if (m < n)
            swap(n, m);
        y = DIV(MUL(MUL(MUL(SUB(n, 1, MOD), n, MOD), ADD(n, 1, MOD), MOD), SUB(2 * m, n, MOD), MOD), 12, MOD);
        Dbg(y);
        cout << "Case #" << t << ": " << y << endl;
    }

    return 0;
}
