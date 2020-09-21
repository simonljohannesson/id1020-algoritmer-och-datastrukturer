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
 *  Created:        2020-09-18
 *  Updated:        
 *  Solves problem: Lab 2, assignment 3.
 *  Usage:          Compile and run with one single argument.
 *                  The argument should be an integer, and is the length of the input
 *                  that should be sorted.
 *                  Ex: 'assignment3.o 10' then input 10 numbers for sorting
 *  Based on:       https://algs4.cs.princeton.edu/21elementary/Insertion.java.html
 */
#include <stdio.h>
#include "helper.h"


/*  determines how many inversions there are in an array

    time complexity
    O(n!) -> O(n) for big n
*/
void inversions_in_array(int *a, int len){
    int inversions = 0;
    // for every integer in array
    for (int i = 0; i < len; ++i){
        // check for any integers that it is out of order with
        for(int j = i; j < len; ++j){
            if (a[i] > a[j]){
                ++inversions;
                printf("[%d, %d], [%d, %d]\n", i, a[i], j, a[j]);
            }
        }
    }
    printf("Number of inversions: %d\n", inversions);
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
        /* check the array */
        inversions_in_array(a, len);
    }
}



/*
Assignment text:

Add a method which counts the number of inversions in the 
input array and prints a list of all inversions on the format
[i,a[i]], [j, a[j]] where i and j are indices and a[i], a[j] 
are the values of the elements. Call the method from main() 
before the array is sorted. Calculate the time complexity for 
the algorithm.
*/