package com.appgenesislab

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class WordCounterSpec extends Specification {


    def wordCounter = new WordCounter();

    def setup() {

    }

    def "should take a valid word"() {
        expect:
        wordCounter.take("ValidWord")
    }

    def "should take word #word #times times and return a count of #count"() {
        expect:
        times.times {
            wordCounter.take(word)
        }
        wordCounter.count(word) == count

        where:
        word    | times | count
        "hello" | 2     | 2
        "amor"  | 3     | 3
        "foco"  | 1     | 1
        "agua"  | 4     | 4

    }


    def "length of Spock's and his friends' names"() {
        expect:
        name.size() == length

        where:
        name     | length
        "Spock"  | 5
        "Kirk"   | 4
        "Scotty" | 6
    }

}
