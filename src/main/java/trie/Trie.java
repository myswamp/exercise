package trie;

import java.util.TreeMap;

public class Trie {

    private final Node root = new Node('*');


    public void insert(String word) {
        Node cur = root;

        for (char c : word.toCharArray()) {
            cur.children.putIfAbsent(c, new Node(c));
            cur = cur.children.get(c);
        }

        cur.word = word;
    }

    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.length() == 0) {
            return false;
        }
        Node cur = root;

        for (char c : prefix.toCharArray()) {
            if (cur.children.containsKey(c)) {
                cur = cur.children.get(c);
            } else {
                return false;
            }
        }

        return true;
    }


    static class Node {
        char c;
        TreeMap<Character, Node> children = new TreeMap<>();
        String word;

        Node(char c) {
            this.c = c;
        }
    }
}



