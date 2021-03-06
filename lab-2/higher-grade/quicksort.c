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
 *  Solves problem: Lab 2, assignment 2 & 3 higher.
 *  Usage:          Compile and run with one single argument.
 *                  The argument should be an integer, and is the length of the input
 *                  that should be sorted.
 *                  Ex: 'quicksort.o 10' then input 10 numbers for sorting
 *  Based on:       https://algs4.cs.princeton.edu/23quicksort/Quick.java.html
 */
#include <stdio.h>
#include <stdlib.h>
#include "helper.h"
#include <string.h>

/* swaps places of elements at indices provided in array provided */
void swap(int *a, int i, int j){
        int swap = a[j];
        a[j] = a[i];
        a[i] = swap;
}

int partition(int *a, int lo, int hi){
    // chose the pivot element
    int pivot = a[lo];
    // initiate variables that will traverse the list in opposite directions
    int i = lo;
    int j = hi+1;

    // let i & j traverse list, swap elements out of order compared to the pivot
    while (1){
        // i goes left to right
        while(a[++i] < a[lo]) {
            if (i == hi) break;
        }
        // j goes right to left
        while(a[--j] > a[lo]) {
            if (j == lo) break;
        }
        // break if i & j cross each other
        if (i >= j) break;
        swap(a, i, j);
    }
    // move pivot element to correct place in array (remember it was swapped with lo)
    swap(a, lo, j);
    // return index of the pivot element
    return j;
}

void sort(int *a, int lo, int hi){
    // base case
    if (hi <= lo) return;
    // partition
    int mid = partition(a, lo, hi);
    sort(a, lo, mid-1);
    sort(a, mid+1, hi);
}

void quicksort(int *a, int len){
    sort(a, 0, len-1);
}

void main(int argc, char **argv){

    if(argc > 1){
        int *a;
        int len = atoi(argv[1]);
        // int cutoff = atoi(argv[2]);
        int success = array_from_stdin(&a, len);
        if(!success){
            printf("Could not allocate memory for input that is %d long.", len);
            return;
        }

        if(len <= 20) print_array(a, 0, len-1);
        
        quicksort(a, len);

        if(len <= 20) print_array(a, 0, len-1);

        if(argc > 3 && strcmp(argv[2], "issorted") == 0){
            printf(is_sorted(a, len)? "List is sorted\n":"List is not sorted\n");
        }
    }
}