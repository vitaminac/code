package core.behaviour;

public interface Cloneable<Self extends Cloneable<Self>> {
    Self clone();
}
