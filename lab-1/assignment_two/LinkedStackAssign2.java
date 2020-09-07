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
public class LinkedStackAssign2<T> implements Stack <T>{
    private int size;
    private Node tail;

    /**
     * Class that represents the node that makes up the parts of a linked list.
     */
    private class Node {
        T item;
        Node next;
    }

    public LinkedStackAssign2(){
        size = 0;
        tail = null;
    }

    @Override
    public void push(T item) {
        // create new node with item
        Node newNode = new Node();
        newNode.item = item;
        // set item that tail points to as the next item of new node
        newNode.next = tail;
        // move tail to the new node
        tail = newNode;
        size++;
    }

    @Override
    public T pop() {
        // make sure list no empty
        if (tail == null) throw new NoSuchElementException("Stack underflow.");
        // store reference to node/item that will be popped
        Node item = tail;
        // update tail to point to next (in queue)
        tail = tail.next;
        // value not used set to null
        item.next = null;
        size--;
        // return item of the node that was popped/removed
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
        LinkedStackAssign2<Object> stack = new LinkedStackAssign2<>();

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