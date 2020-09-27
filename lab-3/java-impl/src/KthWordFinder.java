import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KthWordFinder {


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
        int order = bst.size(); // bst and bstKth have different sizes
        System.out.println("size: " + bstKth.size());
        System.out.println("size: " + bst.size());

        HashTable<Integer, String> ht = new HashTable<>(bst.size()/4);
        for(Integer occurences : bstKth.keys()){
            for(String word : bstKth.get(occurences)){
                ht.add(order--, word);
            }
        }

        // query the bst for indices

        Scanner scanner = new Scanner(System.in);
        Pattern kToKpattern = Pattern.compile("\\s*\\d+\\s*-\\s*\\d+\\s*");
        Pattern first = Pattern.compile("\\d+(?=\\s*-{1})");
        Pattern second = Pattern.compile("\\d+(?!\\s*-{1})");
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
                        System.out.println("k = " + k1 + " & " + k2);
                        if(k1>=k2){
                            System.out.println("k1 can't be larger than k2");
                            continue;
                        }
                        // perform lookup
                        SingleLinkedList<String> results = new SingleLinkedList<>();
                        for(int i = k1; i<=k2; i++){
                            String word = ht.get(i);
                            if(word != null)  results.addFirst(word);
                            // TODO: implement addLast because the words are presented in incorrect order
                        }
                        for(String word : results){
                            System.out.println(word);
                        }
                    }
                }
                else{
                    int k = Integer.decode(input);
                    System.out.println("k = " + k);
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

//        // query the bst for indices
//        Scanner scanner = new Scanner(System.in);
//        Integer input;
//        System.out.println("Shows k:th most common word. Choose k: ");
//        while((scanner.hasNext())){
//            try{
//                input = Integer.decode(scanner.next());
//                if(input < 1) throw new NumberFormatException();
////                LinkedList<Integer, String> result = ht.get(input);
//                String result = ht.get(input);
//                if(result == null){
//                    System.out.println("No words matching that k.");
//                }else{
//                    System.out.println(result);
//                }
//            } catch (NumberFormatException e){
//                System.out.println("Not a valid k-value.");
//                continue;
//            }
//            System.out.println("Shows k:th most common word. Choose k: ");
//        }
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