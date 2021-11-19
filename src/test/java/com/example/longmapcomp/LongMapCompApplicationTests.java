package com.example.longmapcomp;

import com.example.longmapcomp.longmap.LongMap;
import com.example.longmapcomp.longmap.LongMapImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class LongMapCompApplicationTests {
    private LongMap<String> longMap = null;

    @BeforeEach
    void setUp() {
        longMap = new LongMapImpl<>(String.class);
    }

    @Test
    public void testAddItemWithNegativeKey() {
        String expectedValue = "TestValue";
        long key = -15L;
        String value = longMap.put(key, expectedValue);
        Assert.isTrue(1 == longMap.size(), "map size must be 1");
        Assert.isTrue(value.equals(expectedValue), "expectedValue and value must be equals");
    }

    @Test
    public void testAddItem() {
        long key = 1L;
        String expectedValue = "TestValue";
        String value = longMap.put(key, expectedValue);
        Assert.isTrue(1 == longMap.size(), "map size must be 1");
        Assert.isTrue(value.equals(expectedValue), "expectedValue and value must be equals");
    }

    @Test
    public void testAddTwoEqualsKeyAndValue() {
        long key = 1L;
        String expectedValue = "TestValue";
        String value1 = longMap.put(key, expectedValue);
        String value2 = longMap.put(key, expectedValue);
        Assert.isTrue(1 == longMap.size(), "map size must be 1");
        Assert.isTrue(value1.equals(expectedValue), "values must be equals");
        Assert.isTrue(value2.equals(expectedValue), "values must be equals");
    }

    @Test
    public void testAddTwoDifferentElementAndEqualsKey() {
        long key = 1L;
        String expectedValue1 = "TestValue";
        String expectedValue2 = "TestValuee";
        longMap.put(key, expectedValue1);
        longMap.put(key, expectedValue2);

        Assert.isTrue(1 == longMap.size(), "map size must be 1");
        Assert.isTrue(longMap.get(key).equals(expectedValue2), "expectedValue2 must be equals");
    }

    @Test
    public void testAddItemWithZeroKey() {
        String expectedValue = "TestValue";
        long keyZero = 0L;
        longMap.put(keyZero, expectedValue);

        Assert.isTrue(1 == longMap.size(), "map size must be 1");
        Assert.isTrue(longMap.get(keyZero).equals(expectedValue), "Expected one element in a longMap");
    }

    @Test
    public void testAddTwoItemsWithDifferentKeysAndEqualsValue() {
        String expectedValue = "TestValue";
        long key1 = 1L;
        long key2 = 2L;
        String value1 = longMap.put(key1, expectedValue);
        String value2 = longMap.put(key2, expectedValue);

        Assert.isTrue(2 == longMap.size(), "map size must be 2");
        Assert.isTrue(value1.equals(expectedValue), "value must be equals");
        Assert.isTrue(value2.equals(expectedValue), "value must be equals");
    }

    @Test
    public void testGetKey() {
        long key = 1L;
        String expectedValue = "TestValue";
        longMap.put(key, expectedValue);
        String value = longMap.get(key);

        Assert.isTrue(expectedValue.equals(value), "value must be equals");
    }

    @Test
    public void testRemoveKey() {
        String expectedValue = "TestValue";
        long key = 1L;
        longMap.put(key, expectedValue);
        longMap.remove(key);

        Assert.isTrue(0 == longMap.size(), "after remove longMap size must be '0'");
    }

    @Test
    public void testIsEmpty() {
        boolean isEmpty = longMap.isEmpty();

        Assert.isTrue(0 == longMap.size(), "longMap size == 0");
        Assert.isTrue(isEmpty, "longMap must be empty");
    }

    @Test
    public void testIsNotEmpty() {
        String expectedValue = "TestValue";
        long key = 1L;
        longMap.put(key, expectedValue);
        boolean isEmpty = longMap.isEmpty();
        Assert.isTrue(1 == longMap.size(), "longMap size == 1");
        Assert.isTrue(!isEmpty, "longMap must be not empty");
    }
    @Test
    public void testIsContainsValue() {
        String expectedValue = "TestValue";
        long key = 1L;
        longMap.put(key, expectedValue);
        boolean isContains = longMap.containsValue(expectedValue);

        Assert.isTrue(1 == longMap.size(), "longMap size must be 1");
        Assert.isTrue(isContains, "longMap contain value");
    }

}
