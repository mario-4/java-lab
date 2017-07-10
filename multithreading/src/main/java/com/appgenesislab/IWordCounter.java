package com.appgenesislab;

/**
 * Created by mariopaulo on 2017-07-10.
 */
public interface IWordCounter {

    void take(String word);

    int count(String word);
}
