/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-04
 *  Updated:
 *  Solves problem: Lab 1, assignment 4.
 *  Usage:          For normal operation, import Queue class and use with its API.
 *                  For testing, run main method with the class compiled with assertions enabled.
 *  Based on:       Inspiration taken from: https://algs4.cs.princeton.edu/13stacks/Queue.java.html
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AccessibleQueue<T> implements Iterable<T> {
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

    public AccessibleQueue(){
        first = null;
        size = 0;
    }

    private Node removeNode(Node nodeToRemove){
        // empty list (first used as argument when list empty)
        if (nodeToRemove == null){
           throw new NoSuchElementException("Queue underflow.");
        }
        // one element in list
        if (nodeToRemove == nodeToRemove.next){
            first = null;
        // multiple elements in list
        } else {
            nodeToRemove.previous.next = nodeToRemove.next;
            nodeToRemove.next.previous = nodeToRemove.previous;
        }
        // multiple elements in list remove first
        if (nodeToRemove == first){
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
        } else {
            // any non empty list case
            nodeToAdd.previous = node;
            nodeToAdd.next = node.next;
            node.next.previous = nodeToAdd;
            node.next = nodeToAdd;
        }
        size++;
    }

    public T dequeueLast(){
        Node last = first;
        if (first != null) last = first.previous;
        Node node = removeNode(last);
        System.out.println(this.toString());
        return node.item;
    }
    public T dequeueFirst(){
        Node node = removeNode(first);
        System.out.println(this.toString());
        return node.item;
    }
    public void enqueueLast(T item){
        Node newNode = new Node();
        newNode.item = item;
        Node after = first;
        if (first != null) after = first.previous;
        addNodeAfter(after, newNode);

        System.out.println(this.toString());
    }
    public void enqueueFirst(T item){
        Node newNode = new Node();
        newNode.item = item;
        Node after = first;
        if (first != null) after = first.previous;
        addNodeAfter(after, newNode);

        first = first.previous;
        System.out.println(this.toString());
    }
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

    @Override
    public Iterator<T> iterator() {
        return new AccessibleQueueIterator();
    }
    private class AccessibleQueueIterator implements Iterator<T> {
        Node next;

        AccessibleQueueIterator(){
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
        String result;

        AccessibleQueue<String> q = new AccessibleQueue<>();
        q.enqueueLast("a");
        q.enqueueLast("c");
        q.enqueueFirst("1");
        result = q.dequeueFirst();
        assert result.equals("1");

        result = q.dequeueFirst();
        assert result.equals("a");

        result = q.dequeueFirst();
        assert result.equals("c");

        try{
            q.dequeueFirst();
            assert false;
        } catch (NoSuchElementException e){
            assert true;
        }



        AccessibleQueue<String> q1 = new AccessibleQueue<>();
        q1.enqueueFirst("a");
        q1.enqueueFirst("b");
        q1.enqueueFirst("c");
        q1.enqueueLast("1");

        result = q1.dequeueLast();
        assert result.equals("1");

        result = q1.dequeueLast();
        assert result.equals("a");

        result = q1.dequeueLast();
        assert result.equals("b");

        result = q1.dequeueLast();
        assert result.equals("c");

        try{
            q1.dequeueFirst();
            assert false;
        } catch (NoSuchElementException e){
            assert true;
        }

        AccessibleQueue<String> q2 = new AccessibleQueue<>();
        q2.enqueueLast("a");
        q2.enqueueLast("b");
        q2.enqueueLast("c");
        q2.enqueueLast("d");

        String[] expected = {"a", "b", "c", "d"};
        int index = 0;
        for (String item : q2){
            assert item.equals(expected[index]);
            index++;
        }
        assert q2.toString().equals("{[a], [b], [c], [d]}");

    if (assertionsEnabled){
        System.out.println("TESTS SUCCESSFUL!!!");
    } else {
        System.out.println("Assertions are not enabled. Tests not completed.");
    }

    }
}
