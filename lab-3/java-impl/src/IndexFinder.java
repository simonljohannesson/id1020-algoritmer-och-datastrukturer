/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-28
 *  Updated:
 *  Solves problem: Lab 3, assignment 3.
 *  Usage:          Compile. Input one arguments.
 *                  Arg1: absolute path to text file to use as input
 *
 *  Dependencies:   BST.java
 *  (own classes)
 *
 *
 *  Based on:       https://algs4.cs.princeton.edu/31elementary/FrequencyCounter.java.html
 */
import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;

public class IndexFinder {
    public static void main(String[] args){
        int n = 1;
        String path = "";
        if (args.length > 1) {
            path = args[1];
        }else{
            System.out.println(
                    "WARNING: Incorrect usage, terminating program\n"
                            + "Usage:\n"
                            + "Input one arguments.\n"
                            + "Arg1: absolute path to text file to use as input\n");
            System.exit(1);
        }

        /* scan file, print each word */
        BST<String, LinkedList<Integer>> bst = new BST<>();

        try {
            DataInput di = new DataInputStream(new FileInputStream(path));

            char c;
            int index = -1;
            while (true) {
                c = (char) di.readByte();
                ++index;
                if (Character.isLetter(c)) {
                    int startIndex = index;
                    StringBuilder wordBuilder = new StringBuilder();
                    wordBuilder.append(c);
                    // read rest of the word
                    while (Character.isLetter(c = (char) di.readByte())) {
                        ++index;
                        wordBuilder.append(c);
                    }
                    ++index; // increment one for the last char that was not a letter

                    // at this point a whole word and it's start index is available
                    String word = wordBuilder.toString();
                    if(bst.contains(word)){
                        bst.get(word).addLast(startIndex);
                    }else{
                        LinkedList<Integer> newLinkedList = new LinkedList<>();
                        newLinkedList.addLast(startIndex);
                        bst.put(word, newLinkedList);
                    }
                }
            }
        } catch (EOFException e){
            ; // do nothing this is expected https://docs.oracle.com/javase/tutorial/essential/io/datastreams.html
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // query the bst for indices
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.println("Query index for word: ");
        while((scanner.hasNext())){
            input = scanner.next();
            if(bst.contains(input)){
                System.out.println("The word '" + input + "' is present at the index/indices:");
                for(Integer index : bst.get(input)){
                    System.out.println(index);
                }
            }else{
                System.out.println("The word '" + input + "' is not present.");
            }
            System.out.println("Query index for word: ");
        }
    }
}
