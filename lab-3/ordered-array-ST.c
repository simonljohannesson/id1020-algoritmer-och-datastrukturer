#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
// #include "helper.h"

/*  Symbol table implementation with binary search in an ordered array. */

void checkMalloc(void* ptr){
        if(ptr == NULL){
        printf("Could not allocate memory, exiting.");
        exit(1);
    }
}

typedef struct array_bst {
    char** keys;
    int*   vals;
} Array_BST, *Array_BST_ptr;


Array_BST_ptr new_Array_BST(int capacity){
    Array_BST_ptr bts_ptr = malloc(sizeof(Array_BST));

    char** new_keys = malloc(sizeof(char**)*capacity);
    bts_ptr->keys = new_keys;
    // checkMalloc(bts_ptr->keys);
    int* new_vals = malloc(sizeof(int)*capacity);
    bts_ptr->vals = new_vals;
    // checkMalloc(bts_ptr->vals);
}

int main(void){
    Array_BST_ptr bts_ptr = new_Array_BST(10);



    for(int i = 0; i < 10; ++i){
        printf("i: %d\n", i, stderr);
        int len = 9;
        
        bts_ptr->keys[i] = malloc(sizeof(char)*10);
        char a[] = "some shit";
        for(int j = 0; j < len; ++j){
            bts_ptr->keys[i][j] = a[j];
        }
    }

    for(int i = 0; i < 10; ++i){
        puts(bts_ptr->keys[i]);
    }
    return 0;
}
