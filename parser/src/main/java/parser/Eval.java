package parser;

import collections.deque.LinkedListDeque;
import collections.linkedlist.DoublyLinkedList;
import collections.map.MutableMap;
import collections.hashtable.SeparateChainingHashTable;
import collections.stack.Stack;

import java.util.function.Function;

import static parser.ExpressionTokenizer.*;

public final class Eval {
    private static final MutableMap<String, Integer> PRECEDENCE;

    static {
        PRECEDENCE = MutableMap.fromHashTable(SeparateChainingHashTable::new);
        PRECEDENCE.put(BINARY_OPERATOR_ADD, 1);
        PRECEDENCE.put(BINARY_OPERATOR_MINUS, 1);
        PRECEDENCE.put(BINARY_OPERATOR_MUL, 2);
        PRECEDENCE.put(BINARY_OPERATOR_DIV, 2);
        PRECEDENCE.put(UNARY_OPERATOR_MINUS, 3);
    }

    private Eval() {
    }

    private static boolean isOperator(final String token) {
        return PRECEDENCE.get(token) != null;
    }

    private static boolean isUnaryOperator(final String token) {
        return UNARY_OPERATOR_MINUS.equals(token);
    }

    public interface UnaryOperatorApplier<T> {
        T apply(final String op, final T value);
    }

    public interface BinaryOperatorApplier<T> {
        T apply(final String op, final T right, final T left);
    }

    private static <T> void evaluateLastOperator(final Stack<String> ops,
                                                 final Stack<T> values,
                                                 final BinaryOperatorApplier<T> binaryOperatorApplier,
                                                 final UnaryOperatorApplier<T> unaryOperatorApplier) {
        final var operator = ops.pop();
        if (isUnaryOperator(operator)) {
            values.push(unaryOperatorApplier.apply(operator, values.pop()));
        } else {
            values.push(binaryOperatorApplier.apply(operator, values.pop(), values.pop()));
        }
    }

    public static <T> T eval(final String expression,
                             final Function<String, T> valueParser,
                             final BinaryOperatorApplier<T> binaryOperatorApplier,
                             final UnaryOperatorApplier<T> unaryOperatorApplier) {
        final var tk = new ExpressionTokenizer(expression);
        Stack<String> ops = Stack.fromDeque(() -> new LinkedListDeque<>(DoublyLinkedList::new));
        Stack<T> values = Stack.fromDeque(() -> new LinkedListDeque<>(DoublyLinkedList::new));
        for (final var token : tk) {
            if (LEFT_PARENTHESES.equals(token)) {
                ops.push(token);
            } else if (RIGHT_PARENTHESES.equals(token)) {
                while (!ops.peek().equals(LEFT_PARENTHESES)) {
                    evaluateLastOperator(ops, values, binaryOperatorApplier, unaryOperatorApplier);
                }
                ops.pop();
            } else if (isOperator(token)) {
                while (!ops.isEmpty() && !LEFT_PARENTHESES.equals(ops.peek()) && PRECEDENCE.get(token) <= PRECEDENCE.get(ops.peek())) {
                    evaluateLastOperator(ops, values, binaryOperatorApplier, unaryOperatorApplier);
                }
                ops.push(token);
            } else {
                values.push(valueParser.apply(token));
            }
        }
        while (!ops.isEmpty()) {
            evaluateLastOperator(ops, values, binaryOperatorApplier, unaryOperatorApplier);
        }
        return values.pop();
    }

    private static double apply(final String op, final double value) {
        switch (op) {
            case UNARY_OPERATOR_MINUS:
                return -value;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static double apply(final String op, final double right, final double left) {
        switch (op) {
            case BINARY_OPERATOR_MUL:
                return left * right;
            case BINARY_OPERATOR_DIV:
                return left / right;
            case BINARY_OPERATOR_ADD:
                return left + right;
            case BINARY_OPERATOR_MINUS:
                return left - right;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static double evalAsDouble(final String expression) {
        return eval(expression, Double::parseDouble, Eval::apply, Eval::apply);
    }

    private static int apply(final String op, final int value) {
        switch (op) {
            case UNARY_OPERATOR_MINUS:
                return -value;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static int apply(final String op, final int right, final int left) {
        switch (op) {
            case BINARY_OPERATOR_MUL:
                return left * right;
            case BINARY_OPERATOR_DIV:
                return left / right;
            case BINARY_OPERATOR_ADD:
                return left + right;
            case BINARY_OPERATOR_MINUS:
                return left - right;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static int evalAsInteger(final String expression) {
        return eval(expression, Integer::parseInt, Eval::apply, Eval::apply);
    }
}
