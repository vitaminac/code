package code.adt.tree;


import code.adt.Enumerable;
import code.adt.LinkedList;
import code.adt.Stack;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class VirtualFileSystem<Key, E> implements NAryTree<E, VirtualFileSystem<Key, E>>, Serializable {
    private Key filename;
    private E element;
    private VirtualFileSystem<Key, E> parent;
    private Map<Key, VirtualFileSystem<Key, E>> map = new HashMap<>();

    public VirtualFileSystem(Key filename, E element) {
        this.filename = filename;
        this.element = element;
    }

    private static void load(VirtualFileSystem<String, byte[]> tree, File root) throws IOException {
        if (root.isDirectory()) {
            final File[] files = root.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isHidden()) {
                        if (file.isDirectory()) {
                            VirtualFileSystem<String, byte[]> subtree = new VirtualFileSystem<>(file.getName(), null);
                            load(subtree, file);
                            tree.addChild(subtree);
                        } else {
                            tree.addChild(new VirtualFileSystem<>(file.getName(), IOUtils.toByteArray(new FileInputStream(file))));
                        }
                    }
                }
            }
        }
    }

    public static VirtualFileSystem<String, byte[]> loadFromFileSystem(File root) throws IOException {
        if (root == null || !root.exists() || !root.isDirectory())
            throw new RuntimeException("The directory does not exist");
        final VirtualFileSystem<String, byte[]> vfs = new VirtualFileSystem<>("", null);
        load(vfs, root);
        return vfs;
    }

    private static void writeToFileSystem(VirtualFileSystem<String, byte[]> tree, File root) {
        final File file = root.toPath().resolve(tree.filename).toFile();
        tree.children().enumerate(child -> writeToFileSystem(child, file));
        try (OutputStream out = new FileOutputStream(file)) {
            out.write(tree.getElement());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static VirtualFileSystem<String, byte[]> find(VirtualFileSystem<String, byte[]> vfs, String path) {
        for (String s : path.split("/")) {
            if (s.equals("")) continue;
            else if (vfs == null) return null;
            else vfs = vfs.map.get(s);
        }
        return vfs;
    }

    public void remove() {
        if (this.parent != null) this.parent.map.remove(this.filename);
    }

    public void zip(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    @SuppressWarnings("unchecked")
    public static VirtualFileSystem<String, byte[]> unzip(File zip) throws IOException, ClassNotFoundException {
        try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(zip))) {
            final VirtualFileSystem<String, byte[]> vfs = (VirtualFileSystem<String, byte[]>) oin.readObject();
            return vfs;
        }
    }

    private void toString(String prefix, StringBuilder sb) {
        String next = prefix + this.filename;
        sb.append(next);
        sb.append('\n');
        this.children().forEach(child -> child.toString(next + "/", sb));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.toString("", sb);
        return sb.toString();
    }

    public Key getFilename() {
        return this.filename;
    }

    @Override
    public E getElement() {
        return this.element;
    }

    public VirtualFileSystem<Key, E> parent() {
        return this.parent;
    }

    @Override
    public void replace(E element) {
        this.element = element;
    }

    @Override
    public boolean isLeaf() {
        return this.map.isEmpty();
    }

    @Override
    public void addChild(VirtualFileSystem<Key, E> tree) {
        this.map.put(tree.filename, tree);
        tree.parent = this;
    }

    @Override
    public Enumerable<VirtualFileSystem<Key, E>> children() {
        return consumer -> {
            for (var tree : this.map.values()) consumer.accept(tree);
        };
    }
}
