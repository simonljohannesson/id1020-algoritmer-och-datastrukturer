#include <stdio.h>

void print_array(const int* const array, const int const length){
    printf("[");
    for (int i = 0; i < length; i++){
        printf("%d", array[i]);
        if (i != length -1){
            printf(", ");
        }
    }
    printf("]\n");
}

void insertion_sort(int* a, const int len){
    for(int i = 1; i < len; ++i){
        for(int j = i; j > 0  &&  a[j] < a[j-1]; --j){
            int swap = a[j];
            a[j] = a[j-1];
            a[j-1] = swap;
        }
    }
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

void main(void){
    int a1[15] = {11, 3, 44, 2, 7, 0, 3, 10, 40, 9, 8, 32, 12, 22, 55};
    int len = 15;
    print_array(a1, len);
    insertion_sort(a1, len);
    print_array(a1, len);

    int a2[] = {2,2,2,2,2,2,2, 7, 3, 5, 1, 9, 7, 10,10,10,10,10};
    print_array(a2, 18);
    insertion_sort_part(a2, 7, 12);
    print_array(a2, 18);
}