/******************************************************************************
 *  Compilation:  javac FrequencyCounter.java
 *  Execution:    java FrequencyCounter L < the-text.txt.txt
 *  Dependencies: ST.java StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/31elementary/tinyTale.txt
 *                https://algs4.cs.princeton.edu/31elementary/tale.txt
 *                https://algs4.cs.princeton.edu/31elementary/leipzig100K.txt
 *                https://algs4.cs.princeton.edu/31elementary/leipzig300K.txt
 *                https://algs4.cs.princeton.edu/31elementary/leipzig1M.txt
 *
 *  Read in a list of words from standard the-text.txt and print out
 *  the most frequently occurring word that has length greater than
 *  a given threshold.
 *
 *  % java FrequencyCounter 1 < tinyTale.txt
 *  it 10
 *
 *  % java FrequencyCounter 8 < tale.txt
 *  business 122
 *
 *  % java FrequencyCounter 10 < leipzig1M.txt
 *  government 24763
 *
 *
 ******************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *  The {@code FrequencyCounter} class provides a client for
 *  reading in a sequence of words and printing a word (exceeding
 *  a given length) that occurs most frequently. It is useful as
 *  a test client for various symbol table implementations.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/31elementary">Section 3.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
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
