package oop.converter.type;

import oop.converter.exception.NotNumber;

public class Texto extends SuperType {
    private String text;

    public Texto(String text) {
        this.text = text;
    }

    @Override
    public String toStringValue() {
        return this.text;
    }

    @Override
    public int toIntValue() throws NotNumber {
        throw new NotNumber();
    }
}
