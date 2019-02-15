package oop;

import oop.serializable.ArgumentRequiredException;
import oop.serializable.Book;
import oop.serializable.Library;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class SessionTen {
    private Book book;
    private Library library;

    @Test
    public void testBook() {
        this.book.writeTo("json.txt");
        final Book newBook = new Book();
        newBook.readFrom("json.txt");
        assertEquals("deberia ser los mismo", book, newBook);

    }

    @Before
    public void setUp() throws Exception {
        try {
            this.book = new Book("Ken Follet", "Los pilares de la Tierra", new BigInteger("9781234567890"), 764, 2000, "Planeta");
        } catch (ArgumentRequiredException e) {
            e.printStackTrace();
        }
        this.library = new Library();
        this.library.addBook(this.book);
    }

    @Test
    public void testLibrary() {
        this.library.writeTo("palalala");
        Library newLibrary = new Library();
        newLibrary.readFrom("palalala");
    }
}
