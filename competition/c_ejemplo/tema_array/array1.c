#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main()
{
    int randoms[10];
    srand(time(NULL));
    for (int i = 0; i < 10; i++)
    {
        randoms[i] = rand();
    }
    for (int i = 0; i < 10; i++)
    {
        printf("%d ", randoms[i] % 51 + 50);
    }
    printf("\n");
    return 0;
}
