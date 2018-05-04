package greedy.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree<E> {
    private final List<Tree<E>> children = new ArrayList<>();
    private final E e;

    public Tree(E e) {
        this.e = e;
    }

    public E getElement() {
        return e;
    }

    public List<Tree<E>> getChildren() {
        return this.children;
    }

    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    public List<E> preOrderTraversal() {
        List<E> elements = new ArrayList<>();
        elements.add(this.e);
        for (Tree<E> child : this.children) {
            elements.addAll(child.preOrderTraversal());
        }
        return elements;
    }

    public List<E> levelOrderTraversal() {
        List<E> elements = new ArrayList<>();
        Queue<Tree<E>> unvisitedNode = new LinkedList<>();
        unvisitedNode.add(this);
        while (!unvisitedNode.isEmpty()) {
            final Tree<E> node = unvisitedNode.remove();
            elements.add(node.e);
            unvisitedNode.addAll(node.children);
        }
        return elements;
    }

    @Override
    public String toString() {
        return "Tree{" + "children=" + children + ", e=" + e + '}';
    }
}
