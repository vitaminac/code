import java.util.Objects;

public class TestAnnotatedPrototypeMore {
    private int number;

    public TestAnnotatedPrototypeMore(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestAnnotatedPrototypeMore that = (TestAnnotatedPrototypeMore) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
