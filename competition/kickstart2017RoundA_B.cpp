#define DEBUG
#include "cheatsheet.h"

char pattern1[2001], pattern2[2001];
int table[8001][8001];

// https://code.google.com/codejam/contest/8284486/dashboard#s=p1
int main()
{
    ios::sync_with_stdio(false);
    freopen("B-small-practice.in", "r", stdin);
    freopen("ans.out", "w", stdout);

    int T, n, m;
    char *pattern1_ptr, *pattern2_ptr;
    cin >> T;

    REP(t, T)
    {
        Dbg(t);
        pattern1_ptr = pattern1;
        pattern2_ptr = pattern2;
        cin >> pattern1_ptr >> pattern2_ptr;
        MSET(table, 0);
        Dbg(pattern1_ptr);
        Dbg(pattern2_ptr);

        n = 0;
        do
        {
            table[++n][0] = *pattern1_ptr++;
            if (table[n][0] == '*')
            {
                table[n][0] = 0;
                table[++n][0] = 0;
                table[++n][0] = 0;
                table[++n][0] = 0;
            }
        } while (*pattern1_ptr);

        m = 0;
        do
        {
            table[0][++m] = *pattern2_ptr++;
            if (table[0][m] == '*')
            {
                table[0][m] = 0;
                table[0][++m] = 0;
                table[0][++m] = 0;
                table[0][++m] = 0;
            }
        } while (*pattern2_ptr);

        table[1][1] = !table[1][0] || !table[0][1] || table[1][0] == table[0][1];
        Dbg(table[1][1] ? "TRUE" : "FALSE");
        REP(i, n)
        {
            REP(j, m)
            {
                if (table[i][j])
                {
                    table[i + 1][j] |= !table[i + 1][0];
                    table[i][j + 1] |= !table[0][j + 1];
                    table[i + 1][j + 1] |= !table[i + 1][0] || !table[0][j + 1] || table[i + 1][0] == table[0][j + 1];
                }
            }
        }
        cout << "Case #" << t << ": " << (table[n][m] ? "TRUE" : "FALSE") << endl;
    }

    return 0;
}
