#include <stdio.h>
#include <stdlib.h>

int det(int mat[][3])
{
    return mat[0][0] * mat[1][1] * mat[2][2] + mat[0][1] * mat[1][2] * mat[2][0] + mat[0][2] * mat[1][0] * mat[2][1] - mat[2][0] * mat[1][1] * mat[0][2] - mat[2][1] * mat[1][2] * mat[0][0] - mat[2][2] * mat[1][0] * mat[0][1];
}

int main()
{
    int m1[3][3] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    printf("%d\n", det(m1));
}