/*
    Exercise:
    Implement a generic iterable FIFO-queue based on a double linked circular list.
    You should print the content of the list after each insertion/deletion of an element.

    Inspiration taken from: https://algs4.cs.princeton.edu/13stacks/Queue.java.html
 */

import java.util.NoSuchElementException;

public class Queue<T> {
    Node first;
    Node last;
    int size;

    /**
     * Class that represent nodes in a double linked list.
     */
    private class Node {
        T item;
        Node previous;
        Node next;
    }

    public Queue(){
        first = last = null;
        size = 0;
    }

    /**
     * Adds item to the queue.
     *
     * @param item item to add to queue.
     */
    public void enqueue(T item){
        Node newNode = new Node();
        newNode.item = item;
        if (first == null  &&  last == null){
            first = last = newNode;
            newNode.next = newNode.previous = newNode;
        }else{
            newNode.previous = last;
            newNode.next = first;
            first.previous = newNode;
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    /**
     * Removes the first item from the queue and returns it.
     *
     * @return first item in the queue
     */
    public T dequeue(){
        Node dequeueNode;

        // queue empty case
        if (first == null  && last == null){
            throw new NoSuchElementException("Queue underflow.");
        }
        dequeueNode = first;

        // one element in queue case
        if (first == last){
            first = last = null;
        // more than one element in queue case
        }else{
            first = first.next;
            last.next = first;
            first.previous = last;
        }
        size--;
        // remove loitering references              is this really necessary?
        dequeueNode.previous = null;
        dequeueNode.next = null;

        return dequeueNode.item;
    }

    /**
     * Get size of the queue.
     *
     * @return size of queue
     */
    public int size(){
        return size;
    }

    /**
     * Check if queue is empty.
     *
     * @return true if queue is empty, false if queue is not empty
     */
    public boolean isEmpty(){
        return first == null  &&  last == null;
    }
}
