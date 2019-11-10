#include <stdio.h>

int main()
{
    char content[255];
    char filename1[255];
    char filename2[255];
    FILE *origen;
    FILE *destino;
    printf("Introduzca el nombre de archivo origen: ");
    scanf("%s", filename1);
    printf("Introduzca el nombre de archivo destino: ");
    scanf("%s", filename2);
    origen = fopen(filename1, "r");
    destino = fopen(filename2, "w");
    while (fgets(content, 255, origen))
    {
        fputs(content, destino);
    }
    fclose(origen);
    fclose(destino);
}