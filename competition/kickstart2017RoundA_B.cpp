#define DEBUG
#include "cheatsheet.h"

char pattern1[2010], pattern2[2010];
int table[8010][8010];
int T, t, n, m, i, j;

// https://code.google.com/codejam/contest/8284486/dashboard#s=p1
int main()
{
    // Speed up I/O Operationa
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

#ifdef DEBUG
    freopen("B-large-practice.in", "r", stdin);
    freopen("ans.out", "w", stdout);
#endif

    cin >> T;

    REP(t, T)
    {
        Dbg(t);

        cin >> pattern1 >> pattern2;

        MSET(table, 0);

        for (n = 1, i = 0; pattern1[i]; i++)
        {
            if (pattern1[i] == '*')
            {
                table[++n][0] = 0;
                table[++n][0] = 0;
                table[++n][0] = 0;
                table[++n][0] = 0;
            }
            else
            {
                table[++n][0] = pattern1[i];
            }
        }

        for (m = 1, j = 0; pattern2[j]; j++)
        {
            if (pattern2[j] == '*')
            {
                table[0][++m] = 0;
                table[0][++m] = 0;
                table[0][++m] = 0;
                table[0][++m] = 0;
            }
            else
            {
                table[0][++m] = pattern2[j];
            }
        }

        table[1][1] = true;
        REP(i, n)
        {
            REP(j, m)
            {
                if (table[i][j])
                {
                    table[i + 1][j] |= table[i + 1][0] == 0;
                    table[i][j + 1] |= table[0][j + 1] == 0;
                    table[i + 1][j + 1] |= !table[i + 1][0] || !table[0][j + 1] || table[i + 1][0] == table[0][j + 1];
                }
            }
        }
        cout << "Case #" << t << ": " << (table[n][m] ? "TRUE" : "FALSE") << endl;
    }

    return 0;
}
