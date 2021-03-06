/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-28
 *  Updated:
 *  Solves problem: Lab 3, assignment 3.
 *  Usage:          Compile. Input three arguments.
 *                  Arg1: minimum length of word for it to count as a word
 *                  Arg2: absolute path to text file to use as input
 *                  Arg3: capacity of the HashTable\
 *                  Prints out how many of the hashcodes ended up at
 *                  each index after being hashed with the hash function.
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

public class HashCodeEvalSeparateChaining {
    public static void main(String[] args){
        int minlen = 1;
        int n = 1;
        String path = "";
        int capacity = 10;
        if (args.length > 1) {
            minlen = Integer.parseInt(args[0]);
            path = args[1];
            capacity = Integer.parseInt(args[2]);
        }else{
            System.out.println(
                    "WARNING: Incorrect usage, terminating program\n"
                            + "Usage:\n"
                            + "Input three arguments.\n"
                            + "Arg1: minimum length of word for it to count as a word\n"
                            + "Arg2: absolute path to text file to use as input\n"
                            + "Arg3: capacity of the HashTable\n");
            System.exit(1);
        }
        HashTable<Integer, String> ht = new HashTable<>(capacity);

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

                ht.add(key.hashCode(), key);


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("word total   = " + words);

        int[] a = ht.getSizeAllIndices();
        for(int i = 0; i < a.length; i++){
//            System.out.format("a[%d] = %d\n", i, a[i]);
            unique += a[i];
        }
        System.out.println("unique = " + unique);
        StringBuilder distribution = new StringBuilder("distribution: [");
        for (int i = 0; i < a.length; i++){
            distribution.append(a[i]);
            if(i != a.length-1) distribution.append(", ");
        }
        distribution.append("]");
        System.out.println(distribution);
    }
}
