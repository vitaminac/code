#include <stdio.h>

int main()
{
    int n;
    char content[255];
    char filename1[255];
    char filename2[255];
    char filename3[255];
    FILE *origen;
    FILE *destino_impar;
    FILE *destino_par;
    printf("Introduzca el nombre de archivo origen: ");
    scanf("%s", filename1);
    printf("Introduzca el nombre de archivo destino para impares: ");
    scanf("%s", filename2);
    printf("Introduzca el nombre de archivo destino para pares: ");
    scanf("%s", filename3);
    origen = fopen(filename1, "r");
    destino_impar = fopen(filename2, "w");
    destino_par = fopen(filename3, "w");
    while (fscanf(origen, "%d", &n) != EOF)
    {
        if (n % 2 == 0)
        {
            fprintf(destino_par, "%d\n", n);
        }
        else
        {
            fprintf(destino_impar, "%d\n", n);
        }
    }
    fclose(origen);
    fclose(destino_impar);
    fclose(destino_par);
}