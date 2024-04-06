#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
int main()
{
	printf("m\n");
	int p = fork();
	if(p == 0)
	{
	    printf("child\n");
	    printf("n\n");
	    printf("created a => %d\n", p);
	    printf("o\n");
	    execlp("/bin/ls", "ls -a", NULL);
	}
	else{
	    printf("p\n");
	    printf("parent\n");
	}
	printf("%d \n",p);
	return 0;
}
