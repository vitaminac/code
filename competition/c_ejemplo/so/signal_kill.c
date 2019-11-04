#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <time.h>
#include <sys/types.h>
#include <signal.h>

FILE *file;

void handle(int num) {
	printf("hola");
	srand(time(NULL));
	fprintf(file, "%d\n", rand());
	fflush(file);
	signal(SIGUSR1, handle);
}

int main(int argc, char * argv[]) {
	char buffer[4098];
	int pipeline[2];
	pid_t pid;

	pipe(pipeline);
	if ((pid = fork()) == 0) {
		close(pipeline[0]);
		file = fdopen(pipeline[1], "w");
		signal(SIGUSR1, handle);
		while (1);
	}
	else {
		close(pipeline[1]);
		file = fdopen(pipeline[0], "r");
		while (fgets(buffer, 4098, stdin) != NULL) {
			kill(pid, SIGUSR1);
			fgets(buffer, 4098, file);
			fputs(buffer, stdout);
			fflush(stdout);
		}
		fclose(file);
	}
}