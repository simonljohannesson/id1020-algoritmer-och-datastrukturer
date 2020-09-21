/******************************************************************************************
 *                                                                                        *
 *                                                                                        *
 * RENAME FILE helper.c TO helper.h IF YOU WISH TO COMPILE ANY FILE THAT DEPEND ON IT!!!! *
 *                                                                                        *
 *                                                                                        *
 *****************************************************************************************/
/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-21
 *  Updated:        
 *  Solves problem: Lab 2, assignment 5.
 *  Usage:          Compile and run with one single argument.
 *                  The argument should be an integer, and is the length of the input
 *                  that should be sorted.
 *                  Ex: 'mergesort.o 10' then input 10 numbers for sorting
 *  Based on:       https://algs4.cs.princeton.edu/22mergesort/Merge.java.html
 */
#include <stdio.h>
#include "helper.h"
#include <string.h>

void insertion_sort(int* a, const int len){
    for(int i = 1; i < len; ++i){
        for(int j = i; j > 0  &&  a[j] < a[j-1]; --j){
            int swap = a[j];
            a[j] = a[j-1];
            a[j-1] = swap;
        }
    }
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

        if(len <= 20) print_array(a, 0, len-1);
        
        insertion_sort(a, len);

        if(len <= 20) print_array(a, 0, len-1);

        if(argc > 2 && strcmp(argv[2], "issorted") == 0){
            printf(is_sorted(a, len)? "List is sorted\n":"List is not sorted\n");
        }
    }
}