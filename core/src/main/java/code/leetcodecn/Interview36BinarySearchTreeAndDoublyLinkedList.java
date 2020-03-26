package code.leetcodecn;

public class Interview36BinarySearchTreeAndDoublyLinkedList {
    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    private Node treeToDoublyList(Node root, Node next) {
        if (root == null) return next;
        Node left = this.treeToDoublyList(root.left, root);
        root.right = treeToDoublyList(root.right, next == null ? left : next);
        root.right.left = root;
        return left;
    }

    public Node treeToDoublyList(Node root) {
        return this.treeToDoublyList(root, null);
    }
}
