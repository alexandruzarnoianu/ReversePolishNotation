import java.util.*;

public class ReversePolishNotation {
    public static String formPostfix(String expression) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('+', 11);
        map.put('-', 11);
        map.put('*', 12);
        map.put('/', 12);
        map.put('^', 13);

        StringBuilder result = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();

        List<Character> operators = new ArrayList<>();
        operators.add('+');
        operators.add('-');
        operators.add('*');
        operators.add('/');
        operators.add('^');

        List<Character> parantheses = new ArrayList<>();
        parantheses.add(')');
        parantheses.add('(');
        parantheses.add(']');
        parantheses.add('[');
        parantheses.add('{');
        parantheses.add('}');

        for (int i = 0; i < expression.length(); i++) {
            if (Character.isDigit(expression.charAt(i))) {
                while (i <= expression.length() - 1 &&Character.isDigit(expression.charAt(i))) {
                    result.append(expression.charAt(i));
                    i++;
                }
                result.append(" ");
                i--;
            } else if (operators.contains(expression.charAt(i))) {
                Character nextInStack = stack.peekFirst();
                while (operators.contains(nextInStack)
                        && !nextInStack.equals('(')
                        && !nextInStack.equals('[')
                        && !nextInStack.equals('{')
                        && map.get(expression.charAt(i)) < map.get(nextInStack)
                        || map.get(expression.charAt(i)) == map.get(nextInStack)
                        && !nextInStack.equals('^')) {
                    result.append(stack.removeFirst());
                    result.append(" ");
                    nextInStack = stack.peekFirst();
                }
                stack.addFirst(expression.charAt(i));
            } else if (expression.charAt(i) == '('
                    || expression.charAt(i) == '['
                    || expression.charAt(i) == '{') {
                stack.addFirst(expression.charAt(i));
            } else if (expression.charAt(i) == ')'
                    || expression.charAt(i) == ']'
                    || expression.charAt(i) == '}') {
                Character nextInStack = stack.peekFirst();
                while (!nextInStack.equals('(') && !nextInStack.equals('[') && !nextInStack.equals('{')) {
                    result.append(stack.removeFirst());
                    result.append(" ");
                    nextInStack = stack.peekFirst();
                }
                if (stack.isEmpty()) {
                    return "Wrong expression";
                }
                stack.removeFirst();
            }
        }

        while (!stack.isEmpty()) {
            if (operators.contains(stack.peekFirst())) {
                result.append(stack.removeFirst());
                result.append(" ");
            } else if (parantheses.contains(stack.peekFirst())) {
                return "Wrong expression";
            }
        }
        return result.toString();
    }

    public static int evaluateNotation(String postfix) {
        int result = 0;
        boolean error = false;
        Deque<String> stack = new ArrayDeque<>();
        for (int i = 0; i < postfix.length(); i++) {
            if (Character.isDigit(postfix.charAt(i))) {
                StringBuilder sb = new StringBuilder();
                while (Character.isDigit(postfix.charAt(i))) {
                    sb.append(postfix.charAt(i));
                    i++;
                }
                stack.addFirst(sb.toString());
            } else if (postfix.charAt(i) == '+'
                    || postfix.charAt(i) == '-'
                    || postfix.charAt(i) == '*'
                    || postfix.charAt(i) == '/'
                    || postfix.charAt(i) == '^') {
                if (stack.size() < 2) {
                    error = true;
                    break;
                } else {
                    Integer op1 = Integer.parseInt(stack.removeFirst());
                    Integer op2 = Integer.parseInt(stack.removeFirst());
                    Integer op3 = 0;
                    switch (postfix.charAt(i)) {
                        case '+' : op3 = op2 + op1; break;
                        case '-' : op3 = op2 - op1; break;
                        case '*' : op3 = op2 * op1; break;
                        case '/' : op3 = op2 / op1; break;
                        case '^' : {
                            if (op1 == 0) {
                                op3 = 1;
                            } else if (op1 == 1) {
                                op3 = op2;
                            } else {
                                int steps = op1;
                                int multiplier = op2;
                                while (steps != 1) {
                                    op2 *= multiplier;
                                    steps--;
                                }
                                op3 = op2;
                            }
                        }
                    }
                    stack.addFirst(String.valueOf(op3));
                }
            }
        }
        if (stack.size() == 1 && !error) {
            result = Integer.parseInt(stack.removeFirst());
        } else {
            System.out.println("Wrong expression");
            return -1;
        }
        return result;
    }
}
