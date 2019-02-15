package oop;

import oop.serializable.Book;
import oop.serializable.Library;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class SessionTen {
    private Book book;
    private Library library;

    @Before
    public void setUp() {
        this.book = new Book("Ken Follet", "Los pilares de la Tierra", new BigInteger("9781234567890"), 764, 2000, "Planeta");
        this.library = new Library();
        this.library.addBook(this.book);
    }

    @Test
    public void testBook() {
        final String encoded = this.book.serialize();
        final Book clone = new Book();
        clone.deserialize(encoded);
        assertEquals("deberia ser los mismo", book, clone);

    }

    @Test
    public void testLibrary() {
        final String encoded = this.library.serialize();
        Library clone = new Library();
        clone.deserialize(encoded);
        assertEquals("deberia ser los mismo", this.library, clone);
    }
}
