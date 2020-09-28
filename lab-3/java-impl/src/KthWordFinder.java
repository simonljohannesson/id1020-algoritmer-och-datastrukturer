/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-28
 *  Updated:
 *  Solves problem: Lab 3, assignment higher grade.
 *  Usage:          Compile. Input three arguments.
 *                  Arg1: minimum length of word for it to count as a word
 *                  Arg2: absolute path to text file to use as input
 *
 *  Dependencies:   SingleLinkedList (in same file)
 *  (own classes)   BST.java
 *                  HashTable.java
 *
 *
 *  Based on:       https://algs4.cs.princeton.edu/31elementary/FrequencyCounter.java.html
 */
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KthWordFinder {

    public static void main(String[] args){
        int minlen = 1;
        String path = "";
        if (args.length > 1) {
            minlen = Integer.parseInt(args[0]);
            path = args[1];
        }else{
            System.out.println(
                    "WARNING: Incorrect usage, terminating program\n"
                            + "Usage:\n"
                            + "Input two arguments.\n"
                            + "Arg1: minimum length of word for it to count as a word\n"
                            + "Arg2: absolute path to text file to use as input\n");
            System.exit(1);
        }

        /* start time */
        long startTime = System.nanoTime();

        /* scan file */
        BST<String, Integer> bst = new BST<>();

        try {
            System.out.println("Path is: " + path);
            InputStream inputStream = new FileInputStream(new File(path));
            Scanner scanner = new Scanner(inputStream);
            Pattern pattern = Pattern.compile("[^[a-zA-Z]]+");
            scanner.useDelimiter(pattern);

            while(scanner.hasNext()) {
                String word = scanner.next();
                if (word.length() < minlen) continue;
                if(bst.contains(word))  bst.put(word, bst.get(word) + 1);
                else                    bst.put(word, 1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("unique: " + bst.size());

        // sort into new bst
        BST<Integer, SingleLinkedList<String>> bstKth = new BST<>();
        for(String word : bst.keys()){
            int occurences = bst.get(word);
            if(bstKth.contains(occurences)){
                bstKth.get(occurences).addFirst(word);
            }else{
                SingleLinkedList<String> linkedList = new SingleLinkedList<>();
                linkedList.addFirst(word);
                bstKth.put(occurences, linkedList);
            }
        }

        // transfer to a hashtable
        // bst and bstKth have different sizes since bstKth has a linked list as value
        int order = bst.size();

        HashTable<Integer, String> ht = new HashTable<>(bst.size()/4);
        for(Integer occurences : bstKth.keys()){
            for(String word : bstKth.get(occurences)){
                ht.add(order--, word);
            }
        }

        /* end time */
        long setupTimeNanoSeconds = System.nanoTime() - startTime;
        double setupTimeSeconds =  setupTimeNanoSeconds / (double) 1.0e9;
        System.out.println(String.format("Initialization time: about %.3f seconds\n", setupTimeSeconds));

        // query the bst for indices

        Scanner scanner = new Scanner(System.in);
        Pattern kToKpattern = Pattern.compile("\\s*\\d+\\s*-\\s*\\d+\\s*");
        Pattern first = Pattern.compile("\\d+(?=\\s*-{1})");
        Pattern second = Pattern.compile("\\d+$");
        scanner.useDelimiter("\n");

        System.out.println("Shows k:th most common words or k-k+n most common words. "
                + "Use format <k> or <k1>-<k2>");
        while(scanner.hasNext()){
            try{
                String input = scanner.next();
                Matcher kTok = kToKpattern.matcher(input);
                if(kTok.matches()) {
                    Matcher firstM = first.matcher(input);
                    Matcher secondM = second.matcher(input);

                    if (firstM.find() && secondM.find()) {
                        int k1 = Integer.decode(input.substring(firstM.start(), firstM.end()));
                        int k2 = Integer.decode(input.substring(secondM.start(), secondM.end()));
                        if(k1>=k2){
                            System.out.println("k1 can't be larger than k2");
                            continue;
                        }
                        // perform lookup
                        SingleLinkedList<String> results = new SingleLinkedList<>();
                        for(int i = k1; i<=k2; i++){
                            String word = ht.get(i);
                            if(word != null)  results.addLast(word);
                        }
                        for(String word : results){
                            System.out.println(word);
                        }
                    }
                }
                else{
                    int k = Integer.decode(input);
                    if(k < 1){
                        System.out.println("Not a valid k-value");
                        continue;
                    }
                    // perform lookup
                    String result = ht.get(k);
                    if(result == null){
                        System.out.println("No words matching that k.");
                    }else{
                        System.out.println(result);
                    }
                }

            }catch (NumberFormatException e){
                System.out.println("Incorrect input format.");
                continue;
            }
            System.out.println("Shows k:th most common words or k-k+n most common words. "
                    + "Use format <integer> or <integer>-<integer>");
        }
        int[] indices = ht.getSizeAllIndices();
        for (Integer i : indices){
            System.out.print(i + ", ");
        }
        System.out.println();
    }
}
class SingleLinkedList<E> implements Iterable<E>{
    Node head;
    class Node{
        Node next;
        E element;
        Node(Node next, E element){
            this.next = next;
            this.element = element;
        }
    }
    SingleLinkedList(){
        head = new Node(null, null);
    }
    public void addFirst(E element){
        head.next = new Node(head.next, element);
    }
    public void addLast(E element){
        Node prev = head;
        while((prev.next)!=null) prev = prev.next;
        prev.next = new Node(prev.next, element);
    }
    public Iterator<E> iterator(){
        return new Iterator<E>(){
            Node next = head;
            @Override
            public boolean hasNext() {
                return next.next != null;
            }
            @Override
            public E next() {
                next = next.next;
                return next.element;
            }
        };
    }
}