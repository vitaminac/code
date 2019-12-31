package code.adt.tree;


import code.adt.Enumerable;
import code.adt.LinkedList;
import code.adt.Position;
import code.adt.Stack;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Iterator;
import java.util.function.Consumer;


public class VirtualFileSystem<E> implements NAryTree<E> {
    public static class LCRSTree<E> implements Position<E>, Serializable {
        private String filename;
        private E element;
        private LCRSTree<E> parent;
        private LCRSTree<E> leftChild;
        private LCRSTree<E> rightSibling;

        private LCRSTree(String filename, E element) {
            this.filename = filename;
            this.element = element;
        }

        public E getElement() {
            return element;
        }

        private int size() {
            int size = 1;
            LCRSTree<E> node = this.leftChild;
            while (node != null) {
                size += node.size();
                node = node.rightSibling;
            }
            return size;
        }

        private Enumerable<LCRSTree<E>> children() {
            return consumer -> {
                LCRSTree<E> node = LCRSTree.this.leftChild;
                while (node != null) {
                    consumer.accept(node);
                    node = node.rightSibling;
                }
            };
        }

        private LCRSTree<E> addChild(String name, E element) {
            final LCRSTree<E> tree = new LCRSTree<>(name, element);
            if (this.leftChild == null) {
                this.leftChild = tree;
            } else {
                LCRSTree<E> prev = this.leftChild;
                while (prev.rightSibling != null) {
                    prev = prev.rightSibling;
                }
                prev.rightSibling = tree;
            }
            tree.parent = this;
            return tree;
        }

        private static <E> String stringify(LCRSTree<E> tree, int level, String pathname) {
            if (tree.filename.startsWith(".")) {
                return "";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(pathname).append(tree.filename);
                String path = sb.toString() + "/";
                tree.children().enumerate(node -> {
                    String subDirectory = stringify(node, level + 1, path);
                    if (!"".equals(subDirectory)) {
                        sb.append('\n');
                        sb.append(subDirectory);
                    }
                });
                return sb.toString();
            }
        }

        @Override
        public String toString() {
            return stringify(this, 0, "");
        }
    }

    private LCRSTree<E> vfs;

    private static void load(LCRSTree<byte[]> tree, File root) throws IOException {
        if (root.isDirectory()) {
            final File[] files = root.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isHidden()) {
                        if (file.isDirectory()) {
                            load(tree.addChild(file.getName(), null), file);
                        } else {
                            tree.addChild(file.getName(), IOUtils.toByteArray(new FileInputStream(file)));
                        }
                    }
                }
            }
        }
    }

    public static VirtualFileSystem<byte[]> loadFromFileSystem(File root) throws IOException {
        if (root == null || !root.exists() || !root.isDirectory())
            throw new RuntimeException("The directory does not exist");
        final VirtualFileSystem<byte[]> vfs = new VirtualFileSystem<>();
        vfs.vfs = new LCRSTree<>("", null);
        load(vfs.vfs, root);
        return vfs;
    }

    private static <E> void writeToFileSystem(LCRSTree<E> tree, File root) throws IOException {
        final File file = root.toPath().resolve(tree.filename).toFile();
        final Iterator<LCRSTree<E>> it = tree.children().iterator();
        if (it.hasNext()) {
            file.mkdir();
            do {
                LCRSTree<E> child = it.next();
                writeToFileSystem(child, file);
            } while (it.hasNext());
        } else {
            if (tree.getElement().getClass().equals(byte[].class)) {
                try (OutputStream out = new FileOutputStream(file)) {
                    out.write((byte[]) tree.getElement());
                }
            } else {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                    out.writeObject(tree.getElement());
                }
            }
        }
    }

    public void zip(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this.vfs);
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> VirtualFileSystem<E> unzip(File zip) throws IOException, ClassNotFoundException {
        try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(zip))) {
            final VirtualFileSystem<E> vfs = new VirtualFileSystem<>();
            vfs.vfs = (LCRSTree<E>) oin.readObject();
            return vfs;
        }
    }

    public void writeToFileSystem(File root) throws IOException {
        writeToFileSystem(this.vfs, root);
    }

    @Override
    public String toString() {
        return this.vfs.toString();
    }

    @Override
    public LCRSTree<E> get(String path) {
        LCRSTree<E> current = this.vfs;
        final String[] paths = path.split("/");
        for (int i = 0; i < paths.length; i++) {
            while (current != null && !current.filename.equals(paths[i])) {
                current = current.rightSibling;
            }
            if (current == null) break;
            else if (i < paths.length - 1) current = current.leftChild;
        }
        return current;
    }

    private LCRSTree<E> check(Position<E> position) {
        return (LCRSTree<E>) position;
    }

    @Override
    public E remove(Position<E> position) {
        LCRSTree<E> prev = null;
        LCRSTree<E> current = this.check(position).parent.leftChild;

        while (current != null && current != position) {
            prev = current;
            current = current.rightSibling;
        }

        if (prev == null) {
            this.check(position).parent.leftChild = this.check(position).rightSibling;
        } else if (current != null) {
            prev.rightSibling = current.rightSibling;
        }
        return current.getElement();
    }

    @Override
    public Enumerable<Position<E>> children(Position<E> position) {
        return consumer -> VirtualFileSystem.this.check(position).children().enumerate(consumer::accept);
    }

    @Override
    public LCRSTree<E> add(Position<E> position, String name, E element) {
        if (position != null) {
            if (position.getElement() != null) throw new IllegalArgumentException("path specify a file");
            final Iterator<LCRSTree<E>> it = this.check(position).children().iterator();
            while (it.hasNext()) {
                if (this.check(position).filename.equals(it.next().filename))
                    throw new IllegalArgumentException("file already existed!");
            }
            return this.check(position).addChild(name, element);
        } else throw new IllegalArgumentException();
    }

    @Override
    public int size() {
        return this.root().size();
    }

    @Override
    public boolean isEmpty() {
        return this.root().leftChild == null;
    }

    @Override
    public LCRSTree<E> root() {
        return this.vfs;
    }

    @Override
    public void root(E element) {
        if (root().leftChild != null) throw new RuntimeException("Root is a directory");
        this.root().element = element;
    }

    @Override
    public LCRSTree<E> parent(Position<E> position) {
        return this.check(position).parent;
    }

    @Override
    public E replace(Position<E> position, E element) {
        E retVal = position.getElement();
        this.check(position).element = element;
        return retVal;
    }

    @Override
    public boolean isInternal(Position<E> position) {
        return !this.isLeaf(position);
    }

    @Override
    public boolean isLeaf(Position<E> position) {
        return this.check(position).leftChild == null;
    }

    public String getPath(LCRSTree<E> position) {
        Stack<LCRSTree<E>> stack = new LinkedList<>();
        while (position != null) {
            stack.push(position);
            position = position.parent;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop().filename);
            if (!stack.isEmpty()) {
                sb.append('/');
            }
        }
        return sb.toString();
    }

    @Override
    public void enumerate(Consumer<Position<E>> consumer) {
        new DFSTraversal<>(this).enumerate(consumer);
    }
}
