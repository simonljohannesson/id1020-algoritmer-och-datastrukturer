#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <unistd.h>

/*
    Usage:

    To generate random numbers, run with two arguments.

    arg1: n, the number of random numbers to add to the file
    arg2: max_int, the largest permissable integer of the random numbers
*/
void main(int argc, char **argv){
    if(argc != 3){
        return;
    }
    int n = atoi(argv[1]);
    int max_int = atoi(argv[2]);


    // set up random with a high seed
    srandom(time(NULL));

    for(int i = 0; i < n; ++i){
        int rand_val = (int) max_int*(((double) random())/((double) RAND_MAX));
        fprintf(stdout, "%d\n", rand_val);
    }
}