#include <stdio.h>

char* GLOBAL_STRING_ARR = "Hi there global friend";

int INTEGER = 666;

void do_nothing_of_value(void){
    int f = 44;
    for (int i = 0; i < 10; ++i){
        ++f;
    }
}
void print_array(char** a, int len){
    for(int i = 0; i < len; ++i){
        printf("0x%x ", (*a)[i]);
    }
    printf("\n");
}

void main(void){
    char* local_string = "im local, who are you?";
    do_nothing_of_value();
    print_array(&local_string, 22);
}