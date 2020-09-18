/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-18
 *  Updated:        
 *  Solves problem: Lab 2, assignment 4.
 *  Usage:          Compile and run with one single argument.
 *                  The argument should be an integer, and is the length of the input
 *                  that should be sorted.
 *                  Ex: 'assignment4.o 10' then input 10 numbers for sorting
 *  Based on:       https://algs4.cs.princeton.edu/21elementary/Insertion.java.html
 */
#include <stdio.h>
#include "helper.h"

/* moves negative values to the beginning of the array */
void sort_negative(int *a, int lo, int hi){
    for(int i = lo; i <= hi; ++i){
        for(int j = i; j > lo  &&  a[j] < 0  &&  a[j-1] >= 0; --j){
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
        print_array(a, 0, len-1);
        /* sort negative numbers in array */
        sort_negative(a, 0, len-1);
        print_array(a, 0, len-1);

    }
}



/*
Assignment text:

Implement a function in C which takes an array of integers (both positive and negative)
and orders the elements in the array so that all negative elements come before the positive.
You are not allowed to sort the array - only collect all negative values first. The
algorithm should only use O(1) extra memory (i.e. be in-place Wikipedia: In-place algorithm
(Länkar till en externa sida.)Länkar till en externa sida.)
*/