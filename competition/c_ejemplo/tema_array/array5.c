#include <stdio.h>

int main()
{
    char contrasena[255];
    int n = 0;
    int n_numeros = 0;
    int n_letras = 0;
    int n_especial = 0;
    scanf("%s", contrasena);
    for (int i = 0; i < 255; i++)
    {
        if (contrasena[i] == 0)
        {
            if (n < 6)
            {
                printf("Nivel muy bajo\n");
            }
            else if (n_letras == 0 || n_numeros == 0)
            {
                printf("Nivel bajo\n");
            }
            else if (n_especial == 0)
            {
                printf("Nivel medio");
            }
            else
            {
                printf("Nivel alto");
            }
            break;
        }
        else
        {
            n++;
            if (contrasena[i] >= '0' && contrasena[i] <= '9')
            {
                n_numeros++;
            }
            else if ((contrasena[i] >= 'a' && contrasena[i] <= 'z') || (contrasena[i] >= 'A' && contrasena[i] <= 'Z'))
            {
                n_letras++;
            }
            else
            {
                n_especial++;
            }
        }
    }
}
