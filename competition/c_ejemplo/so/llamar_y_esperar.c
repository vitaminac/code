#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <sys/wait.h>
#include <string.h>

int main(int argc, char * argv[]) {
	if (argc > 1) {
		if (fork() == 0) {
			execv(argv[1], argv + 1);
		}
		else {
			wait(NULL);
			exit(EXIT_SUCCESS);
		}
	}
	printf("Error: %s\n", strerror(errno));
	return -1;
}