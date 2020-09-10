#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

// typedef struct node Node, *NodePtr;

typedef struct node {
    struct node* next;
    struct node* previous;
    int number;
} Node, *NodePtr;

/* Linked list implemented using a sentinel element */
typedef struct linked_list {
    NodePtr head;
    int size;
} LinkedList, *LinkedListPtr;

void checkMalloc(void* ptr){
        if(ptr == NULL){
        printf("Could not allocate memory, exiting.");
        exit(1);
    }
}

LinkedListPtr initiateLinkedList(){
    NodePtr sentinel = (NodePtr) malloc(sizeof(Node));
    checkMalloc(sentinel);
    sentinel->next = sentinel;
    sentinel->previous = sentinel;

    LinkedListPtr list = (LinkedListPtr) malloc(sizeof(LinkedList));
    checkMalloc(list);
    list->head = sentinel;
    list->size = 0;
    return list;
}

NodePtr newNode(int value){
    NodePtr new_node = (NodePtr)malloc(sizeof(Node));
    checkMalloc(new_node);
    new_node->number = value;
    return new_node;
}

void insertAfter(NodePtr node, NodePtr new_node){
    new_node->previous = node;
    new_node->next = node->next;

    node->next->previous = new_node;
    node->next = new_node;
}

int removeNode(NodePtr node){
    node->previous->next = node->next;
    node->next->previous = node->previous;
    int value = node->number;
    node->next = node->previous = NULL;
    free(node);
    return value;
}

int removeLast(LinkedListPtr list){
    if(list->head != list->head->previous){
        list->size--;
        return removeNode(list->head->previous);
    } else {
        errno = 101; // arbitrary
        return __INT_MAX__;
    }
}

int removeFirst(LinkedListPtr list){
    if(list->head != list->head->previous){
        list->size--;
        return removeNode(list->head->next);
    } else{
        errno = 101; // arbitrary
        return __INT_MAX__;
    }
}

void insertFirst(LinkedListPtr list, int value){
    NodePtr new_node = newNode(value);
    insertAfter(list->head, new_node);
    list->size++;
}

void insertLast(LinkedListPtr list, int value){
    NodePtr new_node = newNode(value);
    insertAfter(list->head->previous, new_node);
    list->size++;
}

int removeFromIndex(LinkedListPtr list, int index){
    if(index < 0  ||  index > list->size -1){
        errno = 101;  // arbitrary
        return __INT_MAX__;
    }
    NodePtr node = list->head;
    while(index > 0){
        node = node->next;
        index--;
    }
    list->size--;
    return removeNode(node);
}

void print_linked_list(LinkedListPtr list){
    NodePtr head = list->head;
    printf("[");
    while ((head = head->next)  !=  list->head){
        printf("%d, ", head->number);
    }
    printf("]\n\n");
}


void main(){
    LinkedListPtr list = initiateLinkedList();

    insertFirst(list, 1);
    insertFirst(list, 2);
    insertFirst(list, 5);
    insertFirst(list, 6);
    insertFirst(list, 7);
    insertFirst(list, 8);
    insertLast(list, 0);

    printf("List: ");
    printf("Size: %d", list->size);
    print_linked_list(list);

    errno = 0;
    printf("Removed item: %d errno: ", removeLast(list));
    printf("%d\n", errno);   

    printf("List: ");
    print_linked_list(list);
    errno = 0;
    printf("Removed item: %d errno: ", removeLast(list));
    printf("%d\n", errno); 

    printf("List: ");
    print_linked_list(list);
    errno = 0;
    printf("Removed item: %d errno: ", removeFirst(list));
    printf("%d\n", errno); 

    printf("List: ");
    print_linked_list(list);
    errno = 0;
    printf("Removed item: %d errno: ", removeFirst(list));
    printf("%d\n", errno); 
    
    printf("List: ");
    print_linked_list(list);
    errno = 0;
    printf("Removed item from index %d: %d errno: ", 4, removeFromIndex(list, 4));
    printf("%d\n", errno); 

    printf("List: ");
    print_linked_list(list);
    errno = 0;
    printf("Removed item from index %d: %d errno: ", 2, removeFromIndex(list, 2));
    printf("%d\n", errno); 

    printf("List: ");
    print_linked_list(list);

    errno = 0;
    printf("Removed item: %d errno: ", removeLast(list));
    printf("%d\n", errno); 
    
    printf("List: ");
    print_linked_list(list);

    errno = 0;
    printf("Removed item: %d errno: ", removeFirst(list));
    printf("%d\n", errno); 
    
    printf("List: ");
    print_linked_list(list);

    free(list->head);
    free(list);
}