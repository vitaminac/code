import java.util.Objects;

public class TestPrototype implements Prototype {
    private int number;

    public TestPrototype() {
        this((int) (Math.random() * Integer.MAX_VALUE));
    }

    public TestPrototype(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestPrototype that = (TestPrototype) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public Object clone() {
        return new TestPrototype(this.number);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
