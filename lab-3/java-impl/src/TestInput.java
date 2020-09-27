import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestInput {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        Pattern kToKpattern = Pattern.compile("\\s*\\d+\\s*-\\s*\\d+\\s*");
        Pattern first = Pattern.compile("\\d+(?=\\s*-{1})");
        Pattern second = Pattern.compile("\\d+(?!\\s*-{1})");
        scanner.useDelimiter("\n");

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
                        // perform lookup

                    }
                }
                else{
                    int k = Integer.decode(input);
                    System.out.println("k = " + k);
                    // perform lookup

                }


            }catch (NumberFormatException e){
                System.out.println("Incorrect input format.");
                continue;
            }
        }

    }
}
