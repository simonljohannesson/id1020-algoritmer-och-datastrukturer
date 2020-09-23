#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

int main(){
    char* file_path = "./rewrite.txt";
    FILE* fp = fopen(file_path, "r+");
    if(fp == NULL){
        printf("Could not open file %s, terminating program", file_path);
        return 1;
    }
    char c;
    while((c = fgetc(fp)) != EOF){
        if(!isalpha(c)  &&  !isspace(c)){
            // step back and rewrite char in file
            ungetc(c, fp);
            fputc(' ', fp);
        }
    }
    fclose(fp);
}