package oop.serializable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;

public class Library implements Serializable {
    private final HashSet<Book> books;

    public Library() {
        this.books = new HashSet<>();
    }

    @Override
    public String serialize() {
        ArrayList<String> strings = new ArrayList<>();
        for (Book book : this.getBooks()) {
            strings.add(book.serialize());
        }
        return new Gson().toJson(strings, new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    public HashSet<Book> getBooks() {
        return books;
    }

    @Override
    public void deserialize(String text) {
        ArrayList<String> strings = new Gson().fromJson(text, new TypeToken<ArrayList<String>>() {
        }.getType());
        for (String string : strings) {
            Book book = new Book();
            book.deserialize(string);
            this.getBooks().add(book);
        }
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Library) {
            Library other = (Library) obj;
            return this.getBooks().equals(other.getBooks());
        } else {
            return false;
        }
    }
}
