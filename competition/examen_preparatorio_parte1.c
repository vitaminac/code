/*
    Un sudoku es un pasatiempo matemático inventado a finales de la década de
    1970. Su objetivo es asignar números del 1 al 9 a cada una de las 81 casillas de un
    tablero de dimensiones 9x9, de forma que se cumplan una serie de restricciones:
    • Que todas las casillas tengan asignado un y solamente un valor.
    • Que no se repita ningún dígito en cada fila del tablero.
    • Que no se repita ningún dígito en cada columna del tablero.
    • Que no se repita ningún dígito en cada en cada una de los subtableros de 3x3 que pueden formarse dentro del tablero principal.

    1. Escoger razonadamente una estructura de datos que permita almacenar un
    sudoku en memoria principal.
    2. Hacer un programa en lenguaje C que permita leer por consola un sudoku y lo
    almacene en la estructura de datos escogida en el punto 1.
    3. Escribir una o más funciones que determinen si el sudoku escrito está bien o
    mal formado.
    4. Se recomienda hacer un pequeño menú que permita: i) introducir datos; ii)
    comprobar que el sudoku es correcto; y iii) salir del programa.
    5. Extiende las funcionalidades de la Parte I, escribiendo una función de modo que
    el sudoku introducido por teclado y, almacenado provisionalmente en memoria
    principal, se pueda almacenar en un archivo binario en disco.
    6. Extiende las funcionalidades de la Parte I y del ejercicio 5, escribiendo una
    función de modo que, dado un sudoku almacenado en disco, este se pueda
    leer, cargar en memoria y mostrar por pantalla.
    7. Añade al menú las opciones que consideres oportunas.
*/
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <memory.h>

typedef int Sudoku[9][9];

Sudoku sudoku;

void leer_sudoku(FILE *input)
{
    for (int i = 0; i < 9; i++)
    {
        for (int j = 0; j < 9; j++)
        {
            fscanf(input, "%d", &sudoku[i][j]);
        }
    }
}

void escribir_sudoku(FILE *output)
{
    for (int i = 0; i < 9; i++)
    {
        for (int j = 0; j < 9; j++)
        {
            fprintf(output, "%d ", sudoku[i][j]);
        }
        fprintf(output, "\n");
    }
}

int comprobar_es_valido()
{
    int bitmap_horizontal = 0;
    int bitmap_vertical = 0;
    int bitmap_block = 0;
    for (int i = 0; i < 9; i++)
    {
        bitmap_horizontal = 0;
        bitmap_vertical = 0;
        bitmap_block = 0;
        for (int j = 0; j < 9; j++)
        {
            if (bitmap_horizontal & (1 << (sudoku[i][j])))
            {
                return false;
            }
            else
            {
                bitmap_horizontal |= 1 << (sudoku[i][j]);
            }
            if (bitmap_vertical & (1 << (sudoku[j][i])))
            {
                return false;
            }
            else
            {
                bitmap_vertical |= 1 << (sudoku[j][i]);
            }
            if (bitmap_block & (1 << (sudoku[((i / 3) * 3) + (j / 3)][((i % 3) * 3) + (j % 3)])))
            {
                return false;
            }
            else
            {
                bitmap_block |= 1 << (sudoku[((i / 3) * 3) + (j / 3)][((i % 3) * 3) + (j % 3)]);
            }
        }
    }
    return true;
}

int main()
{
    char c = '0';
    int row;
    int col;
    FILE *file;
    char *filename = (char *)malloc(sizeof(char) * 255);
    memset(sudoku, 0, sizeof(sudoku));
    do
    {
        escribir_sudoku(stdout);
        printf("1) introducir datos\n2) comprobar que el sudoku es correcto\n3) modificar valor\n4) almacenar sudoku\n5) leer sudoku\n6) salir del programa.\n");
        switch (c)
        {
        case '1':
            printf("please input the values for the sudoku.\n");
            leer_sudoku(stdin);
            break;
        case '2':
            if (comprobar_es_valido())
            {
                printf("El sudoku es valido!\n");
            }
            else
            {
                printf("El sudoku no es valido!\n");
            }
            break;
        case '3':
            do
            {
                printf("enter row you want to modify: ");
                scanf("%d", &row);
            } while (!(row >= 1 && row <= 9));
            do
            {
                printf("enter col you want to modify: ");
                scanf("%d", &col);
            } while (!(col >= 1 && col <= 9));
            do
            {
                printf("enter the value you want to set: ");
                scanf("%d", &sudoku[row - 1][col - 1]);
            } while (!(col >= 1 && col <= 9));
            break;
        case '4':
            printf("enter the file name you want to write to: ");
            scanf("%s", filename);
            file = fopen(filename, "w");
            escribir_sudoku(file);
            fflush(file);
            fclose(file);
            break;
        case '5':
            printf("enter the file name you want to read from: ");
            scanf("%s", filename);
            file = fopen(filename, "r");
            leer_sudoku(file);
            fflush(file);
            fclose(file);
            break;
        default:
            break;
        }
    } while ((c = getchar()) != '6');
    printf("Adiós!\n");
    free(filename);
    return 0;
}