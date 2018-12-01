package config;

import java.util.Objects;


public class TestPrototypeImpl implements TestPrototype {
    private int state;

    public TestPrototypeImpl() {
        this(0);
    }

    public TestPrototypeImpl(int state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestPrototypeImpl that = (TestPrototypeImpl) o;
        return state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

    public int getState() {
        return state;
    }

    public void changeState(int state) {
        this.state = state;
    }
}
