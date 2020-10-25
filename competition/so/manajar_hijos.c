#include <stdio.h>
#include <signal.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

pid_t hijos[4];
int i;

void manejador_hijo(int sig)
{
	printf("Soy el hijo %d con pid %d, corriendo . . . \n", i, getpid());
	sleep(1);
	if (i != 0)
	{
		printf("Termina. Paso el testigo al hijo %d con pid %d\n", i - 1, hijos[i - 1]);
		kill(hijos[i - 1], SIGUSR2); /* Mando una señal al que le toca correr */
	}
	else
		printf("Terminó!\n");
	exit(0);
}

int mainp4(int argc, char * argv[]) {
	signal(SIGUSR2, manejador_hijo); /* Asocio el manejador_hijo a la señal SIGUSR2 */
	for (i = 0; i < 4; i++)
	{
		hijos[i] = fork();
		if (hijos[i] == 0) pause();
	}
	kill(hijos[3], SIGUSR2);
	for (i = 0; i < 4; i++) wait(NULL);
}