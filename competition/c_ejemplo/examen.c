#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

typedef int *Sudoku;
int sudoku[9][9];

/*
 * Se pide hacer una función que compruebe si el sudoku leído está bien formado,
 * de acuerdo con las restricciones especificadas anteriormente.
 */
int comprobar_es_valido(int *tipo_de_fallo)
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
                *tipo_de_fallo = 1;
                return i + 1;
            }
            else
            {
                bitmap_horizontal |= 1 << (sudoku[i][j]);
            }
            if (bitmap_vertical & (1 << (sudoku[j][i])))
            {
                *tipo_de_fallo = 2;
                return i + 1;
            }
            else
            {
                bitmap_vertical |= 1 << (sudoku[j][i]);
            }
            if (bitmap_block & (1 << (sudoku[((i / 3) * 3) + (j / 3)][((i % 3) * 3) + (j % 3)])))
            {
                *tipo_de_fallo = 3;
                return i + 1;
            }
            else
            {
                bitmap_block |= 1 << (sudoku[((i / 3) * 3) + (j / 3)][((i % 3) * 3) + (j % 3)]);
            }
        }
    }
    *tipo_de_fallo = 0;
    return 0;
}

// para debug
void print_sudoku(Sudoku sudoku)
{
    for (int i = 0; i < 9; i++)
    {
        printf("%d %d %d %d %d %d %d %d %d\n", sudoku[i * 9 + 0], sudoku[i * 9 + 1], sudoku[i * 9 + 2], sudoku[i * 9 + 3], sudoku[i * 9 + 4], sudoku[i * 9 + 5], sudoku[i * 9 + 6], sudoku[i * 9 + 7], sudoku[i * 9 + 8]);
    }
    printf("\n\n");
}

/*
 * Se pide hacer una función que se encargue de leer
 * los sudoku del fichero binario proporcionado.
 */
bool leer_sudoku(FILE *input, Sudoku sudoku)
{
    if (fread(sudoku, 81, sizeof(int), input) > 0)
    {
        return true;
    }
    else
    {
        return false;
    }
}

/*
 * Se pide hacer una función que escriba el resultado de la comprobación de cada sudoku
 * en una línea diferente en un fichero de texto,
 * en el que se numerarán los sudoku en función de su orden de lectura
 */
void escribir_output(int tipo_de_fallo, int n, int lugar, FILE *output)
{
    switch (tipo_de_fallo)
    {
    case 0:
        fprintf(output, "El sudoku número %d está bien formado\n", n);
        break;
    case 1:
        fprintf(output, "El sudoku número %d es erróneo en la fila %d\n", n, lugar);
        break;
    case 2:
        fprintf(output, "El sudoku número %d es erróneo en la columna %d\n", n, lugar);
        break;
    case 3:
        fprintf(output, "El sudoku número %d es erróneo en la submatriz %d\n", n, lugar);
        break;
    }
}

int main()
{
    FILE *input = fopen("sudokus.dat", "rb");
    FILE *output = fopen("output.txt", "w");
    int tipo_de_fallo;
    int retorno;
    // leer hasta que termine el archivo
    for (int i = 1; leer_sudoku(input, (Sudoku)sudoku); i++)
    {
        // para debug
        // print_sudoku((Sudoku)sudoku);
        retorno = comprobar_es_valido(&tipo_de_fallo);
        escribir_output(tipo_de_fallo, i, retorno, output);
    }
    fflush(output);
    fclose(input);
    fclose(output);
    return 0;
}
