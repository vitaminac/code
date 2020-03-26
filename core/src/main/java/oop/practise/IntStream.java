package oop.practise;

public class IntStream {
    private final int[] elements;

    public IntStream(int[] elements) {
        this.elements = elements;
    }


    public IntStream map(IntUnaryOperator function) {
        int[] results = new int[this.elements.length];
        for (int i = 0; i < this.elements.length; i++) {
            results[i] = function.applyAsInt(this.elements[i]);
        }
        return new IntStream(results);
    }

    public int reduce(IntBinaryOperator operator) {
        int result = 0;
        for (int e : this.elements) {
            result = operator.applyAsInt(result, e);
        }
        return result;
    }

    public int[] toArray() {
        return this.elements;
    }
}
