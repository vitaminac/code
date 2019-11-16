#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <math.h>

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

int suma_lenta(int n1, int n2)
{
    if (n2 == 0)
        return n1;
    return suma_lenta(n1 + 1, n2 - 1);
}

int to_bin(int decimal, char *result)
{
    if (decimal < 2)
    {
        result[0] = decimal + '0';
        return 1;
    }
    else
    {
        int len = to_bin(decimal / 2, result);
        result[len] = decimal % 2 + '0';
        return len + 1;
    }
}

void encrypt(char *phrase)
{
    for (int i = 0; phrase[i]; i++)
    {
        if (phrase[i] >= 'a' && phrase[i] <= 'z')
        {
            phrase[i] = (phrase[i] - 'a' + 2 + 26) % 26 + 'a';
        }
        else if (phrase[i] >= 'A' && phrase[i] <= 'Z')
        {
            phrase[i] = (phrase[i] - 'A' + 2 + 26) % 26 + 'A';
        }
        else if (phrase[i] >= '0' && phrase[i] <= '9')
        {
            phrase[i] = (phrase[i] - '0' + 2 + 10) % 10 + '0';
        }
    }
}

void round_arr(float *arr, int n)
{
    for (int i = 0; i < n; i++)
    {
        arr[i] = round(arr[i]);
    }
}

int fibonacci(int n)
{
    if (n < 2)
        return n;
    else
        return fibonacci(n - 1) + fibonacci(n - 2);
}

int main()
{
    char result[255];
    imprimir(inverse(-5));
    printf("es amigo: %s\n", (es_amigo(220, 284) ? "true" : "false"));
    printf("suma lenta: %d\n", suma_lenta(5, 9));
    int len = to_bin(987654321, result);
    result[len] = '\0';
    printf("%s\n", result);
    char welcome[255] = "Hola Mundo!";
    encrypt(welcome);
    printf("%s\n", welcome);
    float arr[5] = {1.1f, 2.2f, 3.3f, 4.4f, 5.5f};
    printf("%f %f %f %f %f\n", arr[0], arr[1], arr[2], arr[3], arr[4]);
    round_arr(arr, 5);
    printf("%f %f %f %f %f\n", arr[0], arr[1], arr[2], arr[3], arr[4]);
    printf("fibonacci 8: %d\n", fibonacci(8));
    return 0;
}