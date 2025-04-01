package com.piedaholic.algo.trie;

import org.junit.Test;

public class TrieTest {
    @Test
    public void test() {
        TrieContainer container = new TrieContainer();
        container.insert("hello");
        container.insert("harsh");

        assert !container.find("yoy");
        assert container.find("hello");
    }
}
