public class BalanceAssignHigher {

    public static boolean parenthesisAreBalanced(String input){
         LinkedStackAssign2<Character> stack = new LinkedStackAssign2<>();

         for (Character c : input.toCharArray()){
            if      (c == '('  || c == '{'  ||  c == '[') stack.push(c);

            else if (c == ')'  || c == '}'  ||  c == ']') {
                Character popped = stack.pop();
                if      (c == ')'  && popped == '(');
                else if (c == ']'  && popped == '[');
                else if (c == '}'  && popped == '{');
                else return false;
            }
         }
         if (stack.isEmpty()) return true;
         return false;
    }

    public static void main(String[] args){

        String balanced = "{[[]]}() (({String string}{}))";
        String not_balanced = "{[[quality] things ]} ()(({}{))";
        System.out.println(parenthesisAreBalanced(balanced));
        System.out.println(parenthesisAreBalanced(not_balanced));


    }
}
