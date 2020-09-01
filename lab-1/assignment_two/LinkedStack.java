/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-01
 *  Updated:
 *  Usage:          For normal operation, run program without any arguments. When prompted input data, the data will
 *                  print it back in reverse.
 *                  For testing, run program with assertions enabled (-ea)
 *  Based on:       After implementation the correctness was checked against the LinkedStack in the LinkedStack class in
 *                  https://algs4.cs.princeton.edu/13stacks/LinkedStack.java.html
 */
import java.util.NoSuchElementException;
/**
 * This class is a representation of a stack.
 *
 * Implemented as a linked list that keeps track of the tail.
 * @param <T>
 */
public class LinkedStack<T> implements Stack <T>{
    private int size;
    private Node tail;

    /**
     * Class that represents the node that makes up the parts of a linked list.
     */
    private class Node {
        T item;
        Node next;
    }

    public LinkedStack(){
        size = 0;
        tail = null;
    }

    @Override
    public void push(T item) {
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = tail;
        tail = newNode;
        size++;
    }

    @Override
    public T pop() {
        if (tail == null) throw new NoSuchElementException("Stack underflow.");
        Node item = tail;
        tail = tail.next;
        item.next = null;
        size--;
        return item.item;
    }

    @Override
    public boolean isEmpty() {
        return tail == null;
    }

    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args){
        System.out.println("Running test of class LinkedListStack");
        // create test objects
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        Object d = new Object();
        Object e = new Object();

        // create stack
        LinkedStack<Object> stack = new LinkedStack<>();

        // add items to stack
        stack.push(a);
        stack.push(b);
        stack.push(c);
        stack.push(d);
        stack.push(e);

        // fetch items and make sure the expected item is returned
        boolean isTrue;
        isTrue = stack.pop() == e;
        assert (isTrue);
        isTrue = stack.pop() == d;
        assert (isTrue);
        isTrue = stack.pop() == c;
        assert (isTrue);
        isTrue = stack.pop() == b;
        assert (isTrue);
        isTrue = stack.pop() == a;
        assert (isTrue);
        try {
            stack.pop();
            assert false;
        } catch (NoSuchElementException error){
            assert true;
        }
        System.out.println("TEST PASSED!");
    }
}