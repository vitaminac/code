package oop.converter.parser;

import oop.converter.exception.NotNumber;
import oop.converter.exception.NotPositive;
import oop.converter.exception.ParseError;
import oop.converter.type.Entero;
import oop.converter.type.Natural;
import oop.converter.type.SuperType;
import oop.converter.type.Texto;

public class Parser {
    private String string;

    public static SuperType parse(String str) throws ParseError, NotPositive, NotNumber {
        String[] parts = str.split("[|]");
        if (parts.length != 2) {
            throw new ParseError();
        }
        switch (parts[1]) {
            case "natural":
                return new Natural(parts[0]);
            case "entero":
                return new Entero(parts[0]);
            case "texto":
                return new Texto(parts[0]);
            default:
                throw new ParseError();
        }
    }
}
