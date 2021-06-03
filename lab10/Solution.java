import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Solution {

    public static void main(String[] args) {
        String[] dictionary = new Dictionary("words.txt").pull();
        int num = dictionary.length, score = 0;
        for (int n = 0; n < 100; n++) { 
            Random r = new Random();
            String target = dictionary[r.nextInt(num)];
            String blackout = "";
            for (int i = 0; i < target.length(); i++) {
                blackout += "_";
            }
            Brain mybrain = new Brain(Arrays.copyOf(dictionary, dictionary.length), blackout);
            int lives = 8;
            boolean running = true;
            while (running) {
                char guess = mybrain.guessLetter();
                String original = mybrain.hiddenWord;
                char[] arrayform = original.toCharArray();
                for (int i = 0; i < target.length(); i++) {
                    if (target.charAt(i) == guess) {
                        arrayform[i] = guess;
                    }
                }
                String newform = "";
                for (int i = 0; i < target.length(); i++) {
                    newform = newform + arrayform[i];
                }
                mybrain.hiddenWord = newform;
                if (newform.equals(original)) {
                    lives = lives-1;
                }
                if (lives == 0) {
                    running = false;
                }
                if (mybrain.hiddenWord.equals(target)) {
                    running = false;
                    score = score +1;
                }
            }
            System.out.printf("Word: %s [%d]\n\n",mybrain.hiddenWord, (mybrain.hiddenWord.equals(target))?1:0);
        }
        System.err.println("\n\nScore: " + score);
    }
}


class Brain {

    String[] dictionary;
    String hiddenWord;
    // added variables
    char[] freqChar, used;
    ArrayList<String> filtered;
    int index, wordlen;
    char guess;
    
    Brain(String[] wordlist, String target) {
        dictionary = wordlist;
        hiddenWord = target;

        index = 0;
        wordlen = hiddenWord.length();
        used = new char[26];
        filtered = new ArrayList<>(20000);
        // STEP 1 [filtering words of the same length]
        for (String word : dictionary) {
            if (word.length() == wordlen) {
                filtered.add(word);
            }
        }
    }

    char guessLetter() {
        // starting with most comman letter 'e'
        if (index == 0) {
            guess = 'e';
            used[index++] = guess;
            return guess;
        }
        // STEP 2 [filtering similer words]
        ArrayList<String> subFiltered = new ArrayList<>(1000);
        for (int i = 0; i < filtered.size(); i++) {
            String word = filtered.get(i);
            boolean potentialWord = true;
            for (int j = 0; j < wordlen; j++) {
                if (hiddenWord.charAt(j) != '_') {
                    if (hiddenWord.charAt(j) != word.charAt(j)) {
                        potentialWord = false;
                        break;
                    }
                }
            }
            if (potentialWord) {
                subFiltered.add(word);
            }
        }
        this.filtered = subFiltered; 

        // ! STEP 3 counting frequencies
        int[] freq = new int[26];
        for (String word : filtered) {
            for (int i = 0; i < 26; i++) {
                boolean usedBefore = false;
                char c = (char)(i + 'a');
                for (int j = 0; j < used.length; j++) {
                    if (used[j] == 0) break;
                    if (used[j] == c) {
                        usedBefore = true;
                        break;
                    }
                }
                if (!usedBefore) {
                    if (word.indexOf(c) != -1) {
                        freq[i]++;
                    }
                }
            }
        }

        // STEP 4 [pulling out most frequently used character]
        int max = 0, maxIndex = 0;
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > max) {
                max = freq[i];
                maxIndex = i;
            }
        }

        char guess = (char)(maxIndex+'a');
        used[index++] = guess;
        return guess;
    }
}


/*

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class Solution {

    public static void main(String[] args) {
        String[] dictionary = new Dictionary("words.txt").pull();
        int num = dictionary.length, score = 0;
        for (int n = 0; n < 100; n++) { 
            Random r = new Random();
            String target = dictionary[r.nextInt(num)];
            String blackout = "";
            for (int i = 0; i < target.length(); i++) {
                blackout += "_";
            }
            Brain mybrain = new Brain(Arrays.copyOf(dictionary, dictionary.length), blackout);
            int lives = 8;
            boolean running = true;
            while (running) {
                char guess = mybrain.guessLetter();
                String original = mybrain.hiddenWord;
                char[] arrayform = original.toCharArray();
                for (int i = 0; i < target.length(); i++) {
                    if (target.charAt(i) == guess) {
                        arrayform[i] = guess;
                    }
                }
                String newform = "";
                for (int i = 0; i < target.length(); i++) {
                    newform = newform + arrayform[i];
                }
                mybrain.hiddenWord = newform;
                if (newform.equals(original)) {
                    lives = lives-1;
                }
                if (lives == 0) {
                    running = false;
                }
                if (mybrain.hiddenWord.equals(target)) {
                    running = false;
                    score = score +1;
                }
            }
            System.out.printf("Word: %s [%d]\n\n",mybrain.hiddenWord, (mybrain.hiddenWord.equals(target))?1:0);
        }
        System.err.println("\n\nScore: " + score);
    }
}


class Brain {

    String[] dictionary;
    String hiddenWord;
    // added variables
    char[] freqChar, used;
    char[] vowels, consonants;
    ArrayList<String> filtered;
    int index, wordlen, vowelsCount;
    char guess;
    
    Brain(String[] wordlist, String target) {
        dictionary = wordlist;
        hiddenWord = target;

        index = 0; vowelsCount = 0;
        wordlen = hiddenWord.length();
        vowels = new char[]{'a','e','i','o','u'};
        consonants = new char[21];
        for (int i = 0, j = 0; i < 26; i++) {
            char c = (char)(i+'a');
            if (!(c=='a'||c=='e'||c=='i'||c=='o'||c=='u')){
                consonants[j++] = c;
            } 
        }
        used = new char[26];
        filtered = new ArrayList<>(20000);
        // STEP 1 [filtering words of the same length]
        for (String word : dictionary) {
            if (word.length() == wordlen) {
                filtered.add(word);
            }
        }
    }

    char guessLetter() {
        // starting with most comman letters
        if (index == 0) {
            guess = 'e';
            used[index++] = guess;
            return guess;
        } else if (index == 1) {
            guess = 't';
            used[index++] = guess;
            return guess;
        } else if (index == 2) {
            guess = 'a';
            used[index++] = guess;
            return guess;
        }
        // STEP 2 [filtering similer words]
        ArrayList<String> subFiltered = new ArrayList<>(1000);
        for (int i = 0; i < filtered.size(); i++) {
            String word = filtered.get(i);
            boolean potentialWord = true;
            for (int j = 0; j < wordlen; j++) {
                if (hiddenWord.charAt(j) != '_') {
                    if (hiddenWord.charAt(j) != word.charAt(j)) {
                        potentialWord = false;
                        break;
                    }
                }
            }
            if (potentialWord) {
                subFiltered.add(word);
            }
        }
        this.filtered = subFiltered; 

        // ! STEP 3 counting frequencies
        HashMap<Character, Integer> freq = new HashMap<>();
        for (String word : filtered) {
            for (int i = 0; i < 26; i++) {
                char c = (char)(i + 'a');
                boolean usedBefore = false;
                for (int j = 0; j < used.length; j++) {
                    if (used[j] == 0) break;
                    if (used[j] == c) {
                        usedBefore = true;
                        break;
                    }
                    if (c=='a'||c=='e'||c=='i'||c=='o'||c=='u') {
                        vowelsCount++;
                    } 
                }
                if (vowelsCount >= 2) {
                    if (Math.random() > 0.1) {
                        c = consonants[new Random().nextInt(consonants.length)];
                    } else {
                        c = vowels[new Random().nextInt(vowels.length)];
                    }
                }
                if (!usedBefore) {
                    if (word.indexOf(c) != -1) {
                        // freq[i]++;
                        if (freq.containsKey(c)) {
                            freq.put(c, freq.get(c) + 1);
                        } else {
                            freq.put(c, 1);
                        }
                    }
                }
            }
        }

        // STEP 4 [pulling out most frequently used character]
        // int max = 0, maxIndex = 0;
        // for (int i = 0; i < freq.length; i++) {
        //     if (freq[i] > max) {
        //         max = freq[i];
        //         maxIndex = i;
        //     }
        // }
        // char guess = (char)(maxIndex+'a');
        // used[index++] = guess;


        int max = 0, maxIndex = 0;
        for (Map.Entry<Character, Integer> pair : freq.entrySet()) {
            // if (pair.value() > max) {
            //     max = pair.value();
            //     maxIndex = pair.key;
            // }
            if (pair.getValue() > max) {
                max = pair.getValue();
                maxIndex = pair.getKey();
            }
        }

        guess = (char)maxIndex;

        return guess;
    }
}

*/