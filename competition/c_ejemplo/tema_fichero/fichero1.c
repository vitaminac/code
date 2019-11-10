#include <stdio.h>

int main()
{
    char content[255];
    char filename[255];
    FILE *file;
    printf("Introduzca el nombre de archivo: ");
    scanf("%s", filename);
    file = fopen(filename, "r");
    while (fgets(content, 255, file))
    {
        fputs(content, stdout);
    }
    fclose(file);
}