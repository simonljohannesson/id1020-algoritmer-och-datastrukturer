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

/*
    puts elements at indices i, j, k in order, then puts the median on the i index

    params:
    the array and indices of the elements in the array
*/
void median_at_low_index(int *a, int i, int j, int k){
    if (k < j) swap(a, k, j);
    if (j < i) swap(a, j, i);
    if (k < j) swap(a, k, j);
    // put median element on lo
    swap(a, i, j);
}

int partition_mo3(int *a, int lo, int hi){
    int mid = (hi+lo)/2;
    // chose the pivot point and put it at lo index
    median_at_low_index(a, lo, mid, hi);
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

void sort_mo3(int *a, int lo, int hi){
    // base case
    if (hi <= lo) return;
    // partition
    int mid = partition_mo3(a, lo, hi);
    sort_mo3(a, lo, mid-1);
    sort_mo3(a, mid+1, hi);
}

void quicksort_mo3(int *a, int len){
    sort_mo3(a, 0, len-1);
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
        
        quicksort_mo3(a, len);

        if(len <= 20) print_array(a, 0, len-1);

        if(argc > 2 && strcmp(argv[2], "issorted") == 0){
            printf(is_sorted(a, len)? "List is sorted\n":"List is not sorted\n");
        }
    }
}