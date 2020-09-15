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
    returns the index of the median of three elements of an array 

    params:
    the array and indices of the elements in the array to check
*/
int median(int *a, int i, int j, int k){
    /* what if there are duplicates? */
    if      (a[i]<a[j] != a[i]<a[k]) return i;
    else if (a[j]<a[i] != a[j]<a[k]) return j;
    else return k;
}

int partition(int *a, int lo, int hi){
    // chose the pivot point
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

void sort_cutoff(int *a, int lo, int hi){
    // check if should cutoff (new base case)
    if(hi-lo <= 20){
        // insertion_sort_part(a, lo, hi);
        /* insertion sort moved to the end for performance increase */
        return;
    }
    // base case
    if (hi <= lo) return;
    // partition
    int mid = partition(a, lo, hi);
    sort_cutoff(a, lo, mid-1);
    sort_cutoff(a, mid+1, hi);
}

void quicksort_cutoff(int *a, int len){
    sort_cutoff(a, 0, len-1);
    insertion_sort_part(a, 0, len-1);
}

/* compare function to benchmark against builtin qsort */
int comp(const void* p1, const void* p2){
    return *(int*)p1 - *(int*)p2;
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
        
        quicksort_cutoff(a, len);
        // qsort(a, len, sizeof(int), &comp);

        if(len <= 20) print_array(a, 0, len-1);

        if(argc > 3 && strcmp(argv[2], "issorted") == 0){
            printf(is_sorted(a, len)? "List is sorted\n":"List is not sorted\n");
        }
    }
}