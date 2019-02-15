package oop.serializable;

import java.io.*;

public interface Serializable extends java.io.Serializable {
    default void writeTo(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(this.serialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String serialize();

    default void readFrom(String path) {
        StringBuilder bd = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.lines().forEach(bd::append);
            this.deserialize(bd.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void deserialize(String text);
}
