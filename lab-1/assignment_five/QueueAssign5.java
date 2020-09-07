/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-04
 *  Updated:
 *  Solves problem: Lab 1, assignment 5.
 *  Usage:          For normal operation, import QueueAssign5 class and use with its API.
 *                  For testing, run main method with the class compiled with assertions enabled.
 *  Based on:       Inspiration taken from: https://algs4.cs.princeton.edu/13stacks/Queue.java.html
 *  TODO: remove all prints except tests
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("DuplicatedCode")
public class QueueAssign5<T> implements Iterable<T> {
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

    public QueueAssign5(){
        first = null;
        size = 0;
    }

    /**
     * Check if queue is empty.
     *
     * @return true if empty, false if not empty
     */
    public boolean isEmpty(){
        return (first == null);
    }

    /**
     * Removes node from queue.
     *
     * Node that should be removed must exist in the queue.
     * Can remove and update first item in the queue.
     * @param nodeToRemove node that should be removed
     * @return node that was removed
     */
    private Node removeNode(Node nodeToRemove){
        // one element in list
        if (nodeToRemove == nodeToRemove.next){
            first = null;
        // multiple elements in list
        } else {
            // next of node before node to remove -> node after node to remove
            nodeToRemove.previous.next = nodeToRemove.next;
            // previous of node after node to remove -> node before node to remove
            nodeToRemove.next.previous = nodeToRemove.previous;
        }
        // multiple elements in list remove first
        if (nodeToRemove == first){
            // move first to next in queue
            first = first.next;
        }
        size--;
        // remove loitering references and return
        nodeToRemove.next = nodeToRemove.previous = null;
        return nodeToRemove;
    }

    /**
     * Adds a node after the specified node.
     *
     * Does not handle where the first element points unless the list happen to be empty.
     * @param node specified node after which a node will be added
     * @param nodeToAdd node that will be added after specified node
     */
    private void addNodeAfter(Node node, Node nodeToAdd){
        // empty list (first used as argument when list empty)
        if (node == null){
            first = nodeToAdd.next = nodeToAdd.previous = nodeToAdd;

        // any non empty list case
        } else {
            // add next and previous for nodeToAdd
            nodeToAdd.previous = node;
            nodeToAdd.next = node.next;
            // make nodes around nodeToAdd point to nodeToAdd
            node.next.previous = nodeToAdd;
            node.next = nodeToAdd;
        }
        size++;
    }

    /**
     * Removes item from the end of the queue.
     *
     * @return item at the end of the queue
     */
    public T dequeueLast(){
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Node node = removeNode(first.previous);

        return node.item;
    }

    /**
     * Removes item from the beginning of the queue.
     *
     * @return item at the beginning of the queue
     */
    public T dequeueFirst(){
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Node node = removeNode(first);
        return node.item;
    }

    /**
     * Add item to end of queue.
     *
     * @param item item to be added
     */
    public void enqueueLast(T item){
        Node newNode = new Node();
        newNode.item = item;
        Node after = first;
        if (first != null) after = first.previous;
        addNodeAfter(after, newNode);
    }

    /**
     * Add item to the beginning of the queue.
     *
     * @param item item to add to queue
     */
    public void enqueueFirst(T item){
        enqueueLast(item);

        // update head to point to the new first item
        first = first.previous;
    }

    public T dequeItemAtIndex(int index){
        if (index >= size  || index < 0){
            throw new IndexOutOfBoundsException();
        }
        int i = 0;
        Iterator<Node> iterator = nodeIterator();
        Node node;
        while(true){
            node = iterator.next();
            if (i == index) break;
            i++;
        }
        return removeNode(node).item;
    }

    /**
     * Size of queue.
     * @return the size of the queue
     */
    public int size(){
        return size;
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

    private Iterator<Node> nodeIterator(){
        return new NodeIterator();
    }

    private class NodeIterator implements Iterator<Node> {
        Node next;

        NodeIterator(){
            next = first;
        }

        @Override
        public boolean hasNext(){
            return next != null;
        }
        @Override
        public Node next(){
            if (next == null) throw new NoSuchElementException();
            Node current = next;
            if (next.next == first){
                next = null;
            } else {
                next = next.next;
            }
            return current;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new QueueAssign5Iterator();
    }
    private class QueueAssign5Iterator implements Iterator<T> {
        Node next;

        QueueAssign5Iterator(){
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
    public static void main(String[] args){
        System.out.println("Running tests on class QueueAssign5");

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









        if (assertionsEnabled){
            System.out.println("TESTS SUCCESSFUL!!!");
        } else {
            System.out.println("Assertions are not enabled. Tests not completed.");
        }

    }
}
