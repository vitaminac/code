#include <stdio.h>

int main()
{
    int position;
    char content[255];
    char filename[255];
    FILE *file;
    printf("Introduzca el nombre de archivo: ");
    scanf("%s", filename);
    getchar();
    file = fopen(filename, "w");
    fgets(content, 255, stdin);
    fputs(content, file);
    printf("Introduzca la position que quiere consultar: ");
    scanf("%d", &position);
    fclose(file);
    file = fopen(filename, "r");
    fseek(file, position, SEEK_SET);
    printf("letra es %c\n", fgetc(file));
    fclose(file);
}