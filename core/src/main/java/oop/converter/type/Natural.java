package oop.converter.type;

import oop.converter.exception.NotNumber;
import oop.converter.exception.NotPositive;

public class Natural extends Entero {

    public Natural(String natural) throws NotPositive, NotNumber {
        super(natural);
        if (this.toIntValue() < 0) {
            throw new NotPositive();
        }
    }
}
