package com.piedaholic.algo.cache;

import java.util.*;

public interface LruCache<K, V> {
  V get(K key);

  void put(K key, V value);

  void remove(K key);

  // A data structure which is sorted on a condition always
  // 0 index is going to return least/most
}

abstract class LruCacheImpl<K, V> implements LruCache<K, V> {
  private final Map<K, V> cache;

  public void print() {
    this.cache.forEach((k, v) -> System.out.println("[ k,v ] -> " + k + " " + v));
  }

  public LruCacheImpl(int capacity) {
    this.cache = new LinkedHashMap<>(capacity) {};
  }

  public LruCacheImpl(Map<K, V> cache) {
    this.cache = cache;
  }

  @Override
  public void remove(K key) {
    this.cache.remove(key);
  }

  public boolean contains(K key) {
    return this.cache.containsKey(key);
  }

  public V get(K key) {
    return this.cache.get(key);
  }

  public void put(K key, V value) {
    this.cache.put(key, value);
  }

  public int size() {
    return cache.size();
  }

  public Set<K> getKeySet() {
    return cache.keySet();
  }
}

class LruCacheSimple<K, V> extends LruCacheImpl<K, V> {
  int capacity;

  public LruCacheSimple(int capacity) {
    super(capacity);
    this.capacity = capacity;
  }

  @Override
  public V get(K key) {
    if (this.contains(key)) {
      V value = super.get(key);
      this.remove(key);
      super.put(key, value);
      return value;
    }
    return null;
  }

  public void put(K key, V value) {
    if (this.contains(key)) {
      this.remove(key);
    } else {
      evict();
    }
    super.put(key, value);
  }

  public void evict() {
    if (this.size() == this.capacity) {
      Optional<K> result = this.getKeySet().stream().findFirst();
      result.ifPresent(this::remove);
    }
  }
}

class LruCacheDefaultImpl<K, V> extends LruCacheImpl<K, V> {

  public LruCacheDefaultImpl(int capacity) {
    super(
        new LinkedHashMap<>(capacity, 0.75f, true) {
          protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > capacity;
          }
        });
  }
}
