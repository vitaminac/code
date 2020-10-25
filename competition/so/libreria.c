/* cabecera */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include "libreria.h"

/* definicion de constantes */
#define NEWLINE '\n'
#define MIN_BUF_SIZE 16
#define NULL_CHAR '\0'

extern int head(int N) {
	/* declaracion de variables */
	size_t capacity = MIN_BUF_SIZE;

	char * line = NULL;


	/* asigna un bloque de memoria para guardar el string */
	line = (char *)malloc(capacity * sizeof(char));

	/* comprueba que no tiene errores */
	if (errno == ENOMEM) {
		printf("Error! Systema no tiene suficiente memoria");
		free(line);
		exit(-1);
	}

	int c;
	int length = 0;
	int i;

	/* leer N linea */
	for (i = 0; i < N; i++) {
		while ((c = getchar()) != EOF) {
			line[length++] = (char)c;

			/* si el tamano excede a la capacidad,
			   redimensiona el espacio asginado de forma dinamica
			 */
			if (length >= capacity) {
				capacity = capacity * 2;
				line = realloc(line, capacity * sizeof(char));
				/* comprueba que no tiene errores */
				if (errno == ENOMEM) {
					printf("Error! Systema no tiene suficiente memoria");
					free(line);
					exit(-1);
				}
			}

			if (c == NEWLINE)
				break;
		}
		/* c string termina con '\0' */
		line[length] = NULL_CHAR;
		length = 0;
		fputs(line, stdout);
	}
	free(line);
	return 0;
}

extern int tail(int N) {
	/* declaracion de variables */
	char ** lines;
	size_t * capacities;
	int lineNumber = 0;
	size_t characterNumber = 0;
	int c;
	int index = 0;
	int i;

	/* inicializa los punteros */
	lines = (char **)malloc((size_t)N * sizeof(char*));
	capacities = (size_t *)malloc((size_t)N * sizeof(size_t));
	for (i = 0; i < N; i++) {
		capacities[i] = MIN_BUF_SIZE;
		lines[i] = malloc(capacities[i] * sizeof(char));
		/* comprueba que no tiene errores */
		if (errno == ENOMEM) {
			printf("Error! Systema no tiene suficiente memoria");
			/* libera los espacios */
			for (; i >= 0; i--) {
				free(lines[i]);
			}
			exit(-1);
		}
	}

	/* lee x linea hasta que se encuentra EOF */
	while ((c = getchar()) != EOF) {
		/* resize buffer */
		if (characterNumber >= capacities[index]) {
			capacities[index] = capacities[index] * 2 + 1;
			lines[index] = realloc(lines[index], capacities[index] * sizeof(char));
			/* comprueba que no tiene errores */
			if (errno == ENOMEM) {
				printf("Error! Systema no tiene suficiente memoria");
				/* libera los espacios */
				for (i = 0; i < N; i++) {
					free(lines[i]);
				}
				exit(-1);
			}
		}
		lines[index][characterNumber++] = (char)c;

		/* si encuentra el comienzo de nueva linea */
		if (c == NEWLINE) {
			lines[index][characterNumber] = NULL_CHAR;
			/* reinicia los valores */
			characterNumber = 0;
			index = ++lineNumber % N;
		}
	}
	if (characterNumber > 0) {
		lines[index][characterNumber] = NULL_CHAR;
		lineNumber += 1;
	}
	else {
		/* si el usuario pulsa ctrl+d y no hay nada en el buffer 
		   no contamos como un linea nueva */
		index = (index + N - 1) % N;
	}

	/*
	 * si usuario introduzca mas de N, solo muestra N ultimos
	 */
	if (lineNumber > N) {
		for (i = 1; i <= N; i++) {
			fputs(lines[(index + i) % N], stdout);
		}

	}
	/* si el usuario introduzca x de linea menos de lo que pide
	   solo muestra x linea
	 */
	else {
		for (i = 0; i < N; i++) {
			fputs(lines[i % N], stdout);
		}
	}
	/* libera la memoria asignada */
	for (i = 0; i < N; i++) {
		free(lines[i]);
	}
	/* liberamos lines y capacities */
	free(lines);
	free(capacities);
	return 0;
}

extern int longlines(int N) {
	/* declaracion de variables */
	/* TODO: ahora el problema de mostrar menos lineas */
	char ** lines;
	size_t * lengths;

	size_t capacity = MIN_BUF_SIZE;
	/* initialize ptrs */
	char * line;
	size_t length = 0;

	int lineNumber = 0;
	int c;

	int i;

	/* inicializamos la memoria */
	lines = (char **)malloc((size_t)N * sizeof(char *));
	lengths = (size_t *)malloc((size_t)N * sizeof(size_t));
	line = malloc(capacity * sizeof(char));
	/* lee hasta que el usuario pulsa ctrl + d */
	while ((c = getchar()) != EOF) {
		/* redimensiona buffer */
		if (length + 1 >= capacity) {
			capacity = capacity * 2;
			line = realloc(line, capacity * sizeof(char));
			/* comprueba que no tiene errores */
			if (errno == ENOMEM) {
				printf("Error! Systema no tiene suficiente memoria");
				/* libera los espacios */
				for (i = 0; i < lineNumber; i++) {
					free(lines[i]);
				}
				free(line);
				exit(-1);
			}
		}

		/* guarda el caracter */
		line[length++] = (char)c;

		if (c == NEWLINE) {
			line[length] = NULL_CHAR;

			lineNumber += 1;

			/* si la linea es menor que N guardamos directamente */
			if (lineNumber <= N) {
				i = lineNumber - 1;
				lines[i] = line;
				lengths[i] = length;
			}
			/* si la linea es mayor que N y su longitud es mayor que la linea mas corta */
			else if (lengths[N - 1] < length) {
				i = N - 1;
				free(lines[i]);
				lines[i] = line;
				lengths[i] = length;
			}
			/* ordenamos */
			for (; i > 0; i--) {
				if (lengths[i - 1] < lengths[i]) {
					line = lines[i - 1];
					lines[i - 1] = lines[i];
					lines[i] = line;
					length = lengths[i - 1];
					lengths[i - 1] = lengths[i];
					lengths[i] = length;
				}
				else {
					break;
				}
			}

			/* reinicia las variables auxiliares */
			capacity = MIN_BUF_SIZE;
			line = malloc(capacity * sizeof(char));
			/* comprueba que no tiene errores */
			if (errno == ENOMEM) {
				printf("Error! Systema no tiene suficiente memoria");
				/* libera los espacios */
				for (i = 0; i < lineNumber; i++) {
					free(lines[i]);
				}
				free(line);
				exit(-1);
			}
			length = 0;
		}
	}
	/* lea la ultima linea */
	line[length] = NULL_CHAR;

	/* repite la logica de while para caso de la ultima linea */
	lineNumber += 1;

	/* si la linea es menor que N guardamos directamente */
	if (lineNumber <= N) {
		i = lineNumber - 1;
		lines[i] = line;
		lengths[i] = length;
	}
	/* si la linea es mayor que N y su longitud es mayor que la linea mas corta */
	else if (lengths[N - 1] < length) {
		i = N - 1;
		free(lines[i]);
		lines[i] = line;
		lengths[i] = length;
	}
	/* ordenamos */
	for (; i > 0; i--) {
		if (lengths[i - 1] < lengths[i]) {
			line = lines[i - 1];
			lines[i - 1] = lines[i];
			lines[i] = line;
			length = lengths[i - 1];
			lengths[i - 1] = lengths[i];
			lengths[i] = length;
		}
		else {
			break;
		}
	}

	/* si el usuario introduzca x de linea menos de lo que pide
	 * solo muestra x linea
	 */
	N = lineNumber < N ? lineNumber : N;
	for (i = 0; i < N; i++) {
		fputs(lines[i], stdout);

		/* despues de mostrar ya podemos liberar la memoria */
		free(lines[i]);
	}
	/* liberamos lines y lengths */
	free(lines);
	free(lengths);
	return 0;
}