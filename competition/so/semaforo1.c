#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>

sem_t semaphore;

/*
 * Escriba un programa en C llamado orden
 * que cree dos threads y mediante el uso de un semaforo
 * se fuerce que siempre uno de ellos escriba por pantalla
 * antes que el otro
 */

void * orden1(void * nothing) {
	printf("Soy thread 1\n");
	sem_post(&semaphore);
	return NULL;
}

void * orden2(void * nothing) {
	sem_wait(&semaphore);
	printf("Soy thread 2\n");
	return NULL;
}

int maint1()
{
	sem_init(&semaphore, 0, 0);
	printf("hello from ejercicio 1!\n");
	pthread_t thread1;
	pthread_t thread2;
	pthread_create(&thread1, NULL, orden1, NULL);
	pthread_create(&thread2, NULL, orden2, NULL);
	pthread_join(thread1, NULL);
	pthread_join(thread2, NULL);
	sem_destroy(&semaphore);
	return 0;
}