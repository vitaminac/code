#include <stdio.h>

void main()
{
    char palabra[10] = {0};
    scanf("%s", palabra);

    int j = 0;
    char c;
    for (int i = 0; i < 5; i++)
    {
        do
        {
            scanf("%c", &c);
        } while (palabra[j++] == c);
        j--;
    }
    if (!palabra[j])
    {
        printf("Win");
    }
    else
    {
        printf("Game Over");
    }
}