#include <stdio.h>
#include <stdlib.h>

int *mul(int *mat1, int *mat2)
{
    int *result = (int *)calloc(9, sizeof(int));
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            result[i * 3 + j] = 0;
            for (int k = 0; k < 3; k++)
            {
                result[i * 3 + j] += mat1[i * 3 + k] * mat2[k * 3 + j];
            }
        }
    }
    return result;
}

int main()
{
    int m1[3][3] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int m2[3][3] = {{1, 5, 9}, {2, 4, 6}, {0, 3, 7}};
    int *m3 = mul((int *)m1, (int *)m2);
    for (int i = 0; i < 3; i++)
    {
        printf("%d %d %d\n", m3[i * 3 + 0], m3[i * 3 + 1], m3[i * 3 + 2]);
    }
}