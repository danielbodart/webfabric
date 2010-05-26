package org.webfabric.properties;

public interface Repository<K,V> {
    V get(K key);
    K set(K key, V value);
    void remove(K key);
}
