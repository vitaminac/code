#include <stdio.h>

#define N 100

unsigned int countc(char *arr, int size, char c)
{
    int n = 0;
    for (int i = 0; i < size; i++)
    {
        if (arr[i] == c)
        {
            n++;
        }
    }
    return n;
}

int main()
{
    printf("%d\n", countc("Hola Mundo, Como estas?", 23, 'o'));
    return 0;
}
