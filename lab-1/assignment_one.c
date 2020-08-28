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

    // // fetch expected output and save in an array
    // const char *expected_output_data_path = "assignment_one_expected_output_test_data";
    // FILE *expected_data_file = fopen(expected_output_data_path, "r");
    // if (expected_data_file == NULL) {
    //     printf("Could not open file '%s'", expected_output_data_path);
    //     // return ERROR_NUMBER
    // }
    // // allocate string array that holds 4 strings
    // char **expected_output = (char**) malloc(sizeof(char*)*4);
    // int BUFFER_SIZE = 20;
    // for(int i = 0; i < 4; ++i){
    //     char *output = (char*)malloc(sizeof(char)*BUFFER_SIZE);
    //     fgets(output, BUFFER_SIZE, expected_data_file);
    //     *(expected_output+i) = output;
    // }
    // printf("Print array\n");
    // char **ptr = NULL;
    // for(ptr = expected_output; ptr<expected_output+4; ++ptr){
    //     printf("%s", *ptr);
    // }

    // // set stdinput stream to input from file
    // const char *input_data_path = "assignment_one_input_test_data";
    // freopen(input_data_path, "r", stdin);

    // print_reversed_input_rec();
    // print_reversed_input_rec();
    // print_reversed_input_rec();
    // print_reversed_input_rec();

    // Usage of test: start program with

    
    // for(int i = 0; i < 3; ++i){
    //     printf("Actual output:   ");
    //     print_reversed_input_iter();
    //     printf("\n");
    //     char input[20];
    //     fgets(input, 20, stdin);
    //     printf("Expected output: %s", input);
    //     printf("\n");
    // }
    printf("Testing iterative reversed input:\n");
    test_print_char_implementation(print_reversed_input_iter, 3);
    printf("Testing recursive reversed input:\n");
    test_print_char_implementation(print_reversed_input_rec, 3);

}


int main(void){
    // print_user_prompt();
    // print_reversed_input_rec();

    // print_user_prompt();
    // print_reversed_input_iter();

    // test();

    // fputs("12345678901234567890", stdin);
    // char input[20];
    // fgets(input, 20, stdin);
    // printf("%s", input);

    test();

    return 0;
}