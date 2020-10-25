#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>

int main(int argc, char * argv[]) {
	char buffer[4098];
	int pipeline1[2], pipeline2[2];
	FILE *input, *output;
	char * ptr;
	pipe(pipeline1);
	pipe(pipeline2);
	if (fork() == 0) {
		close(pipeline1[1]);
		close(pipeline2[0]);
		input = fdopen(pipeline1[0], "r");
		output = fdopen(pipeline2[1], "w");
		while (fgets(buffer, 4098, input) != NULL) {
			for (ptr = buffer; *ptr; ++ptr) {
				*ptr = toupper(*ptr);
			}
			fputs(buffer, output);
			fflush(output);
		}
	}
	else {
		close(pipeline1[0]);
		close(pipeline2[1]);
		output = fdopen(pipeline1[1], "w");
		input = fdopen(pipeline2[0], "r");
		while (fgets(buffer, 4098, stdin) != NULL) {
			fputs(buffer, output);
			fflush(output);
			fgets(buffer, 4098, input);
			fputs(buffer, stdout);
			fflush(stdout);
		}
		fclose(input);
		fclose(output);
	}
}