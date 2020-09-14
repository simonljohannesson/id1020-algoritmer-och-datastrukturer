#include <stdio.h>
#include <stdlib.h>

void print_array(const int* const array, int length){
    printf("[");
    for (int i = 0; i < length; i++){
        printf("%d", array[i]);
        if (i != length -1){
            printf(", ");
        }
    }
    printf("]\n");
}



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




void main(){
    int arr[] = {3, 5, 1, 8, 4, 9, 2, 6};
    int len = 8;
    print_array(arr, len);
    merge_sort(arr, len);
    print_array(arr, len);
}