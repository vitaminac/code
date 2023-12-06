package parser;

import java.util.Iterator;

import core.functional.Iterable;
import collections.linkedlist.SinglyLinkedListDoubleReference;
import collections.linkedlist.Steque;

/**
 * <expression> ::= <whitespace><term><more-expression>
 * <more-expression> ::= <whitespace>+<expression> | <whitespace>-<expression> | <whitespace>*<expression> | <whitespace>/<expression> | <whitespace>
 * <term> ::= (<expression>) | -<expression> | <positive-floating-number>
 * <positive-floating-number> ::= <positive-integer><decimal-part>
 * <decimal-part> ::= .<positive-integer> | <empty>
 * <positive-integer> ::= [0-9]+
 * <whitespace> ::= \s*
 */
public class ExpressionTokenizer implements Iterable<String> {
    public static final String LEFT_PARENTHESES = "(";
    public static final String RIGHT_PARENTHESES = ")";
    public static final String UNARY_OPERATOR_MINUS = "U-";
    public static final String BINARY_OPERATOR_ADD = "B+";
    public static final String BINARY_OPERATOR_MINUS = "B-";
    public static final String BINARY_OPERATOR_MUL = "B*";
    public static final String BINARY_OPERATOR_DIV = "B/";

    private final Steque<String> queue = new SinglyLinkedListDoubleReference<>();

    public ExpressionTokenizer(final String expression) {
        parseExpression(expression.toCharArray(), 0);
    }

    private int parseExpression(final char[] expression, int index) {
        index = parseWhitespace(expression, index);
        index = parseTerm(expression, index);
        index = parseMoreExpression(expression, index);
        return index;
    }

    private int parseMoreExpression(final char[] expression, int index) {
        index = parseWhitespace(expression, index);
        if (index < expression.length) {
            if (expression[index] == '+') {
                queue.append(BINARY_OPERATOR_ADD);
                return parseExpression(expression, index + 1);
            }
            if (expression[index] == '-') {
                queue.append(BINARY_OPERATOR_MINUS);
                return parseExpression(expression, index + 1);
            }
            if (expression[index] == '*') {
                queue.append(BINARY_OPERATOR_MUL);
                return parseExpression(expression, index + 1);
            }
            if (expression[index] == '/') {
                queue.append(BINARY_OPERATOR_DIV);
                return parseExpression(expression, index + 1);
            }
        }
        return index;
    }

    private int parseTerm(final char[] expression, int index) {
        if (expression[index] == '(') {
            queue.append(LEFT_PARENTHESES);
            index = parseExpression(expression, index + 1);
            queue.append(RIGHT_PARENTHESES);
            return index + 1;
        } else if (expression[index] == '-') {
            queue.append(UNARY_OPERATOR_MINUS);
            return parseExpression(expression, index + 1);
        } else {
            return parsePositiveFloatingNumber(expression, index);
        }
    }

    private int parsePositiveFloatingNumber(final char[] expression, int index) {
        final var sb = new StringBuilder();
        index = parsePositiveInteger(expression, index, sb);
        index = parseDecimalPart(expression, index, sb);
        queue.append(sb.toString());
        return index;
    }

    private int parseDecimalPart(final char[] expression, int index, final StringBuilder sb) {
        if (index < expression.length && expression[index] == '.') {
            sb.append(expression[index++]);
            return parsePositiveInteger(expression, index, sb);
        }
        return index;
    }

    private int parseWhitespace(final char[] expression, int index) {
        while (index < expression.length && Character.isWhitespace(expression[index])) {
            index += 1;
        }
        return index;
    }

    private int parsePositiveInteger(final char[] expression, int index, final StringBuilder sb) {
        do {
            sb.append(expression[index++]);
        } while (index < expression.length && Character.isDigit(expression[index]));
        return index;
    }

    @Override
    public Iterator<String> iterator() {
        return this.queue.iterator();
    }
}
