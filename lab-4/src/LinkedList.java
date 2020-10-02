/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-10-01
 *  Updated:
 *  Solves problem: Lab 4
 *  Usage:          Compile and use with API.
 *
 *  Dependencies:
 *  (own classes)
 *
 *  Based on:       None.
 */
import java.util.Iterator;

public class LinkedList<Type extends Comparable<Type>> implements List<Type>, Iterable<Type> { //TODO: using equals would be better
    private Node sentinel;
    private int size;

    private class Node {
        Type item;
        Node next;
        Node prev;

        Node(Type item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    public LinkedList() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    @Override
    public void append(Type item) {
        insertAfter(sentinel.prev, item);
    }
    @Override
    public void prepend(Type item){
        insertAfter(sentinel, item);
    }

    private void insertAfter(Node after, Type item){
        Node newNode = new Node(item, after.next, after);
        after.next.prev = newNode;
        after.next = newNode;
        size++;
    }
    private Type remove(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = node.prev = null;
        size--;
        return node.item;
    }

    @Override
    public Type remove(int index) {
        if (index < 0 || this.size-1 < index) throw new IndexOutOfBoundsException();
        Node toRemove = sentinel.next;
        while(index-- != 0){
            toRemove = toRemove.next;
        }
        return remove(toRemove);
    }
    @Override
    public int size(){
        return this.size;
    }

    @Override
    public Type getMatching(Type item) throws NotInListException {
        Node node = sentinel.next;
        while(node != sentinel){
            if(node.item.compareTo(item) == 0) return node.item; // TODO: equals would be better
            node = node.next;
        }
        throw new NotInListException();
    }


    @Override
    public Iterator<Type> iterator() {
        return new Iterator<Type>() {
            Node next = sentinel.next;

            @Override
            public boolean hasNext() {
                return next != sentinel; // reference check
            }

            @Override
            public Type next() {
                Type item = next.item;
                next = next.next;
                return item;
            }
        };
    }

    public static void main(String[] args) {
        class Test implements Comparable<Test> {
            Integer a;
            int b;
            Test(int a, int b){
                this.a = a;
                this.b = b;
            }

            @Override
            public int compareTo(Test test) {
                return this.a.compareTo(test.a);
            }
        }
        LinkedList<Test> intList = new LinkedList<>();

        intList.append(new Test(1,1));
        intList.append(new Test(2,2));

        for(Test a : intList){
            System.out.println((a.a + " " + a.b));
        }
        try{
            Test t = intList.getMatching(new Test(0,3));
            System.out.println((t.a + " " + t.b));
        }catch (NotInListException e){
            ;
        }

    }
}


