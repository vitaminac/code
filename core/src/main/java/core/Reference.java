package core;

public class Reference<T> {
    private T reference;

    public Reference(T reference) {
        this.reference = reference;
    }

    public T getReference() {
        return reference;
    }

    public void setReference(T reference) {
        this.reference = reference;
    }
}
