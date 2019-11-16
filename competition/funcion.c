#include <stdio.h>
#include <stdbool.h>

int inverse(int n)
{
    return -n;
}

void imprimir(int n)
{
    printf("%d\n", n);
}

bool es_amigo(int a, int b)
{
    int n1 = 0, n2 = 0;
    for (int i = 1; i <= a / 2; i++)
    {
        if (a % i == 0)
        {
            n1 += i;
        }
    }
    for (int i = 1; i <= b / 2; i++)
    {
        if (b % i == 0)
        {
            n2 += i;
        }
    }
    if (n1 == b && n2 == a)
    {
        return true;
    }
    else
    {
        return false;
    }
}

int main()
{
    imprimir(inverse(-5));
    printf("es amigo: %s", (es_amigo(220, 284) ? "true" : "false"));
    return 0;
}