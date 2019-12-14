package code.algs4;

import code.adt.LinkedList;
import code.adt.Stack;

import java.util.function.Consumer;

/**
 * https://algs4.cs.princeton.edu/13stacks/
 * 5. Stack + max. Create a data structure
 * that efficiently supports the stack operations (pop and push)
 * and also return the maximum element.
 * Assume the elements are integers or reals so that you can compare them.
 * Hint: use two stacks, one to store all of the elements and a second stack to store the maximums.
 */
public class StackMax implements Stack<Integer> {
    private Stack<Integer> stack = new LinkedList<>();
    private Stack<Integer> maxStack = new LinkedList<>();

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public Integer top() {
        return this.stack.top();
    }

    @Override
    public void push(Integer n) {
        if (this.maxStack.isEmpty() || n >= this.maxStack.top()) {
            this.maxStack.push(n);
        }
        this.stack.push(n);
    }

    @Override
    public Integer pop() {
        int n = this.stack.pop();
        if (n == this.maxStack.top()) this.maxStack.pop();
        return n;
    }

    public int max() {
        return this.maxStack.top();
    }

    @Override
    public void enumerate(Consumer<Integer> consumer) {
        this.stack.enumerate(consumer);
    }
}
