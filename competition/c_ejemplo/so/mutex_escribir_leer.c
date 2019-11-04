#include <stdio.h>
#include <pthread.h>

#define BUFFER_SIZE 10000

pthread_mutex_t mutex;
int buffer[BUFFER_SIZE] = { 0 };

/*
 * Escriba un programa en C llamado mutex que cree dos threads
 * que accedan simultaneamente a un buffer de 10000 enteros.
 * Uno de ello lee en el buffer y el otro escribe en el mismo.
 * El thread escritor, debe escribir el mismo valor
 * en todos los elemenetos del buffer
 * incrementando en uno el valor en cada pasada. El thread lector
 * debe ir comprobando que todos los numeros del buffer son iguales.
 */
void * escritor(void * nothing) {
	int i;
	while (1) {
		pthread_mutex_lock(&mutex);
		printf("Soy escritor\n");
		for (i = 0; i < BUFFER_SIZE; i++) {
			buffer[i] = buffer[i] + 1;
		}
		pthread_mutex_unlock(&mutex);
	}
	return NULL;
}

void * lector(void * nothing) {
	int i;
	while (1) {
		pthread_mutex_lock(&mutex);
		printf("Soy lector\n");
		for (i = 1; i < BUFFER_SIZE; i++) {
			if (buffer[i] != buffer[i - 1]) {
				printf("Hay distintos valroes en el array");
				break;
			}
		}
		pthread_mutex_unlock(&mutex);
	}
	return NULL;
}

int maint2()
{
	pthread_mutex_init(&mutex, NULL);
	printf("hello from ejercicio 2!\n");
	pthread_t thread1;
	pthread_t thread2;
	pthread_create(&thread1, NULL, escritor, NULL);
	pthread_create(&thread2, NULL, lector, NULL);
	pthread_join(thread1, NULL);
	pthread_join(thread2, NULL);

	pthread_mutex_destroy(&mutex);
	return 0;
}