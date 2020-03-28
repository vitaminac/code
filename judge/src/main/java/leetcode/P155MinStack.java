package leetcode;


import java.util.Stack;

// https://leetcode.com/problems/min-stack
class MinStack {
    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> minStack = new Stack<>();

    /**
     * initialize your data structure here.
     */
    public MinStack() {
    }

    public void push(int n) {
        if (this.minStack.isEmpty() || n <= this.minStack.peek()) {
            this.minStack.push(n);
        }
        this.stack.push(n);
    }

    public void pop() {
        if (this.stack.pop().equals(this.minStack.peek())) this.minStack.pop();
    }

    public int top() {
        return this.stack.peek();
    }

    public int getMin() {
        return this.minStack.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
