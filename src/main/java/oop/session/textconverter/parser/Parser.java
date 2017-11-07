package oop.session.textconverter.parser;

public class Parser {
    private String string;

    public Parser(String string) {
        this.string = string;
    }

    public void parse() {
        System.out.println(this.string.split("|"));
    }
}
