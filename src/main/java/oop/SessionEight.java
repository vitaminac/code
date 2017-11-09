package oop;

import oop.converter.exception.NotNumber;
import oop.converter.exception.ParseError;
import oop.converter.exception.SuperTypeValueError;
import oop.converter.parser.Parser;
import oop.converter.type.SuperType;

import java.util.ArrayList;

public class SessionEight {
    public static void main(String[] args) {
        String[] cadenas = new String[11];
        cadenas[0] = "23|entero";
        cadenas[1] = "45|entero";
        cadenas[2] = "-236|natural";
        cadenas[3] = "asd|entero";
        cadenas[4] = "99|texto";
        cadenas[5] = "99natural";
        cadenas[6] = "99|";
        cadenas[7] = "natural|natural";
        cadenas[8] = "89||";
        cadenas[9] = "89|fake";
        cadenas[10] = "96|entero";
        ArrayList<SuperType> supertypes = new ArrayList<SuperType>();
        for (String cadena : cadenas) {
            try {
                supertypes.add(Parser.parse(cadena));
            } catch (SuperTypeValueError e) {
                continue;
            } catch (ParseError error) {
                continue;
            }
        }
        int resultInt = 0;
        String resultString = "";
        for (SuperType sp : supertypes) {
            try {
                resultString += sp.toStringValue();
                resultInt += sp.toIntValue();
            } catch (NotNumber e) {
                continue;
            }
        }
        System.out.println(resultInt);
        System.out.println(resultString);
    }
}
