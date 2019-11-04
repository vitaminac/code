#include <stdio.h>
#include <stdbool.h>
#include <pthread.h>
#include <unistd.h>

#define NUM_FILOSOFOS 5

typedef enum {
	pensando = 0, esperando, comiendo
} estado;

bool palillo[NUM_FILOSOFOS] = { false };
pthread_cond_t espera[NUM_FILOSOFOS];
pthread_mutex_t mutex;
estado filosofo_estado[NUM_FILOSOFOS] = { pensando };

void imprimir_estado(estado filosofo_estado, int filosofo) {
	switch (filosofo_estado)
	{
	case pensando:
		printf("Filosofo %d pensando\n", filosofo);
		break;
	case esperando:
		printf("Filosofo %d esperado\n", filosofo);
		break;
	case comiendo:
		printf("Filosofo %d comiendo\n", filosofo);
		break;
	default:
		break;
	}
}

void print_estado() {
	int i;
	printf("\n");
	for (i = 0; i < NUM_FILOSOFOS; i++) {
		imprimir_estado(filosofo_estado[i], i);
	}
	printf("\n");
}

/*
 * Un programa en C que modelice la cena de los Filosofos
 */
void * filosofo(void * arg) {
	int filosofo = *((int *)arg);
	while (true) {
		/*print_estado();*/
		filosofo_estado[filosofo] = pensando;
		/* imprimir un mensaje por pantalla tipo "Filoso i pensando" siendo i el numero de filosofo */
		imprimir_estado(filosofo_estado[filosofo], filosofo);
		/* Esperar con un sleep n tiempo aleatorio enre 1 y 5 segundos */
		usleep(rand() % 5);

		/* Imprimir un mensaje por pantalla tipo "Filosofo i quiere comer" */
		printf("Filosofo %d quiere comer\n", filosofo);
		filosofo_estado[filosofo] = esperando;
		imprimir_estado(filosofo_estado[filosofo], filosofo);

		pthread_mutex_lock(&mutex);
		while (palillo[filosofo] || palillo[(filosofo + 1) % NUM_FILOSOFOS])
			pthread_cond_wait(&espera[filosofo], &mutex);

		/* seccion critica: el filosofo coger los palillos */
		palillo[filosofo] = true;
		palillo[(filosofo + 1) % NUM_FILOSOFOS] = true;
		pthread_mutex_unlock(&mutex);

		/* Cuando tenga el control de los palillos,
		imprimir un mensaje en pantalla tipo
		"Filosofo i comiendo" */
		filosofo_estado[filosofo] = comiendo;
		imprimir_estado(filosofo_estado[filosofo], filosofo);
		/* Esperar con un sleep un tiempo aleatorio entre 1 y 5 segundos */
		usleep(rand() % 5);
		/* Soltar los palillos */

		/* seccion critica */
		pthread_mutex_lock(&mutex);
		palillo[filosofo] = false;
		palillo[(filosofo + 1) % NUM_FILOSOFOS] = false;
		/* notifica al filoso anterior y filosofo posterior */
		pthread_cond_signal(&espera[(filosofo - 1 + NUM_FILOSOFOS) % NUM_FILOSOFOS]);
		pthread_cond_signal(&espera[(filosofo + 1) % NUM_FILOSOFOS]);
		pthread_mutex_unlock(&mutex);
	}
	return NULL;
}

int main()
{
	int i;
	int filosofo_ids[NUM_FILOSOFOS];
	printf("hello from ejercicio 3!\n");
	pthread_mutex_init(&mutex, NULL);
	pthread_t threads[NUM_FILOSOFOS];
	for (i = 0; i < NUM_FILOSOFOS; i++) {
		filosofo_ids[i] = i;
		pthread_cond_init(&espera[i], NULL);
		pthread_create(&threads[i], NULL, filosofo, &filosofo_ids[i]);
	}
	for (i = 0; i < NUM_FILOSOFOS; i++) {
		pthread_join(threads[i], NULL);
	}
	return 0;
}