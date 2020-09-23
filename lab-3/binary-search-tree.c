#include <stdio.h>
#include <string.h>
#include "helper.h"

typedef struct value{
    int a;
} Value, *ValuePtr;

typedef struct key{
    int b;
} Key, *KeyPtr;

typedef struct node{
    struct node* left;
    struct node* right;
    KeyPtr c;
    ValuePtr d;
} Node, *NodePtr;