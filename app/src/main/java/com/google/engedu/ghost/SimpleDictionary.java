package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    Random random = new Random();
    PrefixMatch prefixMatch = new PrefixMatch();

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
                words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }


    @Override
    public String getAnyWordStartingWith(final String prefix) {
        if (prefix.length() > 1) {
            Collections.sort(words);

            return prefixMatch.findMatches(words, prefix);

        }
        String s = words.get(random.nextInt(words.size()));
        return s.substring(0, 4);
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        return "";
    }
}
