#include <stdio.h>

int main()
{
    int enteros[10];
    int *max = enteros;
    int *min = enteros;
    for (int i = 0; i < 10; i++)
    {
        scanf("%d", enteros + i);
    }

    for (int i = 0; i < 10; i++)
    {
        printf("%d ", i);
        if (enteros[i] > *max)
        {
            max = enteros + i;
        }
        if (enteros[i] < *min)
        {
            min = enteros + i;
        }
    }
    printf("\nmin: %d, max: %d\n", *min, *max);
}