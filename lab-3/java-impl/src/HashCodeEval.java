/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-28
 *  Updated:
 *  Solves problem: Lab 3, assignment 3.
 *  Usage:          Compile. Input two arguments.
 *                  Arg1: minimum length of word for it to count as a word
 *                  Arg2: absolute path to text file to use as input
 *                  Shows data about collisions of the words hash codes.
 *
 *  Dependencies:   LinkedList (in same file)
 *  (own classes)   BST.java
 *
 *
 *  Based on:       https://algs4.cs.princeton.edu/31elementary/FrequencyCounter.java.html
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HashCodeEval {
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
                            + "Input three arguments.\n"
                            + "Arg1: minimum length of word for it to count as a word\n"
                            + "Arg2: absolute path to text file to use as input\n");
            System.exit(1);
        }
        BST<Integer, Queue<String>> st = new BST<>();
        int collisions = 0, words = 0, unique = 0;
        // compute frequency counts
        try {
            System.out.println("Path is: " + path);
            Scanner scanner = new Scanner(new File(path));
            Pattern pattern = Pattern.compile("[^[a-zA-Z]]+");
            scanner.useDelimiter(pattern);

            while(scanner.hasNext()){
                String key = scanner.next();

                if (key.length() < minlen) continue;
                words++;

                Integer hashValue = key.hashCode();

                if (st.contains(hashValue)) {
                    boolean keyInList = false;
                    Queue<String> q = st.get(hashValue);
                    for(String word : q){
                        if(key.equals(word)){
                            keyInList = true;
                            break;
                        }
                    }
                    if(!keyInList){
                        q.enqueueLast(key);
                        collisions++;
                        unique++;
                    }
                }
                else {
                    Queue<String> q = new Queue<>();
                    q.enqueueLast(key);
                    st.put(hashValue, q);
                    unique++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("collisions   = " + collisions);
        System.out.println("word total   = " + words);
        System.out.println("unique words = " + unique);
        System.out.println("Found collisions:");
        for(Integer i : st.keys()){
            Queue<String> q;
            if((q = st.get(i)).size() > 1){
                System.out.println(q);
            }
        }
    }
}
