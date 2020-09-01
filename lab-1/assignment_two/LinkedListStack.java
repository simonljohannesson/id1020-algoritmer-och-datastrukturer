/**
 * This class is a representation of a stack.
 *
 * Implemented as a linked list.
 * @param <T>
 */
public class LinkedListStack <T> implements Stack <T>{
    private int size;
    private Node<T> tail;

    /**
     * Class that represents the node that makes up the parts of a linked list.
     * @param <T> type of item that can be stored in the node
     */
    private class Node <T>{
        T item;
        Node<T> next;
    }

    public LinkedListStack(){
        size = 0;
        tail = null;
    }

    @Override
    public void push(T item) {
        Node<T> newNode = new Node<>();
        newNode.item = item;
        newNode.next = tail;
        tail = newNode;
        size++;
    }

    @Override
    public T pop() {
        if (tail == null) throw new java.util.NoSuchElementException("Stack underflow.");
        Node<T> item = tail;
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
}