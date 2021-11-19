package com.example.longmapcomp.longmap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongMapImpl<V> implements LongMap<V> {
    private static final float LOAD_FACTOR = 0.75f;
    private static final int CAPACITY = 16;

    private int numberItem;
    private Bucket<V>[] buckets;
    private final Class<V> vClass;

    public LongMapImpl(Class<V> vClass) {
        this(CAPACITY, vClass);
    }

    public LongMapImpl(int bucketSize, Class<V> vClass) {
        this.vClass = vClass;
        buckets = createNewBucketArray(bucketSize);
    }

    private float currentCapacity() {
        return (float) numberItem / buckets.length;
    }

    public V put(long key, V value) {
        if (currentCapacity() >= LOAD_FACTOR) {
            rehash();
        }
        Bucket<V> bucket = getBucket(key, buckets);
        if (!bucket.isBucketHasKey(key)) {
            KeyAndValue<V> keyValueNode = new KeyAndValue<>(key, value);
            bucket.addItem(keyValueNode);
            numberItem++;
        } else {
            KeyAndValue<V> item = bucket.getItem(key);
            item.setValue(value);
        }
        return value;
    }

    private void rehash() {
        int newCapacity = buckets.length * 2;
        Bucket<V>[] newBuckets = createNewBucketArray(newCapacity);
        for (Bucket<V> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            bucket.getAll().forEach(item -> moveItemToNewBucket(newBuckets, item));
        }
        buckets = newBuckets;
    }

    @SuppressWarnings("unchecked")
    private Bucket<V>[] createNewBucketArray(int size) {
        return (Bucket<V>[]) new Bucket[size];
    }

    private void moveItemToNewBucket(Bucket<V>[] newBucketsArray, KeyAndValue<V> pair) {
        Bucket<V> bucket = getBucket(pair.getKey(), newBucketsArray);
        bucket.addItem(pair);
    }

    private Bucket<V> getBucket(long key, Bucket<V>[] bucketsArray) {
        Bucket<V> bucket = bucketsArray[indexFor(key, bucketsArray.length)];
        if (bucket == null) {
            bucket = new Bucket<>();
            bucketsArray[indexFor(key, bucketsArray.length)] = bucket;
        }
        return bucket;
    }

    private int indexFor(long key, int bucketsLength) {
        return (int) Math.abs(key) % bucketsLength;
    }

    public V get(long key) {
        Bucket<V> bucket = getBucket(key, buckets);
        KeyAndValue<V> keyValue = bucket.getItem(key);
        if (keyValue == null) {
            return null;
        }
        return keyValue.getValue();
    }

    public V remove(long key) {
        Bucket<V> bucket = getBucket(key, buckets);
        KeyAndValue<V> item = bucket.removeItem(key);
        if (item != null) {
            numberItem--;
            return item.getValue();
        }
        return null;
    }

    public boolean isEmpty() {
        return numberItem == 0;
    }

    public boolean containsKey(long key) {
        Bucket<V> bucket = getBucket(key, buckets);
        return bucket.isBucketHasKey(key);
    }

    public boolean containsValue(V value) {
        for (Bucket<V> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            return bucket.isBucketHasValue(value);
        }
        return false;
    }

    public long[] keys() {
        long[] allKeys = new long[numberItem];
        int addedKeys = 0;
        for (Bucket<V> bucket : buckets) {
            if (bucket == null) {
                continue;
            }
            long[] bucketKeys = bucket.getAllKeys();
            for (int j = addedKeys, k = 0; k < bucketKeys.length; j++, k++) {
                allKeys[j] = bucketKeys[k];
            }
            addedKeys += bucketKeys.length;
        }
        return allKeys;
    }

    @SuppressWarnings("unchecked")
    public V[] values() {
        List<V> all = new ArrayList<>();
        for (Bucket<V> bucket : buckets) {
            if (bucket == null) continue;
            all.addAll(bucket.getAllValue());
        }
        V[] result = (V[]) Array.newInstance(vClass, all.size());
        all.toArray(result);

        return result;
    }

    public long size() {
        return numberItem;
    }

    public void clear() {
        if (0 == size())
            return;
        Arrays.fill(buckets, null);
        numberItem = 0;
    }
}
