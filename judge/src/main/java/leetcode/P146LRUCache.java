package leetcode;

import java.util.HashMap;
import java.util.Map;

class LRUCache {
    private static class Node {
        private final int key;
        private int value;
        private Node next;
        private Node prev;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map = new HashMap<>();
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    private void touch(Node node) {
        if (node != this.head) {
            if (node == this.tail) {
                this.tail = node.prev;
            } else {
                node.next.prev = node.prev;
            }
            node.prev.next = node.next;
            this.head.prev = node;
            node.next = this.head;
            node.prev = null;
            this.head = node;
        }
    }

    public int get(int key) {
        Node node = this.map.get(key);
        if (node == null) {
            return -1;
        } else {
            this.touch(node);
            return node.value;
        }
    }

    public void put(int key, int value) {
        Node node = this.map.get(key);
        if (node == null) {
            if (this.map.size() == this.capacity) {
                this.map.remove(this.tail.key);
                this.tail = this.tail.prev;
                if (this.tail != null) this.tail.next = null;
                node = new Node(key, value);
                this.head.prev = node;
                node.next = this.head;
                this.head = node;
            } else {
                node = new Node(key, value);
                if (this.tail == null) {
                    this.tail = node;
                } else {
                    this.head.prev = node;
                }
                node.next = this.head;
                this.head = node;
            }
            this.map.put(key, node);

        } else {
            this.touch(node);
            node.value = value;
        }
    }
}
