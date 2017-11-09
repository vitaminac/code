package oop.converter.parser;

import org.junit.jupiter.api.Test;

class ParserTest {
    @Test
    public void testParse() {
        try {
            System.out.println(Parser.parse("23|entero")
                                     .toIntValue());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}