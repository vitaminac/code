package oop.serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import oop.xml.XMLNode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.UnaryOperator;

public class Book implements Serializable {
    private String author;
    private String title;
    private BigInteger ISBN;
    private int pages;
    private int publication;
    private String editorial;

    public Book(String author, String title, BigInteger ISBN) throws ArgumentRequiredException {
        this(author, title, ISBN, 0, 0, "");
    }

    public Book(String author, String title, BigInteger ISBN, int pages, int publication, String editorial) throws ArgumentRequiredException {
        this.setAuthor(author);
        this.setTitle(title);
        this.setISBN(ISBN);
        this.setPages(pages);
        this.setPublication(publication);
        this.setEditorial(editorial);
    }

    public Book() {

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

    public void setAuthor(String author) throws ArgumentRequiredException {
        if ((author == null) || (author.isEmpty())) {
            throw new ArgumentRequiredException("author");
        }
        this.author = author;
    }

    public String getTitle() {
        return this.title;
    }

    public BigInteger getISBN() {
        return this.ISBN;
    }

    public void setISBN(BigInteger ISBN) throws ArgumentRequiredException {
        if (ISBN.toString().length() != 13 || ISBN.signum() < 0) {
            throw new ArgumentRequiredException("ISBN");
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

    public void setTitle(String title) throws ArgumentRequiredException {
        if (title == null || title == "") {
            throw new ArgumentRequiredException("title");
        }
        this.title = title;
    }

    @Override
    public String serialize() {
        return new GsonBuilder().create().toJson(this.getAttributesAndValues());
    }

    private Map<String, String> getAttributesAndValues() {
        UnaryOperator<String> capitalize = s -> (s.equals(s.toUpperCase())) ? s : s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (String field : this.getAttributes()) {
            try {
                map.put(capitalize.apply(field), this.getClass().getDeclaredField(field).get(this).toString());
            } catch (NoSuchFieldException e) {
                continue;
            } catch (IllegalAccessException e) {
                continue;
            }
        }
        return map;
    }

    public String[] getAttributes() {
        return Arrays.stream(this.getClass().getDeclaredFields()).map(f -> f.getName()).toArray(String[]::new);
    }

    @Override
    public void deserialize(String text) {
        Gson gson = new Gson();
        LinkedHashMap<String, String> map = gson.fromJson(text, LinkedHashMap.class);
        try {
            this.setISBN(new BigInteger(map.get("ISBN")));
            this.setAuthor(map.get("Author"));
            this.setEditorial(map.get("Editorial"));
            this.setPages(gson.fromJson(map.get("Pages"), int.class));
            this.setTitle(map.get("Title"));
            this.setPublication(gson.fromJson(map.get("Publication"), int.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String serializeToText() {
        ArrayList<String> columns = new ArrayList<String>();
        for (Entry<String, String> entry : this.getAttributesAndValues().entrySet()) {
            columns.add(entry.getKey() + " : " + entry.getValue());
        }
        return String.join(", ", columns);
    }

    public String serializeToXML() {
        XMLNode xml = new XMLNode("xml");
        for (Entry<String, String> entry : this.getAttributesAndValues().entrySet()) {
            xml.appendChild(new XMLNode(entry.getKey(), entry.getValue()));
        }
        return xml.toString();
    }
}
