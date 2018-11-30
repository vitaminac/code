package provider;

import injection.Injectable;
import injection.Scope;

import java.util.Objects;

@Injectable(scope = Scope.Prototype)
public class TestPrototype {
    private int number;

    public TestPrototype() {
        this(0);
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
