#include <stdio.h>

#define N 2
typedef struct
{
    char nombre[32];
    char apellido[32];
    char telefono[32];
} Info;

Info info[N];

int main()
{
    char filename[255];
    FILE *file;
    for (int i = 0; i < N; i++)
    {
        printf("Introduzca el nombre, apellido y el telefono: ");
        scanf("%s %s %s", info[i].nombre, info[i].apellido, info[i].telefono);
    }

    printf("Introduzca el nombre de archivo: ");
    scanf("%s", filename);
    file = fopen(filename, "w");
    fwrite(&info, sizeof(Info), 2, file);
    fclose(file);
}