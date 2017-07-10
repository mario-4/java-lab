package com.appgenesislab;


import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class WordCounterTest {

    private IWordCounter wordCounter;

    private ExecutorService executorService = Executors.newFixedThreadPool(16);

    @Before
    public void setUp() {

        wordCounter = new WordCounter();
    }

    @Test
    public void shouldTakeValidWord() {
        wordCounter.take("hello");
    }

    @Test
    public void shouldReturnZeroForUnexistentWord() {
        assertThat(wordCounter.count("hello")).isEqualTo(0);
    }

    @Test
    public void shouldTakeValidWordsAndStoreThem() {
        wordCounter.take("amor");
        wordCounter.take("hello");
        wordCounter.take("hello");

        assertThat(wordCounter.count("hello")).isEqualTo(2);
        assertThat(wordCounter.count("amor")).isEqualTo(1);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowIllegalArgumentExceptionForNullWord() {
        wordCounter.take(null);
    }

    @Test
    public void shouldSynchronizeInsertionsAndUpdates() throws InterruptedException {

        Stream.of("hello", "hi", "halo", "hola").parallel().forEach(s -> {
                    for (int i = 0; i < 16; i++) {
                        executorService.execute(() -> {
                            wordCounter.take(s);
                            wordCounter.take(s);
                            wordCounter.take("extra");
                            wordCounter.take(s);
                        });
                    }
                }
        );

        executorService.awaitTermination(3, TimeUnit.SECONDS);

        assertThat(wordCounter.count("hello")).isEqualTo(48);
        assertThat(wordCounter.count("hi")).isEqualTo(48);
        assertThat(wordCounter.count("halo")).isEqualTo(48);
        assertThat(wordCounter.count("hola")).isEqualTo(48);
        assertThat(wordCounter.count("extra")).isEqualTo(64);


    }


}
