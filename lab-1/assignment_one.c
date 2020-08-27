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

/* takes input from stdin until newline character is received
   then prints the input in reverse order, does not print the
   newline character */

void print_reversed_input(void) {
    char character = getchar();
    if (character == '\n');     // reached base case do nothing
    else {
        print_reversed_input(); // call itself
        putchar(character);
    }
}

/* prompts the user to give an input */

void print_user_prompt(void) {
    printf("Enter desired characters to present in reverse. End input with return.\n");
}




int main(void){
    print_user_prompt();
    print_reversed_input();
    return 0;
}