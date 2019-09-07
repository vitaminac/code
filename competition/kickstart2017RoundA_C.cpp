#define DEBUG
#include "cheatsheet.h"

// https://code.google.com/codejam/contest/8284486/dashboard#s=p2
constexpr int UPPER_LIMIT = 100000000;
constexpr int LOWER_LIMIT = -100000000;
constexpr int X = 0;
constexpr int Y = 1;
constexpr int Z = 2;
constexpr int R = 3;

int T, N, t, n, y;
int stars[2010][4];

inline bool cover(int xs, int xe, int ys, int ye, int zs, int ze, int r)
{
    int minx = INF;
    int maxx = NINF;
    int miny = INF;
    int maxy = NINF;
    int minz = INF;
    int maxz = NINF;

    FOR(n, N)
    {
        if (!(
                stars[n][X] - stars[n][R] >= xs &&
                stars[n][X] + stars[n][R] <= xe &&
                stars[n][Y] - stars[n][R] >= ys &&
                stars[n][Y] + stars[n][R] <= ye &&
                stars[n][Z] - stars[n][R] >= zs &&
                stars[n][Z] + stars[n][R] <= ze))
        {
            minx = min(minx, stars[n][X] - stars[n][R]);
            maxx = max(maxx, stars[n][X] + stars[n][R]);
            miny = min(miny, stars[n][Y] - stars[n][R]);
            maxy = max(maxy, stars[n][Y] + stars[n][R]);
            minz = min(minz, stars[n][Z] - stars[n][R]);
            maxz = max(maxz, stars[n][Z] + stars[n][R]);

            if (maxx - minx > r || maxy - miny > r || maxz - minz > r)
                return false;
        };
    }

    return true;
}

int main()
{
    INIT_IO;

#ifdef DEBUG
    freopen("C-large-practice.in", "r", stdin);
    freopen("ans.out", "w", stdout);
#endif

    cin >> T;

    REP(t, T)
    {
        Dbg(t);

        int minx = INF;
        int maxx = NINF;
        int miny = INF;
        int maxy = NINF;
        int minz = INF;
        int maxz = NINF;

        cin >> N;

        FOR(n, N)
        {
            Dbg(n);

            cin >> stars[n][X] >> stars[n][Y] >> stars[n][Z] >> stars[n][R];

            minx = min(minx, stars[n][X] - stars[n][R]);
            maxx = max(maxx, stars[n][X] + stars[n][R]);
            miny = min(miny, stars[n][Y] - stars[n][R]);
            maxy = max(maxy, stars[n][Y] + stars[n][R]);
            minz = min(minz, stars[n][Z] - stars[n][R]);
            maxz = max(maxz, stars[n][Z] + stars[n][R]);
        }

        int rleft = 0;
        int rright = INF;
        while (rleft < rright)
        {
            int r = (rleft + rright) / 2;

            if (
                cover(minx, minx + r, miny, miny + r, minz, minz + r, r) ||
                cover(minx, minx + r, miny, miny + r, maxz - r, maxz, r) ||
                cover(minx, minx + r, maxy - r, maxy, minz, minz + r, r) ||
                cover(minx, minx + r, maxy - r, maxy, maxz - r, maxz, r) ||
                cover(maxx - r, maxx, miny, miny + r, minz, minz + r, r) ||
                cover(maxx - r, maxx, miny, miny + r, maxz - r, maxz, r) ||
                cover(maxx - r, maxx, maxy - r, maxy, minz, minz + r, r) ||
                cover(maxx - r, maxx, maxy - r, maxy, maxz - r, maxz, r))
                rright = r;
            else
                rleft = r + 1;
        }

        cout << "Case #" << t << ": " << rleft << endl;
    }

    return 0;
}
