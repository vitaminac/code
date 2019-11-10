#include <stdio.h>
#include <stdlib.h>

int main()
{
    float *array;
    int n;
    printf("Introduzca el numero n:");
    scanf("%d", &n);
    array = malloc(sizeof(float) * n);
    for (int i = 0; i < n; i++)
    {
        printf("%f ", array[i]);
    }
    printf("\n");
}