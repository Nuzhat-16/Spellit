import java.util.*;
import java.util.regex.Pattern;

public class SpellChecker {
    private Set<String> dictionary;

    public SpellChecker(Set<String> dictionary) {
        this.dictionary = dictionary;
    }


    public List<String> suggestCorrections(String word) {
        List<String> suggestions = new ArrayList<>();
        int minDistance = Integer.MAX_VALUE;

        for (String dictWord : dictionary) {
            int distance = calculateEditDistance(word, dictWord);

            if (distance < minDistance) {
                suggestions.clear();
                suggestions.add(dictWord);
                minDistance = distance;
            } else if (distance == minDistance) {
                suggestions.add(dictWord);
            }
        }

        return suggestions;
    }


    private int calculateEditDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i <= word1.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int insert = dp[i][j - 1] + 1;
                    int delete = dp[i - 1][j] + 1;
                    int replace = dp[i - 1][j - 1] + 1;
                    dp[i][j] = Math.min(Math.min(insert, delete), replace);
                }
            }
        }

        return dp[word1.length()][word2.length()];
    }


    public boolean isWordInDictionary(String word) {
        return dictionary.contains(word);
    }


    public static final String reset = "\u001B[0m";
    public static final String red = "\u001B[41m";


}
