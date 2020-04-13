package oop.converter.type;

import oop.converter.exception.NotNumber;

public abstract class SuperType {
    abstract public int toIntValue() throws NotNumber;

    abstract public String toStringValue();
}
