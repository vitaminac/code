#include <stdio.h>
#include <stdlib.h>

int main()
{
    float *array1;
    float *array2;
    float *array3;
    int n;
    printf("Introduzca el numero n:");
    scanf("%d", &n);
    array1 = malloc(sizeof(float) * n);
    array2 = malloc(sizeof(float) * n);
    array3 = malloc(sizeof(float) * n);
    for (int i = 0; i < n; i++)
    {
        array3[i] = array1[i] + array2[i];
        printf("%f ", array3[i]);
    }
    printf("\n");
}