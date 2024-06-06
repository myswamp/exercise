package cache;

import java.util.HashMap;
import java.util.Map;

public class LRU<K, V> implements Cache<K, V> {
    static class Node<K, V> {
        Node<K, V> pre, next;
        K key;
        V val;

        Node(K key, V val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public String toString() {
            return val != null ? val.toString() : "null";
        }
    }

    private Node<K, V> head = new Node<>(null, null), tail = new Node<>(null, null);
    private int capacity;
    private Map<K, Node<K, V>> map = new HashMap<>();

    public LRU(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.pre = head;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<K, V> cur = head.next;

        while (cur != tail) {
            sb.append(cur.val);
            sb.append("->");
            cur = cur.next;
        }

        return sb.toString().substring(0, sb.length() - 2);
    }

    private void removeNode(Node<K, V> node) {
        Node<K, V> pre = node.pre;
        Node<K, V> next = node.next;
        pre.next = next;
        next.pre = pre;
    }

    private void addToFront(Node<K, V> node) {
        Node<K, V> next = head.next;
        next.pre = node;
        head.next = node;
        node.pre = head;
        node.next = next;
    }

    private void removeLast() {
        map.remove(tail.pre.key);
        removeNode(tail.pre);
    }

    @Override
    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.val = value;
            removeNode(node);
            addToFront(node);
        } else {
            Node<K, V> node = new Node<>(key, value);
            map.put(key, node);
            addToFront(node);
            if (map.size() > capacity) {
                removeLast();
            }
        }
    }

    @Override
    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }

        Node<K, V> node = map.get(key);
        removeNode(node);
        addToFront(node);
        return node.val;
    }

    @Override
    public void evict(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void evictAll() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        LRU<Integer, String> lru = new LRU<>(3);
        lru.put(1, "one");
        lru.put(2, "two");
        lru.put(3, "three");
        System.out.println(lru);
        lru.put(4, "four");
        System.out.println(lru);
        lru.get(2);
        System.out.println(lru);
        lru.get(4);
        System.out.println(lru);
    }
}
