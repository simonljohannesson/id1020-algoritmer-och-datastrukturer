#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <unistd.h>

/*
    Usage:

    arg1: n, the number of ordered numbers to send to stdout
    arg2: max_int, largest allowed random number
*/
void main(int argc, char **argv){
    if(argc != 3){
        return;
    }
    int n = atoi(argv[1]);
    int max_int = atoi(argv[2]);

    // set up random with a high seed
    srandom(time(NULL));

    for(int i = 1; i < n+1; ++i){

        if(i % 2 == 0) fprintf(stdout, "%d\n", i);
        else{
            int rand = ((int) max_int*(((double) random())/((double) RAND_MAX)));
            fprintf(stdout, "%d\n", rand);
        }
    }
}