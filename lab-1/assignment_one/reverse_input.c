/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-08-27
 *  Updated: 
 *  Solves problem: 
 *  Usage:          
 *  Based on:       None.
 */
#include <stdio.h>
#include <stdlib.h>

/* 
    takes input from stdin until newline character is received
    then prints the input in reverse order, does not print the
    newline character 
    recursive implementation 
 */
void print_reversed_input_rec(void) {
    char character = getchar();
    if (character == '\n');     // reached base case do nothing
    else {
        print_reversed_input_rec(); // call itself
        putchar(character);
    }
}

/*  
    takes input from stdin until newline character is received
    then prints the input in reverse order, does not print the
    newline character 
    recursive implementation
 */
void print_reversed_input_iter(void) {
    const int BUFFER_SIZE = 1048;
    char input[BUFFER_SIZE];
    int count_input_chars = 0;
    while (count_input_chars < BUFFER_SIZE){
        char character = getchar();
        if (character == '\n') break;
        *(input + count_input_chars++) = character;  // put char at its corresponding index
    }
    for (char *charPtr = input+count_input_chars-1; charPtr >= input; --charPtr){
        putchar(*charPtr);
    }
}

/*  prompts the user to give an input  */
void print_user_prompt(void) {
    printf("Enter desired characters to present in reverse. End input with return.\n");
}

/*
    tests a function passed as argument
    the function tested should take input from stdin until newline
    character is received then print the input in reverse order
*/
void test_print_char_implementation(void (*functionPtr)(void), int lines_to_test){
        for(int i = 0; i < lines_to_test; ++i){
        printf("Actual output:   ");
        functionPtr();
        printf("\n");
        char input[20];
        fgets(input, 20, stdin);
        printf("Expected output: %s", input);
        printf("\n");
    }
}


void test(void){
    printf("Testing iterative reversed input:\n");
    test_print_char_implementation(&print_reversed_input_iter, 3);
    printf("Testing recursive reversed input:\n");
    test_print_char_implementation(&print_reversed_input_rec, 3);

}

int test_flag_set(char* argc, char** argv){
    // check if flag -T in args else run normal operation
    if (*argc > 1){
        int i = 0;
        while (i != 0x00){
            
        }
    }
}

int main(char* argc, char** argv){

    test();

    return 0;
}