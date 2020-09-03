/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-03
 *  Updated:
 *  Usage:          For normal operation, import Queue class and use with its API.
 *                  For testing, run main method with the class compiled with assertions enabled.
 *  Based on:       Inspiration taken from: https://algs4.cs.princeton.edu/13stacks/Queue.java.html
 */
/*
    Exercise:
    Implement a generic iterable FIFO-queue based on a double linked circular list.
    You should print the content of the list after each insertion/deletion of an element.

    Inspiration taken from: https://algs4.cs.princeton.edu/13stacks/Queue.java.html
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<T> implements Iterable<T> {
    private Node first;
    private Node last;
    private int size;

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
        System.out.println(this);
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

        System.out.println(this);
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

    @Override
    public Iterator<T> iterator() {
        return new QueueIterator();
    }
    private class QueueIterator implements Iterator<T>{
        Node next;

        QueueIterator(){
            next = first;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            if (next == null) throw new NoSuchElementException();
            Node current = next;
            if (next.next == first){
                next = null;
            } else {
                next = next.next;
            }
            return current.item;
        }
    }
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder("[");
        for (T item : this){
            string.append(item.toString());
            string.append(", ");
        }
        if (size > 0){
            string.deleteCharAt(string.length() - 1);
            string.deleteCharAt(string.length() - 1);
        }
        string.append("]");
        return string.toString();
    }

    public static void main(String[] args){
        // run tests
        System.out.println("Running tests on class Queue");

        boolean assertionsEnabled = false;
        try{
            assert false;
        } catch (AssertionError e){
            assertionsEnabled = true;
        } finally {
            if (!assertionsEnabled){
                System.out.println("Assertions are not enabled. Tests not completed.");
            }
        }

        Queue<String> strQueue = new Queue<>();

        // size should be 0 for empty list
        int queueSize = strQueue.size();
        assert queueSize == 0;

        // make sure that Exception is thrown when dequeue called on empty queue
        try{
            strQueue.dequeue();
            assert false;
        } catch (NoSuchElementException e){
            assert true;
        }
        // add some elements to queue
        String a = "hello";
        String b = "bye!";
        String c = "''";
        String d = "strings";
        strQueue.enqueue(a);
        strQueue.enqueue(b);
        strQueue.enqueue(c);

        // dequeue elements from queue and check they are the right objects
        String aResult = strQueue.dequeue();
        String bResult = strQueue.dequeue();

        assert aResult == a; // should compare object reference not string equivalence
        assert bResult == b; // should compare object reference not string equivalence

        // size should be 1
        queueSize = strQueue.size();
        assert queueSize == 1;

        // add another element, though list is not empty yet
        strQueue.enqueue(d);
        String cResult = strQueue.dequeue();
        String dResult = strQueue.dequeue();

        assert cResult == c; // should compare object reference not string equivalence
        assert dResult == d; // should compare object reference not string equivalence


        // test iteration
        Integer[] intList = {4, 5, 7, 1, 9};

        Queue<Integer> integerQueue = new Queue<>();
        for (Integer integer : integerQueue){
            integerQueue.enqueue(integer);
        }
        int index = 0;
        for (Integer integer : integerQueue){
            assert integer == intList[index]; // should return same object not just equivalent
            index++;
        }

        // make iterator go one too many, should throw NoSuchElementException
        Iterator<Integer> queueIterator = integerQueue.iterator();
        while(queueIterator.hasNext()){
            queueIterator.next(); // do nothing
        }
        try{
            queueIterator.next();
            assert false;
        } catch (NoSuchElementException e){
            assert true;
        }


        if(assertionsEnabled){
            System.out.println("TESTS SUCCESSFUL!");
        }
    }

}
