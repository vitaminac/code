package symbolic;

public interface Divisible {
    public abstract Expression greatestCommonFactor(Divisible other);

    public abstract Expression divide(Expression divisor);
}
