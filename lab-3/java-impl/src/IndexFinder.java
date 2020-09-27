import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;

public class IndexFinder {
    public static void main(String[] args){
        int minlen = 1;
        int n = 1;
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

        /* scan file, print each word */
        BST<String, LinkedList<Integer>> bst = new BST<>();


        try {
            File file = new File(path);
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
                    while (Character.isLetter(c = (char) di.readByte())) {
                        ++index;
                        wordBuilder.append(c);
                    }
                    ++index; // increment one for the last char that was not a letter
                    // here a whole word and it's start index is available
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
