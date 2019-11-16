#include <stdio.h>

#define N 100

float maxf(float *arr, int n)
{
    float max = 1 << 31;
    for (int i = 0; i < n; i++)
    {
        if (arr[i] > max)
        {
            max = arr[i];
        }
    }
    return max;
}

int main()
{
    float numbers[10] = {1.1, 9.8, 56.3, 7.8, 99.9, 65.5, 64.5, 12345.521, 98.55555, 66.66};
    printf("%f\n", maxf(numbers, 10));
    return 0;
}
