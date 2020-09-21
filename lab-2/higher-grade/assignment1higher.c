/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-21
 *  Updated:        
 *  Solves problem: Lab 2, assignment 1 higher grade.
 *  Usage:          Compile and run with one argument.
 *                  arg 1: integer, length of input                
 *                  Ex: 'assignment1higher.o 10' then input 10 numbers for sorting
 *  Based on:       https://algs4.cs.princeton.edu/21elementary/Insertion.java.html
 */
#include <stdio.h>
#include "helper.h"
#include <string.h>


void insertion_sort_part_decending(int *a, int lo, int hi){
    /* flip sign of all values */
    for(int i = lo; i <= hi; ++i) a[i] *=-1;
    /* normal insertion sort */
    for(int i = lo; i <= hi; ++i){
        for(int j = i; j > lo  &&  a[j] < a[j-1]; --j){
            int swap = a[j];
            a[j] = a[j-1];
            a[j-1] = swap;
        }
    }
    /* flip back sign of all values */
    for(int i = lo; i <= hi; ++i) a[i] *=-1;
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
        
        insertion_sort(a, len);

        print_array(a, 0, len-1);

        if(argc > 2 && strcmp(argv[2], "issorted") == 0){
            printf(is_sorted(a, len)? "List is sorted\n":"List is not sorted\n");
        }
    }

}