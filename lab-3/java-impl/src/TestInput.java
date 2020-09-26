import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TestInput {
    public static void main(String[] args){
//        Scanner scanner = new Scanner(System.in);
//        Pattern pattern = Pattern.compile("[^[a-zA-Z]]+");
//        scanner.useDelimiter(pattern);
//        while(scanner.hasNext()){
//            System.out.println(scanner.next());
//        }
        try {
            String path = "";
            if (args.length > 0) path = args[0];
            System.out.println("Path is: " + path);
            Scanner scanner = new Scanner(new File(path));
            Pattern pattern = Pattern.compile("[^[a-zA-Z]]+");
            scanner.useDelimiter(pattern);
            while(scanner.hasNext()){
                System.out.println(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
