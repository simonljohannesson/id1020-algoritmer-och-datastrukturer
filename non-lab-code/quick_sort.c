#include <stdio.h>
#include <stdlib.h>

/* prints an array between indices provided (inclusive) */
void print_array(const int* const array, int lo, int hi){
    printf("[");
    for (int i = lo; i <= hi; i++){
        printf("%d", array[i]);
        if (i != hi){
            printf(", ");
        }
    }
    printf("]");
}

/* returns 1 if list is sorted otherwise 0 */
int is_sorted(int *a, int len){
    for(int i = 0; i < len-1; ++i){
        if (a[i] > a[i+1]) return 0;
    }
    return 1;
}

/* performs insertion sort on part of an array */
void insertion_sort_part(int *a, int lo, int hi){
    for(int i = 1; i <= hi; ++i){
        for(int j = i; j > lo  &&  a[j] < a[j-1]; --j){
            int swap = a[j];
            a[j] = a[j-1];
            a[j-1] = swap;
        }
    }
}

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

void sort_cutoff(int *a, int lo, int hi, int cutoff_len){
    // check if should cutoff (new base case)
    if(hi-lo <= cutoff_len){
        insertion_sort_part(a, lo, hi);
        return;
    }
    // base case
    // if (hi <= lo) return;
    // partition
    int mid = partition(a, lo, hi);
    sort(a, lo, mid-1);
    sort(a, mid+1, hi);
}

void main(void){
    int a[] = {10, 16, 123, 2, 223, 4, 18, 3, 12, 5, 932, 1, 296, 6, 15, 32, 342, 92};
    printf(is_sorted(a, 18)? "List is sorted\n":"List is not sorted\n");
    sort(a, 0, 17);
    printf(is_sorted(a, 18)? "List is sorted\n":"List is not sorted\n");

    int b[] = {10, 16, 123, 2, 223, 4, 18, 3, 12, 5, 932, 1, 296, 6, 15, 32, 342, 92};
    printf(is_sorted(b, 18)? "List is sorted\n":"List is not sorted\n");
    sort_cutoff(b, 0, 17, 4);
    printf(is_sorted(b, 18)? "List is sorted\n":"List is not sorted\n");
}