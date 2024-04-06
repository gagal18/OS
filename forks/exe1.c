#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

int main(){
	int i;
	for(i = 0; i < 2; i++){
		int pid = fork();
    	printf("Current PID: %d\n", getppid()); // Print parent process ID		
        if (pid == 0) {
            // Child process
            printf("Child %d PID: %d\n", i+1, getpid()); // Print child process ID
    		printf("Parent PID: %d\n", getppid()); // Print parent process ID		
        } else if (pid < 0) {
            // Error handling if fork fails
            perror("fork failed"); // Handle fork failure
            exit(1);
        }
	}
	return 0;
}

