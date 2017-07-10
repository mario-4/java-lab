package com.appgenesislab;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WordCounter implements IWordCounter {

    private Map<String, AtomicInteger> words = new ConcurrentHashMap<>(8,2,2);

    @Override
    public void take(String word) {
        Objects.requireNonNull(word, "Word cannot be null");
        words.putIfAbsent(word, new AtomicInteger(0));
        words.computeIfPresent(word, (s, atomicInteger) -> new AtomicInteger(atomicInteger.incrementAndGet()));
    }

    @Override
    public int count(String word) {
        return words.getOrDefault(word, new AtomicInteger(0)).intValue();
    }
}
