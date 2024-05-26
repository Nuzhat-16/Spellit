import java.io.*;
import java.util.*;

public class SpellCheckerApp {
    public static final String reset = "\u001B[0m";
    public static final String red = "\u001B[41m";

    public static void main(String[] args) {
        Set<String> dictionary = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\english3.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.add(line.toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
            return;
        }

        SpellChecker spellChecker = new SpellChecker(dictionary);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter the path to a text file (or 'quit' to exit): ");
            String inputPath = sc.nextLine();

            if (inputPath.equalsIgnoreCase("quit")) {
                break;
            }

            List<String> misspelledWords = new ArrayList<>();
            int totalWords = 0;
            int misspelledCount = 0;

            try (BufferedReader fileReader = new BufferedReader(new FileReader(inputPath))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        // Remove punctuation from the word
                        String cleanedWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

                        if (cleanedWord.isEmpty()) {
                            continue;  // Skip empty words
                        }

                        totalWords++;

                        if (!dictionary.contains(cleanedWord)) {
                            misspelledWords.add(word);
                            misspelledCount++;
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading the input file: " + e.getMessage());
                continue;
            }

            if (misspelledWords.isEmpty()) {
                System.out.println("No misspelled words found in the file.");
            } else {
                System.out.println("Misspelled words in the file:");
                for (String misspelledWord : misspelledWords) {
                    System.out.println("Word: " + red + misspelledWord + reset);
                    List<String> corrections = spellChecker.suggestCorrections(misspelledWord);
                    System.out.println("Suggestions: " + corrections);
                    System.out.println();
                }
            }

            System.out.println("Total words in the file: " + totalWords);
            System.out.println("Total misspelled words: " + misspelledCount);
        }

        System.out.println("Exiting SpellChecker.");
    }
}
