/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-28
 *  Updated:
 *  Solves problem: Lab 3, assignment 2.
 *  Usage:          Compile. "Input three arguments.
 *                  Arg1: minimum length of word for it to count as a word
 *                  Arg2: how many hundred words that should be tested
 *                  Arg3: absolute path to text file to use as input
 *
 *                  Will read N-hundred words from 10, to N in increments of 10
 *                  and output the time and data for each of the test.
 *
 *                  Example arguments: 1 100 ./the-text.txt
 *
 *  Dependencies:   BinarySearchST.java
 *  (own classes)
 *
 *
 *  Based on:       https://algs4.cs.princeton.edu/31elementary/FrequencyCounter.java.html
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class FrequencyCounterBinarySearchST {

    // Do not instantiate.
    private FrequencyCounterBinarySearchST() { }

    /**
     * Reads in a command-line integer and sequence of words from
     * standard the-text.txt and prints out a word (whose length exceeds
     * the threshold) that occurs most frequently to standard output.
     * It also prints out the number of words whose length exceeds
     * the threshold and the number of distinct such words.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int minlen = 1;
        int n = 1;
        String path = "";
        if (args.length > 2) {
            minlen = Integer.parseInt(args[0]);
            n = Integer.parseInt(args[1]);
            path = args[2];
        }else{
            System.out.println(
                    "WARNING: Incorrect usage, terminating program\n"
                            + "Usage:\n"
                            + "Input three arguments.\n"
                            + "Arg1: minimum length of word for it to count as a word\n"
                            + "Arg2: how many hundred words that should be tested\n"
                            + "Arg3: absolute path to text file to use as input\n");
            System.exit(1);
        }

        for(int i = 10; i <= n; i+=10){
            int distinct = 0, words = 0;
            BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();

            // time
            double time = Double.NaN;
            // compute frequency counts
            try {
                System.out.println("Path is: " + path);
                Scanner scanner = new Scanner(new File(path));
                Pattern pattern = Pattern.compile("[^[a-zA-Z]]+");
                scanner.useDelimiter(pattern);
                // start timer
                long startTime = System.nanoTime();
                while(scanner.hasNext() && words < i*100){
                    String key = scanner.next();

                    if (key.length() < minlen) continue;
                    words++;
                    if (st.contains(key)) {
                        st.put(key, st.get(key) + 1);
                    }
                    else {
                        st.put(key, 1);
                        distinct++;
                    }
                }
                // stop timer
                long stopTime = System.nanoTime();
                time = stopTime - startTime;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }



            // find a key with the highest frequency count
            String max = "";
            st.put(max, 0);
            for (String word : st.keys()) {
                if (st.get(word) > st.get(max))
                    max = word;
            }

            System.out.println("n=" + i + " time=" + time);
            System.out.println("most used word  = " + max);
            System.out.println("frequency count = " + st.get(max));
            System.out.println("distinct        = " + distinct);
            System.out.println("words           = " + words);
        }

    }

}
