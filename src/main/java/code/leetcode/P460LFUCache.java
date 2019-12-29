package code.leetcode;

import java.util.HashMap;
import java.util.Map;

class LFUCache {
    private static class Node {
        private int freq = 0;
        private int key;
        private int value;
        private Node next;
        private Node prev;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private int min = 0;
    private final Map<Integer, Node> freq = new HashMap<>();
    private final Map<Integer, Node> map = new HashMap<>();
    private final int capacity;
    private int size = 0;


    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    private void remove(Node node) {
        if (node.prev == node) {
            this.freq.remove(node.freq);
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            Node head = this.freq.get(node.freq);
            if (node == head) {
                this.freq.put(node.freq, node.next);
            }
        }
    }

    private void put(Node node) {
        Node head = this.freq.get(node.freq);
        if (head == null) {
            node.next = node;
            node.prev = node;
            this.freq.put(node.freq, node);
        } else {
            node.next = head;
            node.prev = head.prev;
            head.prev.next = node;
            head.prev = node;
        }
    }

    private void touch(Node node) {
        this.remove(node);
        if (this.min == node.freq && !this.freq.containsKey(node.freq)) {
            this.min = node.freq + 1;
        }
        node.freq += 1;
        this.put(node);
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
            if (this.capacity > 0) {
                node = new Node(key, value);
                if (this.size == this.capacity) {
                    Node head = this.freq.get(this.min);
                    this.remove(head);
                    this.map.remove(head.key);
                    this.put(node);
                } else {
                    this.put(node);
                    this.size += 1;
                }
                this.map.put(key, node);
                this.min = 0;
            }
        } else {
            this.touch(node);
            node.value = value;
        }
    }
}
