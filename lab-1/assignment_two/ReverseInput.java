import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that has methods that takes input from a user and reverses it.
 */
public class ReverseInput {
    /**
     * Takes input from stdin until newline character is received then prints the input in reverse order.
     * <p>
     * Does not print the newline character, recursive implementation.
     *
     * @param reader
     * @throws IOException
     */
    private static void print_recursive_worker(BufferedReader reader) throws IOException {
        char character = (char) reader.read();
        if (character != '\n') {
            print_recursive_worker(reader);
            System.out.print(character);
        }
    }

    public static void print_recursive() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        print_recursive_worker(reader);
    }

    public static void print_iterative() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LinkedListStack<Character> input = new LinkedListStack<>();

        char character;
        while ((character = (char) reader.read()) != '\n') {
            input.push(character);
        }

        while (!input.isEmpty()) {
            System.out.print(input.pop());
        }
    }

    public static void main(String[] args) {
       if(hasTestFlag(args)) {
           System.out.println("Running test of ReverseInput class:");
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
               PrintStream fakeOut = new PrintStream(outputStream);
               System.setOut(fakeOut);

               // redirect STDIN and load with each prepared test input
               for(int i = 0; i < test_input.length; i++){
                   String next_input = test_input[i];
                   InputStream redirectedIn = new ByteArrayInputStream(next_input.getBytes());
                   System.setIn(redirectedIn);
                   ReverseInput.print_iterative();
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
       else{
           try{

               System.out.println("Recursive: Please enter a string terminated by a newline.");
               print_recursive();
               System.out.println("\nRecursive: Please enter a string terminated by a newline.");
               print_iterative();
           } catch (IOException e){
               e.printStackTrace();
           }
       }
    }

    /**
     * Checks if program was started with the test flag '-T' as an argument
     *
     * @param argv
     * @return
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
}
