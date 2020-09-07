/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-01
 *  Updated:
 *  Solves problem: Lab 1, assignment 2.
 *  Usage:          For normal operation, run program without any arguments.
 *                  When prompted input data, the data will print it back in reverse.
 *                  For testing, run program with '-T' as an argument.
 *  Based on:       None.
 */
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interface for classes that should take input and then print it out in reverse.
 */
interface ReverseInputPrinter{
    /**
     * Takes input from stdin until newline character is received then prints the input in reverse order.
     *
     * @throws IOException
     */
    void print() throws IOException;
}

/**
 * Implements functionality to take input from stdin and print it out in reverse order, implemented recursively.
 */
class RecursiveReverseInputPrinter implements ReverseInputPrinter{
    @Override
    public void print() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        print_recursive_worker(reader);
    }
    private static void print_recursive_worker(BufferedReader reader) throws IOException {
        char character = (char) reader.read();
        if (character != '\n') {
            print_recursive_worker(reader);
            System.out.print(character);
        }
    }
}

/**
 * Implements functionality to take input from stdin and print it out in reverse order, implemented iteratively.
 */
class IterativeReverseInputPrinter implements ReverseInputPrinter{
    @Override
    public void print() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LinkedStackAssign2<Character> input = new LinkedStackAssign2<>();

        char character;
        while ((character = (char) reader.read()) != '\n') {
            input.push(character);
        }

        while (!input.isEmpty()) {
            System.out.print(input.pop());
        }
    }
}
/**
 * Class that has methods that takes input from a user and reverses it.
 */
public class ReverseInputAssign2 {
    public static void main(String[] args) {
       if(hasTestFlag(args)) {
            run_test(new RecursiveReverseInputPrinter());
            run_test(new IterativeReverseInputPrinter());
       }
       else{
           try{

               System.out.println("Recursive: Please enter a string terminated by a newline.");
               new RecursiveReverseInputPrinter().print();
               System.out.println("\nRecursive: Please enter a string terminated by a newline.");
               new IterativeReverseInputPrinter().print();
           } catch (IOException e){
               e.printStackTrace();
           }
       }
    }
    /**
     * Checks if program was started with the test flag '-T' as an argument
     *
     * @param argv arguments that the program started with
     * @return true if flag i found, false if no flag is found
     */
    private static boolean hasTestFlag(String[] argv) {
        Pattern pattern = Pattern.compile("-T");
        for (String argument : argv){
            Matcher matcher = pattern.matcher(argument);
            if(matcher.matches()){
                return true;
            }
        }
        return false;
    }

    /**
     * Runs tests of the print method in any class that implements the ReverseInputPrinter interface.
     *
     * @param reverseInputPrinter class that should be tested
     */
    private static void run_test(ReverseInputPrinter reverseInputPrinter){
            System.out.println("Running test of " + reverseInputPrinter.getClass());
            try {
                // test cases
                String[] test_input = {"a\n", "abcd\n", "abcde\n", "1234567890\n", "aA&%$#@(\n"};
                String[] expected_output = {"a", "dcba", "edcba", "0987654321", "(@#$%&Aa"};
                String[] real_output = new String[5];

                // store default streams
                PrintStream defaultOut = System.out;
                InputStream defaultIn = System.in;

                // redirect STDOUT
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PrintStream newOut = new PrintStream(outputStream);
                System.setOut(newOut);

                // redirect STDIN and load with each prepared test input
                for(int i = 0; i < test_input.length; i++){
                    String next_input = test_input[i];
                    InputStream redirectedIn = new ByteArrayInputStream(next_input.getBytes());
                    System.setIn(redirectedIn);
                    reverseInputPrinter.print();
                    real_output[i] = outputStream.toString();
                    outputStream.reset();
                }

                // restore default STDOUT and STDIN stream
                System.setOut(defaultOut);
                System.setIn(defaultIn);

                //
                boolean test_failed = false;
                for(int i = 0; i < real_output.length; i++){
                    String result = real_output[i];
                    String expected = expected_output[i];
                    if(!(result.equals(expected))){
                        System.out.println(
                                "TEST FAILED: Real output '"
                                        + result
                                        + "' not equal to expected output ''"
                                        + expected
                                        + "'");
                        test_failed = true;
                    }
                }
                if(!test_failed){
                    System.out.println("TEST PASSED: " + real_output.length + " cases");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
