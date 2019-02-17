package code.adt.user;


import code.adt.Position;
import code.adt.tree.narytree.LCRSTree;
import code.adt.tree.narytree.NAryTree;

import java.io.File;
import java.util.Iterator;
import java.util.Stack;

public class VirtualFileSystem {
    private LCRSTree<String> fileSystem = new LCRSTree<>();

    private static void load(File file, Position<String> node, NAryTree<String> tree) {
        if (file.isDirectory()) {
            final File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    load(subFile, tree.add(file.getName(), node), tree);
                }
            }
        }
    }

    public void loadFileSystem(String path) {
        File root = new File(path);
        load(root, fileSystem.setRoot(path), this.fileSystem);
    }

    public String getFileSystem() {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        int level = 0;
        Stack<Iterator<? extends Position<String>>> parents = new Stack<>();

        if (this.fileSystem != null && !this.fileSystem.isEmpty()) {
            parents.add(this.fileSystem.children(this.fileSystem.root()).iterator());
        }

        while (!parents.empty()) {
            sb.append(counter++);
            sb.append(' ');
            for (int i = 0; i < level; i++) {
                sb.append('\t');
            }
            final Iterator<? extends Position<String>> it = parents.pop();
            level -= 1;
            if (it.hasNext()) {
                // parents.add(it.next());
            }
        }
        return null;
    }


    public void moveFileById(int idFile, int idTargetFolder) {
        throw new RuntimeException("Not yet implemented");
    }

    public void removeFileById(int idFile) {
        throw new RuntimeException("Not yet implemented");
    }


    public Iterable<String> findBySubstring(int idStartFile, String substring) {
        throw new RuntimeException("Not yet implemented");
    }

    public Iterable<String> findBySize(int idStartFile, long minSize, long maxSize) {
        throw new RuntimeException("Not yet implemented");
    }

    public String getFileVirtualPath(int idFile) {
        throw new RuntimeException("Not yet implemented");
    }

    public String getFilePath(int idFile) {
        throw new RuntimeException("Not yet implemented");
    }
}
