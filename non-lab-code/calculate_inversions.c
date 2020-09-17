#include <stdio.h>
#include "helper.h"


// time complexity
// O(n!) -> O(n) for big n

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

void main(void){
    int a1[15] = {11, 3, 44, 2, 7, 0, 3, 10, 40, 9, 8, 32, 12, 22, 55};
    int len = 15;
    print_array(a1, 0, len-1);
    inversions_in_array(a1, len);
    print_array(a1,0, len-1); 
}