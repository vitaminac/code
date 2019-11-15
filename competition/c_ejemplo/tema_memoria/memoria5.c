#include <stdio.h>
#include <stdlib.h>

int *mul(int *mat, int m, int n)
{
    int *result = (int *)calloc(m * n, sizeof(int));
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            result[j * m + i] = mat[i * n + j];
        }
    }
    return result;
}

int main()
{
    int m1[3][3] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int *m2 = mul((int *)m1, 3, 3);
    for (int i = 0; i < 3; i++)
    {
        printf("%d %d %d\n", m2[i * 3 + 0], m2[i * 3 + 1], m2[i * 3 + 2]);
    }
}