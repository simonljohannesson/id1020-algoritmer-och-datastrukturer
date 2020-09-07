/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-03
 *  Updated:
 *  Solves problem: Lab 1, assignment 3.
 *  Usage:          For normal operation, import Queue class and use with its API.
 *                  For testing, run main method with the class compiled with assertions enabled.
 *  Based on:       Inspiration taken from: https://algs4.cs.princeton.edu/13stacks/Queue.java.html
 *  TODO: remove all prints except tests
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueAssign3<T> implements Iterable<T> {
    private Node first;
    private int size;

    /**
     * Class that represent nodes in a double linked list.
     */
    private class Node {
        T item;
        Node previous;
        Node next;
    }

    public QueueAssign3(){
        first = null;
        size = 0;
    }

    /**
     * Adds item to the queue.
     *
     * @param item item to add to queue.
     */
    public void enqueue(T item){
        // create new node containing the item
        Node newNode = new Node();
        newNode.item = item;
        // queue empty case
        if (first == null){
            // replace null with the new node
            first = newNode;
            // since list empty next and previous will point to the new node
            newNode.next = newNode.previous = newNode;
        // queue not empty
        }else{
            // new node previous points to last element in list
            newNode.previous = first.previous;
            // new node next points to first element in list
            newNode.next = first;
            // last element in list (next) points to new node
            first.previous.next = newNode;
            // first element in list (previous) points to new node
            first.previous = newNode;
        }
        size++;
    }

    /**
     * Removes the first item from the queue and returns it.
     *
     * @return first item in the queue
     */
    public T dequeue(){
        // queue empty case
        if(first == null){
            throw new NoSuchElementException("Queue underflow.");
        }

        // store ref to node to dequeue
        Node dequeueNode = first;

        // one element in queue case
        if (first == first.previous){ // points to itself
            first = null;
        // more than one element in queue case
        }else{
            // last element next point to second element in queue
            first.previous.next = first.next;
            // second element in queue (previous) point to last element in queue
            first.next.previous = first.previous;
            // first point to second element in queue (making it the new first element in queue)
            first = first.next;
        }
        size--;
        // remove loitering references
        dequeueNode.previous = dequeueNode.next = null;

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
        return first == null;
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
        StringBuilder string = new StringBuilder("{");
        for (T item : this){
            string.append("[");
            string.append(item.toString());
            string.append("], ");
        }
        if (size > 0){
            string.deleteCharAt(string.length() - 1);
            string.deleteCharAt(string.length() - 1);
        }
        string.append("}");
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

        QueueAssign3<String> strQueue = new QueueAssign3<>();

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
        QueueAssign3<Integer> integerQueue = new QueueAssign3<>();
        for (int i = 0; i < intList.length; i++){
            integerQueue.enqueue(intList[i]);
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

        // test toString function
        QueueAssign3<String> toStringQueue = new QueueAssign3<>();
        assert toStringQueue.toString().equals("{}");
        toStringQueue.enqueue("hello");
        toStringQueue.enqueue("my");
        toStringQueue.enqueue("friend");
        assert toStringQueue.toString().equals("{[hello], [my], [friend]}");


        if(assertionsEnabled){
            System.out.println("TESTS SUCCESSFUL!");
        }
    }

}
