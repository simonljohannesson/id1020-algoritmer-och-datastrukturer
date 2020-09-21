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
#include <stdlib.h>
#include <string.h>
#include "helper.h"


void merge(int *a, int *aux, int lo, int mid, int hi){
    // copy subsection to aux
    for(int i = lo; i <= hi; ++i){
        aux[i] = a[i];
    }
    // ordered merge to a
    for(int i = lo, j = mid+1, k = lo; k < hi+1; ++k){
        if      (i > mid){
            a[k] = aux[j++];
        }         
        else if (j > hi){
            a[k] = aux[i++];}
        else if (aux[i] < aux[j]) {
            a[k] = aux[i++];
        }
        else {
            a[k] = aux[j++];
        }
    }
}

void sort(int *a, int *aux, int lo, int hi){
    // base case
    if (hi <= lo) return;
    // break up list into two
    int mid = (hi+lo)/2;
    sort(a, aux, lo, mid);
    sort(a, aux, mid+1, hi);
    merge(a, aux, lo, mid, hi);
}


void merge_sort(int *a, int len){
    int* aux = (int*) malloc(sizeof(int)*len);
    sort(a, aux, 0, len-1);
}



void main(int argc, char **argv){
    if(argc > 1){
        int *a;
        int len = atoi(argv[1]);
        int success = array_from_stdin(&a, len);
        if(!success){
            printf("Could not allocate memory for input that is %d long.", len);
            return;
        }

        if(len <= 20) print_array(a, 0, len-1);
        
        merge_sort(a, len);

        if(len <= 20) print_array(a, 0, len-1);

        if(argc > 2 && strcmp(argv[2], "issorted") == 0){
            printf(is_sorted(a, len)? "List is sorted\n":"List is not sorted\n");
        }
    }
}