package core.behaviour;

public interface Immutable<Self extends Immutable<Self>> extends Cloneable<Self> {
    @Override
    @SuppressWarnings("unchecked")
    default Self clone() {
        return (Self) this;
    }
}
