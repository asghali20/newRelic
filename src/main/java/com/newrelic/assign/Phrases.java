package com.newrelic.assign;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Phrases {

    Logger logger = LoggerFactory.getLogger(Phrases.class);

    private static List lineslist = new ArrayList<String>();
    private static List wordslist = new ArrayList<>();
    private static List wordsSequancelist = new ArrayList<>();
    private static List topSequances = new ArrayList<>();
    private static Map<String, Integer> occuranceSequanceMap = new HashMap<>();
    private Integer top = 10;
    private static final List fileNames = new ArrayList<>();
    private static final String FILE_NAME = "/Users/Downloads/newRelic.txt";

    /**
     * Entry point to process phrases
     *
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException {
        for (String s : args) {
            if (s != null || !s.isEmpty()) {
                System.out.println(s);
                new Phrases(s);
            }
        }
        new Phrases(FILE_NAME);
    }

    public Phrases() throws IOException {

    }

    public Phrases(String name) throws IOException {

        lineslist = readFileLines(name);
        wordslist = readWords(lineslist);
        wordsSequancelist = readWordsSequance(wordslist);
        topSequances = getTopSequance(wordsSequancelist);
        generatePhrasesReport(topSequances);
        clean();
    }

    private void clean() {

        lineslist.clear();
        wordslist.clear();
        wordsSequancelist.clear();
        occuranceSequanceMap = new HashMap<>();

    }

    /**
     * Read all file contents into array of lines
     *
     * @throws java.io.IOException
     */
    public List readFileLines(String fileName) throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while (br.ready()) {
                String line = br.readLine();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        }

        return lines;
    }

    /**
     * Split words in every line into array of all words
     *
     * @throws java.io.IOException
     */
    public List readWords(List<String> lines) throws IOException {

        String[] wordsArr = null;
        List<String> words = new ArrayList<String>();
        for (String line : lines) {
            wordsArr = line.split(" ");
            for (String word : wordsArr) {
                word = word.replace("\n", " ");
                if (!word.isEmpty()) {
                    words.add(word.toLowerCase());

                }

            }
        }
        return words;
    }

    /**
     * Shift the reader for every three words
     *
     * @throws java.io.IOException
     */
    public List readWordsSequance(List words) throws IOException {

        List<String> sequancelist = new ArrayList<String>();
        for (int i = 0; i < words.size(); i++) {
            if (((i + 2) < words.size())) {
                sequancelist.add(words.get(i) + " " + words.get(i + 1) + " " + words.get(i + 2));
            }
        }
        //logger.info("###### sequancelist: " + sequancelist);
        return sequancelist;
    }

    /**
     * Count the top phrases, top variable can be adjusted
     *
     */
    public List<String> getTopSequance(List<String> words) {
        if (words == null || words.isEmpty()) {
            return new ArrayList<>();
        }

        for (String s : words) {
            occuranceSequanceMap.put(s, occuranceSequanceMap.getOrDefault(s, 0) + 1);
        }

        List<String> topSequances = new ArrayList(occuranceSequanceMap.keySet());
        try {
            Collections.sort(topSequances, (w1, w2) -> !Objects.equals(occuranceSequanceMap.get(w1), occuranceSequanceMap.get(w2))
                    ? occuranceSequanceMap.get(w2) - occuranceSequanceMap.get(w1) : w1.compareTo(w2));

            System.out.println(topSequances.subList(0, top));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topSequances.subList(0, top);
    }

    /**
     * Generate a report of the top 100 3 words sequance.
     *
     */
    public void generatePhrasesReport(List<String> topSequances) {

        for (String seq : topSequances) {
            logger.info(occuranceSequanceMap.get(seq) + "-" + seq);
        }

    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

}
