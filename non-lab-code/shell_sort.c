#include <stdio.h>
int list_test1[] = {2, 8, 5, 4, 1, 6, 7, 9, 0};
int list_test2[] = {2, 8, 5, 4, 1, 6, 7, 9, 0};
int list_len = 9;
int gaps_array[] = {4, 1};
int gaps_arr_len = 2;


void print_array(const int* const array, const int* const length){
    printf("[");
    for (int i = 0; i < *length; i++){
        printf("%d", array[i]);
        if (i != *length -1){
            printf(", ");
        }
    }
    printf("]\n");
}

void shell_sort(int* array, const int* const length, const int* const gaps_array, const int* const gap_len){
    // for every gap do a insertion sort with a gap
    for(int i = 0; i < *gap_len; i++){
        int gap = gaps_array[i];

        for(int j = 0; j < *length  &&  j+gap < *length; j++){
            printf("array[j+gap] < array[j] : %d < %d\n", array[j+gap], array[j]);
            if(array[j+gap] < array[j]){
                int swap = array[j];
                array[j] = array[j+gap];
                array[j+gap] = swap;
                print_array(array, length);
            }
        }
    }
}



void shell_sort_copy(int* a, int* a_len){

        int n = *a_len;

        // 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ... 
        int h = 1;
        while (h < n/3) h = 3*h + 1; 
        printf("new h\n");
        while (h >= 1) {
            // h-sort the array
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h ; j -= h) {
                    if(a[j] < a[j-h]){
                        int swap = a[j];
                        a[j] = a[j-h];
                        a[j-h] = swap;
                        // printf("h=%d, i=%d, j=%d, swapped list now: ", h, i, j);
                        print_array(a, a_len);
                    }
                }
            }
            h /= 3;
        }
}



void main(void){
    // printf("                                 ");
    printf("Copied sort:\n");
    print_array(list_test1, &list_len);
    shell_sort_copy(list_test1, &list_len);
    printf("\n");

    // printf("                                 ");
    printf("Own sort:\n");
    print_array(list_test2, &list_len);
    shell_sort(list_test2, &list_len, gaps_array, &gaps_arr_len);
}