package net.wu_chinese.leetcode.queue;

import java.util.LinkedList;
import java.util.Queue;

public class _225_用队列实现栈 {
    private final Queue<Integer> queue;

    /**
     * Initialize your data structure here.
     */
    public _225_用队列实现栈() {
        queue = new LinkedList<>();
    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        queue.add(x);
        for (int i = queue.size(); i > 1; --i) {
            queue.add(queue.poll());
        }
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        return queue.remove();
    }

    /**
     * Get the top element.
     */
    public int top() {
        return queue.peek();
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return queue.isEmpty();
    }
}
