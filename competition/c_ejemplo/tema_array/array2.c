#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define N 100

int main()
{
    int randoms[N];
    srand(time(NULL));
    for (int i = 0; i < N; i++)
    {
        randoms[i] = rand() % 101;
    }
    for (int i = 0; i < N; i++)
    {
        printf("%d ", randoms[i]);
    }
    printf("\n");
    for (int i = 1; i < N; i++)
    {
        randoms[i] = randoms[i - 1] + randoms[i];
    }
    for (int i = 0; i < N; i++)
    {
        printf("%d ", randoms[i]);
    }
    printf("\n");
    return 0;
}
