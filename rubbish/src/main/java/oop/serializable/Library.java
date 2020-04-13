package oop.serializable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Library implements Serializable {
    private final List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }


    public List<Book> getBooks() {
        return books;
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

    @Override
    public void write(Writer writer) throws IOException {
        final PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println("<library>");
        for (Book book : this.books) {
            printWriter.print(book.serialize());
        }
        printWriter.println("</library>");
    }

    @Override
    public void read(Reader reader) throws IOException {
        final BufferedReader br = new BufferedReader(reader);
        assert "<library>".equals(br.readLine());
        br.mark(100);
        while (!"</library>".equals(br.readLine())) {
            br.reset();
            final Book book = new Book();
            book.read(br);
            this.addBook(book);
        }
    }
}
