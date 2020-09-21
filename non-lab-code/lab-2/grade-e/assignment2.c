/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-18
 *  Updated:        
 *  Solves problem: Lab 2, assignment 2.
 *  Usage:          Compile and run with one single argument.
 *                  The argument should be an integer, and is the length of the input
 *                  that should be sorted.
 *                  Ex: 'assignment2.o 10' then input 10 numbers for sorting
 *  Based on:       https://algs4.cs.princeton.edu/21elementary/Insertion.java.html
 */

#include <stdio.h>
#include <string.h>
#include "helper.h"

/* insertion sort that prints how many swaps was needed to sort a list */
void insertionsort_print_swaps(int* a, const int len){
    int swaps = 0;
    for(int i = 1; i < len; ++i){
        for(int j = i; j > 0  &&  a[j] < a[j-1]; --j){
            int swap = a[j];
            a[j] = a[j-1];
            a[j-1] = swap;
            ++swaps;
        }
    }
    printf("Number of swaps: %d\n", swaps);
}

void main(int argc, char** argv){

    if(argc > 1){
        int *a;
        int len = atoi(argv[1]);
        int success = array_from_stdin(&a, len);
        if(!success){
            printf("Could not allocate memory for input that is %d long.", len);
            return;
        }
        print_array(a, 0, len-1);
        /* sort the array */
        insertionsort_print_swaps(a, len);
        print_array(a, 0, len-1);

        if(argc > 2 && strcmp(argv[2], "issorted") == 0){
            printf(is_sorted(a, len)? "List is sorted\n":"List is not sorted\n");
        }
    }
}