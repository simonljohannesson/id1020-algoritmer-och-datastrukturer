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

void sort_cutoff(int *a, int *aux, int lo, int hi, int cutoff_len){
    // base case (cutoff)
    if (hi-lo <= cutoff_len) {
        insertion_sort_part(a, lo, hi);
        return;
    }
    // break up list into two
    int mid = (hi+lo)/2;
    sort_cutoff(a, aux, lo, mid, cutoff_len);
    sort_cutoff(a, aux, mid+1, hi, cutoff_len);
    merge(a, aux, lo, mid, hi);
}



// void sort_cutoff(int *a, int *aux, int lo, int hi){
//     // base case (cutoff)
//     if (hi-lo <= 500) {
//         insertion_sort_part(a, lo, hi);
//         return;
//     }
//     // if (hi <= lo) return;
//     // break up list into two
//     int mid = (hi+lo)/2;
//     sort_cutoff(a, aux, lo, mid);
//     sort_cutoff(a, aux, mid+1, hi);
//     merge(a, aux, lo, mid, hi);
// }


void merge_sort(int *a, int len){
    int* aux = (int*) malloc(sizeof(int)*len);
    sort(a, aux, 0, len-1);
}

// void merge_sort_cutoff(int *a, int len){
void merge_sort_cutoff(int *a, int len, int cutoff_len){
    int* aux = (int*) malloc(sizeof(int)*len);
    sort_cutoff(a, aux, 0, len-1, cutoff_len);
    // sort_cutoff(a, aux, 0, len-1);
}

void main(int argc, char **argv){
    if(argc > 2){
        int *a;
        int len = atoi(argv[1]);
        int cutoff = atoi(argv[2]);
        int success = array_from_stdin(&a, len);
        if(!success){
            printf("Could not allocate memory for input that is %d long.", len);
            return;
        }

        if(len <= 20) print_array(a, 0, len-1);
        
        merge_sort_cutoff(a, len, cutoff);
        // merge_sort_cutoff(a, len);

        if(len <= 20) print_array(a, 0, len-1);

        if((argc == 4) && strcmp(argv[3], "issorted") == 0){
            printf(is_sorted(a, len)? "List is sorted\n":"List is not sorted\n");
        }
    }
}