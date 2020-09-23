







/**************************************************************************************
 *                                                                                    *
 *                                                                                    *
 * RENAME THIS FILE TO helper.h IF YOU WISH TO COMPILE ANY FILE THAT DEPEND ON IT!!!! *
 *                                                                                    *
 *                                                                                    *
 *                                                                                    *
 *************************************************************************************/




























/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-18
 *  Updated:        
 *  Solves problem: Contains functions that are needed for nearly every assignment of lab 2.
 *  Usage:          
 *  Based on:       
 */
#ifndef SORTHELPER
#define SORTHELPER
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
    printf("]\n");
}

/* performs insertion sort on part of an array */
void insertion_sort_part(int *a, int lo, int hi){
    for(int i = lo; i <= hi; ++i){
        for(int j = i; j > lo  &&  a[j] < a[j-1]; --j){
        
            int swap = a[j];
            a[j] = a[j-1];
            a[j-1] = swap;
        }
    }
}

/* returns 1 if list is sorted otherwise 0 */
int is_sorted(int *a, int len){
    for(int i = 0; i < len-1; ++i){
        if (a[i] > a[i+1]) return 0;
    }
    return 1;
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
    while((fgets(input, buffer, stdin)) != NULL){
        int val = atoi(input);
        (*array)[index++] = val;
        if(!(index < len)) break;
    }
    return 1;
}

#endif