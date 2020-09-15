#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

/* prints an array between indices provided (inclusive) */
void print_array(const int* const array, int lo, int hi){
    printf("[");
    for (int i = lo; i <= hi; i++){
        printf("%d", array[i]);
        if (i != hi){
            printf(", ");
        }
    }
    printf("]");
}

/* takes input from stdin and puts it into an int array of size len
   user should free memory allocated by this function
   the integers received from stdin should not be bigger than max int
   or the behaviour will be undefined
   array should point to NULL

   returns 1 on success, 0 on failure
*/
int array_from_stdin(int** array, int len){
    // reset errno
    errno = 0;
    *array = (int*)malloc(sizeof(int) * len);
    // check allocation for failure
    if (errno == ENOMEM){
        errno = 0;
        return 0;
    }

    int index = 0;
    int buffer = 20;
    char input[buffer];
    while((fgets(input, buffer, stdin)) != NULL && index < len){
        int val = atoi(input);
        (*array)[index++] = val;
    }
    return 1;
}

/*
    reads stdin into an array
    arguments: num_elements, number of integers separated by newline character to read
*/
void main(int argc, char** argv){
    if(argc > 1){
        int num_elements = atoi(argv[1]);
        int *a = NULL;
        int success = array_from_stdin(&a, num_elements);
        
        if(!success) {
            printf("could not allocate memory for input");
            return;
        }
        // print_array(a, 0, num_elements-1);

        /* sort array here */

    }else{
        printf("%s%s",
                "Incorrect usage, run 'read_stdin <elements in stdinput before EOF>' ",
                "enter input and terminate with end of file character\n");
        }
    return;
}