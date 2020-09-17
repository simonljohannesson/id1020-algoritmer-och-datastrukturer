#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <unistd.h>

/*
    Usage:

    arg1: n, the number of ordered numbers to send to stdout
*/
void main(int argc, char **argv){
    if(argc != 2){
        return;
    }
    int n = atoi(argv[1]);


    for(int i = 0; i < n; ++i){
        fprintf(stdout, "%d\n", i);
    }
}