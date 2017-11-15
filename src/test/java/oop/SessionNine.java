package oop;

import oop.serializable.ArgumentRequiredException;
import oop.serializable.Book;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;


public class SessionNine {
    @Test
    public void testBook() {
        try {
            Book book = new Book("Ken Follet", "Los pilares de la Tierra", new BigInteger("9781234567890"), 764, 2000, "Planeta");
            assertEquals("Author : Ken Follet, Title : Los pilares de la Tierra, ISBN : 9781234567890, Pages : 764, Publication : 2000, Editorial : Planeta", book.serialize());
            assertEquals("<xml>\n\n" +
                         "<Author>\nKen Follet\n</Author>\n" +
                         "<Title>\nLos pilares de la Tierra\n</Title>\n" +
                         "<ISBN>\n9781234567890\n</ISBN>\n" +
                         "<Pages>\n764\n</Pages>\n" +
                         "<Publication>\n2000\n</Publication>\n" +
                         "<Editorial>\nPlaneta\n</Editorial>\n" +
                         "</xml>", book.serializeToXML());
        } catch (ArgumentRequiredException e) {
            e.printStackTrace();
        }
    }
}
