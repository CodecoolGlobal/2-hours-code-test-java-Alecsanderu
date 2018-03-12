package com.mycompany.interviews;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is a JUnit class which you can run to check if AnagramGame is working correctly.
 * Running it is not part of the code test and if you are not familiar with JUnit,
 * please make sure you complete the assignment before trying to set it up.
 */
class AnagramGameTest {

    private IWordDictionary dictionary;
    private IAnagramGame game;

	@BeforeEach
    void setUp() {
        dictionary = new WordDictionary();
        game = new AnagramGame("areallylongword", dictionary);
    }

	@Test
    void afterDictionaryIsLoaded_wordsAreEvaluatedCorrectly() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);

        dictionary.load(() -> {
            assertEquals(2, game.evaluateWord("no"));
            assertEquals(4, game.evaluateWord("grow"));
            assertEquals(0, game.evaluateWord("bold"));
            assertEquals(0, game.evaluateWord("glly"));
            assertEquals(6, game.evaluateWord("woolly"));
            assertEquals(0, game.evaluateWord("adder"));
            lock.countDown();
        });

        boolean wasCalled = lock.await(3, TimeUnit.SECONDS);
        assertTrue(wasCalled);
    }


	@Test
    void wordsSubmittedBeforeDictionaryIsLoaded_areNotDiscarded() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);

	    game.submitWord("no");
        game.submitWord("grow");
        game.submitWord("bold");
        game.submitWord("glly");
        game.submitWord("woolly");
        game.submitWord("adder");

        dictionary.load(() -> {
            assertHighscoreEntry(0, "woolly", 6);
            assertHighscoreEntry(1, "grow", 4);
            assertHighscoreEntry(2, "no", 2);
            lock.countDown();
        });
        boolean wasCalled = lock.await(3, TimeUnit.SECONDS);
        assertTrue(wasCalled);
    }

    private void assertHighscoreEntry (int index, String expectedWord, int expectedScore) {
        assertEquals(expectedWord, game.getWordAtPosition(index));
        assertEquals(expectedScore, game.getScoreAtPosition(index));
    }

}