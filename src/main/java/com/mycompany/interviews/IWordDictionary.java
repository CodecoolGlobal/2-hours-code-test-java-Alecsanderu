package com.mycompany.interviews;

public interface IWordDictionary {

    /**
     * loads the dictionary
     * @param callback The method to call when the dictionary is loaded
     */
    void load(Action callback);

    /**
     * Can be used to check whether a word is in the dictionary
     * @param word The word to look for.
     * @return true if the dictionary contains the word
     */
    boolean contains(String word);

    /**
     * @return number of words in the dictionary
     */
    int size();

    /**
     * Adds a callback when the dictionary is loaded
     * @param callback The method to call when the dictionary is loaded and populated
     */
    void addCallback(Action callback);

    /**
     * @return Whether the dictionary is fully loaded
     */
    boolean isLoaded();

}
