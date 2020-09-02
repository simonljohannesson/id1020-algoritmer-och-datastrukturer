/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-08-27
 *  Updated:        2020-09-02
 *  Solves problem: Lab 1, assignment 1.
 *  Usage:          For normal operation, run program without arguments and follow the prompts.
 *                  For testing operation, run program with -T as the only argument and pipe the
 *                  contents of 'test_data' as the standard input of the program. For example:
 *                  In bash, run the command './program -T < test_data', if program is the name
 *                  of the binary that resulted from compiling this file.
 *  Based on:       None.
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

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

void run_test(void){
    printf("Testing iterative reversed input:\n");
    test_print_char_implementation(&print_reversed_input_iter, 3);
    printf("Testing recursive reversed input:\n");
    test_print_char_implementation(&print_reversed_input_rec, 3);

}

/*
    check if flag '-T' in args

    return 1 if flag is first argument in args, else return 0
*/
int test_flag_set(int argc, char** argv){
    
    const char* const test_flag = "-T";
    if (argc > 1  &&  0 == strcmp(test_flag, argv[1])) return 1;
    else return 0;
}

int main(int argc, char** argv){

    if (test_flag_set(argc, argv)){
        run_test();
    }
    else {
        printf("Iterative: ");
        print_user_prompt();
        print_reversed_input_iter();

        printf("Recursive: ");
        print_user_prompt();
        print_reversed_input_rec();
    }


    return 0;
}