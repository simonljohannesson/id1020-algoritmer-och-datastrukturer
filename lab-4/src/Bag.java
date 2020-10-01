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

public class Bag<Type> implements Iterable<Type>{
    private Node head;
    private int size;
    class Node{
        Type item;
        Node next;
        Node(Type item, Node next){
            this.item = item;
            this.next = next;
        }
    }
    public Bag(){
        int size = 0;
    }

    /**
     * Add an item to the bag.
     */
    public void add(Type item){
        head = new Node(item, head);
        this.size++;
    }
    /**
     * Returns true if bag is empty.
     */
    public boolean isEmpty(){
        return size == 0;
    }
    /**
     * Returns size of bag.
     */
    public int size(){
        return this.size;
    }
    @Override
    public Iterator<Type> iterator() {
        return new Iterator<Type>(){
            Node next = head;
            @Override
            public boolean hasNext() {
                return next != null;
            }
            @Override
            public Type next() {
                Type item = next.item;
                this.next = next.next;
                return item;
            }
        };
    }
    public static void main(String[] args){
        Bag<String> bag = new Bag<>();
        for(int i = 0; i < 10; i++){
            bag.add(Integer.toString(i));
        }
        for(String each : bag){
            System.out.println(each);
        }
    }
}
