package com.example.longmapcomp.longmap;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class Bucket<V> {
    private List<KeyAndValue<V>> node;

    public Bucket() {
        node = new LinkedList<>();
    }

    public List<KeyAndValue<V>> getAll() {
        return node;
    }

    public void addItem(KeyAndValue<V> item) {
        node.add(item);
    }

    public KeyAndValue<V> searchItemByValue(V value) {
        Optional<KeyAndValue<V>> item = node.stream()
                .filter(i -> i.getValue() != null
                        && value.equals(i.getValue()))
                .findFirst();
        return item.isPresent() ? item.get() : null;
    }

    public List<V> getAllValue() {
        return node.stream()
                .map(KeyAndValue::getValue)
                .collect(Collectors.toList());
    }

    public KeyAndValue<V> searchItemByKey(long key) {
        Optional<KeyAndValue<V>> item = node.stream()
                .filter(i -> i.getKey() == key)
                .findFirst();
        return item.isPresent() ? item.get() : null;
    }

    public long[] getAllKeys() {
        return node
                .stream()
                .mapToLong(KeyAndValue::getKey).toArray();
    }

    public boolean isBucketHasValue(V value) {
        return searchItemByValue(value) != null;
    }

    public boolean isBucketHasKey(long key) {
        return searchItemByKey(key) != null;
    }

    public KeyAndValue<V> getItem(long key) {
        return searchItemByKey(key);
    }

    public KeyAndValue<V> removeItem(long key) {
        KeyAndValue<V> itemToRemove = searchItemByKey(key);
        if (itemToRemove == null) {
            return null;
        }
        node.remove(itemToRemove);
        return itemToRemove;
    }

}
