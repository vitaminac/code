#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int main()
{
    int n;
    scanf("%d", &n);
    bool *is_not_prime = calloc(n, sizeof(bool));
    for (int i = 2; i < n; i++)
    {
        if (!is_not_prime[i])
        {
            printf("%d ", i);
        }
        for (int j = i; j < n; j += i)
        {
            is_not_prime[j] = true;
        }
    }
    free(is_not_prime);
    return 0;
}
