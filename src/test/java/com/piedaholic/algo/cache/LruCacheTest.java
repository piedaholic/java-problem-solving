package com.piedaholic.algo.cache;

import junit.framework.TestCase;

public class LruCacheTest extends TestCase {
    public void test_LruCacheSimple() {
        LruCache<Integer, String> lruCache = new LruCacheSimple<>(2);
        lruCache.put(1, "Klose");
        lruCache.get(1);
        lruCache.put(2, "Podolski");
        lruCache.get(1);
        lruCache.put(3, "Ballack");

        assert lruCache.get(2) == null;
    }

    public void test_LruCacheDefault() {
        LruCacheDefaultImpl<Integer, String> lruCache = new LruCacheDefaultImpl<>(2);
        lruCache.put(1, "Klose");
        lruCache.get(1);
        lruCache.put(2, "Podolski");
        lruCache.get(1);
        lruCache.put(3, "Ballack");

        assert lruCache.get(2) == null;
    }
}
