/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-18
 *  Updated:        
 *  Solves problem: Lab 2, assignment 1.
 *  Usage:          Compile and run with one single argument.
 *                  The argument should be an integer, and is the length of the input
 *                  that should be sorted.
 *                  Ex: 'assignment1.o 10' then input 10 numbers for sorting
 *  Based on:       https://algs4.cs.princeton.edu/21elementary/Insertion.java.html
 */

#include <stdio.h>
#include <string.h>
#include "helper.h"

/* Insertion sort that prints the content of the array that's
   being sorted after each inner loop is completed
*/
void insertionsort_verbose(int* a, const int len){
    for(int i = 1; i < len; ++i){
        for(int j = i; j > 0  &&  a[j] < a[j-1]; --j){
            int swap = a[j];
            a[j] = a[j-1];
            a[j-1] = swap;
            print_array(a, 0, len-1);
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
        print_array(a, 0, len-1);
        printf("\n");
        /* sort the array */
        insertionsort_verbose(a, len);
        printf("\n");
        print_array(a, 0, len-1);
        
        if(argc > 2 && strcmp(argv[2], "issorted") == 0){
            printf(is_sorted(a, len)? "List is sorted\n":"List is not sorted\n");
        }
    }
}


/* 
Assignment text:

Implement insertionsort. Augment the sorting process so that all the content of
the array that is being sorted is printed after each inner loop iteration. Write
a unit test in main() which allows the user to define the size of the input (N)
and then input (N) integers from stdin which is to be sorted.     
*/