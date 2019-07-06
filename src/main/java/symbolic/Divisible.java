package symbolic;

public interface Divisible {
    Expression greatestCommonFactor(Divisible other);

    Expression divide(Expression divisor);
}
