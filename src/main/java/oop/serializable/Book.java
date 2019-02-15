package oop.serializable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.math.BigInteger;

public class Book implements Serializable<Book> {
    private String author;
    private String title;
    private BigInteger ISBN;
    private int pages;
    private int publication;
    private String editorial;

    public Book() {
    }

    public Book(String author, String title, BigInteger ISBN, int pages, int publication, String editorial) {
        this.setAuthor(author);
        this.setTitle(title);
        this.setISBN(ISBN);
        this.setPages(pages);
        this.setPublication(publication);
        this.setEditorial(editorial);
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        } else {
            if (obj instanceof Book) {
                Book other = (Book) obj;
                return this.getAuthor().equals(other.getAuthor()) &&
                        this.getTitle().equals(other.getTitle()) &&
                        this.getISBN().equals(other.getISBN()) &&
                        this.getPages() == other.getPages() &&
                        this.getPublication() == other.getPublication() &&
                        this.getEditorial().equals(other.getEditorial());
            } else {
                return false;
            }
        }
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        if ((author == null) || (author.isEmpty())) {
            throw new IllegalArgumentException("author");
        }
        this.author = author;
    }

    public String getTitle() {
        return this.title;
    }

    public BigInteger getISBN() {
        return this.ISBN;
    }

    public void setISBN(BigInteger ISBN) {
        if (ISBN.toString().length() != 13 || ISBN.signum() < 0) {
            throw new IllegalArgumentException("ISBN");
        }
        this.ISBN = ISBN;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPublication() {
        return this.publication;
    }

    public String getEditorial() {
        return this.editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setPublication(int publication) {
        this.publication = publication;
    }

    public void setTitle(String title) {
        if (title == null || title.equals("")) {
            throw new IllegalArgumentException("title");
        }
        this.title = title;
    }

    @Override
    public void write(Writer writer) {
        final PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println("<book>");
        printWriter.println("<author>");
        printWriter.println(this.getAuthor());
        printWriter.println("</author>");
        printWriter.println("<title>");
        printWriter.println(this.getTitle());
        printWriter.println("</title>");
        printWriter.println("<ISBN>");
        printWriter.println(this.getISBN());
        printWriter.println("</ISBN>");
        printWriter.println("<pages>");
        printWriter.println(this.getPages());
        printWriter.println("</pages>");
        printWriter.println("<publication>");
        printWriter.println(this.getPublication());
        printWriter.println("</publication>");
        printWriter.println("<editorial>");
        printWriter.println(this.getEditorial());
        printWriter.println("</editorial>");
        printWriter.println("</book>");
    }

    @Override
    public void read(Reader reader) throws IOException {
        final BufferedReader br = new BufferedReader(reader);
        assert "<book>".equals(br.readLine());
        assert "<author>".equals(br.readLine());
        this.setAuthor(br.readLine());
        assert "</author>".equals(br.readLine());
        assert "<title>".equals(br.readLine());
        this.setTitle(br.readLine());
        assert "</title>".equals(br.readLine());
        assert "<ISBN>".equals(br.readLine());
        this.setISBN(new BigInteger(br.readLine()));
        assert "</ISBN>".equals(br.readLine());
        assert "<pages>".equals(br.readLine());
        this.setPages(Integer.parseInt(br.readLine()));
        assert "</pages>".equals(br.readLine());
        assert "<publication>".equals(br.readLine());
        this.setPublication(Integer.parseInt(br.readLine()));
        assert "</publication>".equals(br.readLine());
        assert "<editorial>".equals(br.readLine());
        this.setEditorial(br.readLine());
        assert "</editorial>".equals(br.readLine());
        assert "</book>".equals(br.readLine());
    }
}
