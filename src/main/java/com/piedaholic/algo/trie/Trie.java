package com.piedaholic.algo.trie;

public class Trie {
  Trie[] references;

  Trie() {
    this.references = new Trie[26];
  }
}

class TrieContainer {
  Trie root;

  void insert(String s) {
    Trie current = root;
    int idx = 0;
    char c = s.charAt(idx);
    while (idx < s.length()) {
      if (current == null) {
        current = new Trie();
        if (root == null) {
          root = current;
        }
      }

      if (current.references[c - 97] == null) {
        current.references[c - 97] = new Trie();
      }

      current = current.references[c - 97];
      idx++;
    }
  }

  boolean find(String s) {
    Trie current = root;
    int idx = 0;
    char c = s.charAt(idx);
    while (idx < s.length()) {
      if (current == null || current.references[c - 97] == null) {
        return false;
      }
      current = current.references[c - 97];
      idx++;
    }
    return true;
  }
}
