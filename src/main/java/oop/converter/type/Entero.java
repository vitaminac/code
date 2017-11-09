package oop.converter.type;

import oop.converter.exception.NotNumber;

public class Entero extends SuperType {
    private int number;

    public Entero(String number) throws NotNumber {
        try {
            this.number = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new NotNumber();
        }
    }

    @Override
    public int toIntValue() {
        return this.number;
    }

    @Override
    public String toStringValue() {
        return String.valueOf(this.number);
    }
}
