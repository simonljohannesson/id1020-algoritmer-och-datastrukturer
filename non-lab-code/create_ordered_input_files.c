#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <unistd.h>

/*
    Usage:

    arg1: n, the number of random numbers to add to the file
    arg2: max_int, the largest permissable integer of the random numbers
    arg3: file_name, the name of the file to append to or create
*/
void main(int argc, char **argv){
    if(argc != 4){
        return;
    }
    int n = atoi(argv[1]);
    int max_int = atoi(argv[2]);
    char *file_name = argv[3];

    FILE *fp = fopen(file_name, "a");
    // error check
    if (fp == NULL){
        printf("Could not open file: %s", file_name);
        return;
    }



    for(int i = 0; i < n; ++i){
        fprintf(fp, "%d\n", i);
    }

}