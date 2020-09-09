/*
 *  Author:         Simon Johannesson
 *  Email:          simonljohannesson@gmail.com, sijohann@kth.se
 *  Created:        2020-09-09
 *  Updated:
 *  Usage:          For normal operation, run program without any arguments. When prompted input data, the data will
 *                  print it back in reverse.
 *                  For testing, run program with assertions enabled (-ea)
 *  Based on:       None.
 */
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BalanceAssignHigher {
    /**
     * Check if parenthesis in input are balanced.
     * @param input input to check
     * @return true if balanced, false if not balanced
     */
    public static boolean parenthesisAreBalanced(String input){
        LinkedStackAssign2<Character> stack = new LinkedStackAssign2<>();
        try {
            for (Character c : input.toCharArray()) {
                if (c == '(' || c == '{' || c == '[') stack.push(c);
                else if (c == ')' || c == '}' || c == ']') {
                    Character popped = stack.pop();
                    if (c == ')' && popped == '(') ;
                    else if (c == ']' && popped == '[') ;
                    else if (c == '}' && popped == '{') ;
                    else return false;  // closing parenthesis does not match open parenthesis
                }
            }
        }catch (NoSuchElementException e){ return false; } // catches closing brackets when stack empty
        if (stack.isEmpty()) return true;  // stack empty all parenthesis accounted for
        return false;                      // stack not empty, missing closing parenthesis
    }

    private static String getInput(){
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder("");
            sb.append(sc.nextLine());
        return sb.toString();
    }

    public static void runTests(){
        String balanced = "{[[]]}() (({String string}{}))";
        String not_balanced = "{[[quality] things ]} ()(({}{))";
        String not_balanced_extra_at_end = "{}{{{()()}}} )))";
        String not_balanced_extra_at_beginning = "}}{}{{{()()}}} )))";

        boolean result;
        result = parenthesisAreBalanced(balanced);
        assert result == true;
        result = parenthesisAreBalanced(not_balanced);
        assert result == false;
        result = parenthesisAreBalanced(not_balanced_extra_at_end);
        assert result == false;
        result = parenthesisAreBalanced(not_balanced_extra_at_beginning);
        assert result == false;
        System.out.println("TESTS SUCCEEDED!");
    }

    public static void main(String[] args){

        // test if assertions are on
        boolean assertionsOn = false;
        try { assert false; }
        catch (AssertionError e){ assertionsOn = true; }

        if (assertionsOn){
            runTests();
        }else {
            System.out.println("Input data to check for balanced parenthesis:");
            boolean balanced = parenthesisAreBalanced(getInput());
            System.out.println(balanced ? "Parenthesis are balanced." : "Parenthesis not balanced.");
        }
    }
}
