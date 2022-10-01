package wiki.mdzz.leetcode.stack;

import java.util.HashMap;
import java.util.Stack;

public class _20_有效的括号 {

    //  private HashMap<Character, Character> map = new HashMap<>();
    private static final HashMap<Character, Character> map = new HashMap<>();

    /*
    public _20_有效的括号() {
      map.put('(', ')');
      map.put('[', ']');
      map.put('{', '}');
    }
    */
    static {
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
    }

    public boolean isValid1(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                char left = stack.pop();
                if (map.get(left) != c) return false;
                if (left == '[' && c != ']') return false;
                if (left == '{' && c != '}') return false;
            }
        }
        return stack.isEmpty();
    }

    public boolean isValid2(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                if (map.get(stack.pop()) != c) return false;
            }
        }
        return false;
    }
}
