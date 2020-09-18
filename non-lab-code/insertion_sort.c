#include <stdio.h>
#include "helper.h"
#include <string.h>

void insertion_sort(int* a, const int len){
    for(int i = 1; i < len; ++i){
        for(int j = i; j > 0  &&  a[j] < a[j-1]; --j){
            int swap = a[j];
            a[j] = a[j-1];
            a[j-1] = swap;
        }
    }
}

void insertion_sort_print_swaps(int* a, const int len){
    int swaps = 0;
    for(int i = 1; i < len; ++i){
        for(int j = i; j > 0  &&  a[j] < a[j-1]; --j){
            int swap = a[j];
            a[j] = a[j-1];
            a[j-1] = swap;
            ++swaps;
        }
    }
    printf("Number of swaps: %d\n", swaps);
}

// /* performs insertion sort on part of an array */
// void insertion_sort_part(int *a, int lo, int hi){
//     for(int i = lo; i <= hi; ++i){
//         for(int j = i; j > lo  &&  a[j] < a[j-1]; --j){
//             int swap = a[j];
//             a[j] = a[j-1];
//             a[j-1] = swap;
//         }
//     }
// }

/* performs insertion sort on part of an array - decending implementation */
void insertion_sort_part_decending(int *a, int lo, int hi){
    for(int i = lo; i <= hi; ++i){
        for(int j = i; j > lo  &&  a[j] < a[j-1]; --j){
            int swap = a[j];
            a[j] = a[j-1];
            a[j-1] = swap;
        }
    }
    for(int i = lo, j = hi; i < j; ++i, --j){
        int swap = a[j];
        a[j] = a[i];
        a[i] = swap;
    }
}
void insertion_sort_part_decending_better(int *a, int lo, int hi){
    for(int i = lo; i <= hi; ++i) a[i] *=-1;
    for(int i = lo; i <= hi; ++i){
        for(int j = i; j > lo  &&  a[j] < a[j-1]; --j){
            int swap = a[j];
            a[j] = a[j-1];
            a[j-1] = swap;
        }
    }
    for(int i = lo; i <= hi; ++i) a[i] *=-1;
}

/* performs insertion sort on part of an array - negative values (assignment 4) */
void insertion_sort_part_negative(int *a, int lo, int hi){
    for(int i = 1; i <= hi; ++i){
        for(int j = i; j > lo  &&  a[j] < 0  &&  a[j-1] >= 0; --j){
            int swap = a[j];
            a[j] = a[j-1];
            a[j-1] = swap;
        }
    }
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

        if(len <= 20) print_array(a, 0, len-1);
        
        insertion_sort(a, len);

        if(len <= 20) print_array(a, 0, len-1);

        if(argc > 2 && strcmp(argv[2], "issorted") == 0){
            printf(is_sorted(a, len)? "List is sorted\n":"List is not sorted\n");
        }
    }
    else{
        // int a1[15] = {11, 3, 44, 2, 7, 0, 3, 10, 40, 9, 8, 32, 12, 22, 55};
        // int len = 15;
        // print_array(a1, 0, len-1);
        // insertion_sort_print_swaps(a1, len);
        // print_array(a1,0, len-1); 
        int a1[] = {11, 3, 44, 2, 7, 0, 3, 10, 40, 9, 8, 32, 12, 22, 55, 4};
        int len = 16;
        print_array(a1, 0, len-1);
        insertion_sort_part_decending_better(a1, 0, len-1);
        print_array(a1, 0, len-1);
        
    }


    // int a1[15] = {11, 3, 44, 2, 7, 0, 3, 10, 40, 9, 8, 32, 12, 22, 55};
    // int len = 15;
    // print_array(a1, len);
    // insertion_sort(a1, len);
    // print_array(a1, len);

    // int a2[] = {2,2,2,2,2,2,2, 7, 3, 5, 1, 9, 7, 10,10,10,10,10};
    // print_array(a2, 18);
    // insertion_sort_part_decending(a2, 7, 12);
    // print_array(a2, 18);

    // int a3[] = {3,1,12,23,21,56,2, 7, -6, 5, 78, 9, -4, -11,-10,12,18,19};
    // print_array(a3, 18);
    // insertion_sort_part_decending(a3, 0, 17);
    // print_array(a3, 18);

    // int a4[] = {3,1,12,23,21,56,2, 7, -6, 5, 78, 9, -4, -11,-10,12,18,19};
    // print_array(a4, 18);
    // insertion_sort_part_negative(a4, 0, 17);
    // print_array(a4, 18);
}