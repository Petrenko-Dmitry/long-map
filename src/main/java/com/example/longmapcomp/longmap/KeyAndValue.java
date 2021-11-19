package com.example.longmapcomp.longmap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeyAndValue<V> {
    private long key;
    private V value;
}
