package com.newrelic;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.newrelic.assign.Phrases;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PhrasesTest.class})
public class PhrasesTest {

    private static final String FILE_NAME = "/Users/ahmed-explorer/Downloads/newRelic.txt";

    private static final List fileNames = new ArrayList<>();

    @Test
    public void testReadFileLines() throws IOException {
        Phrases service = new Phrases();

        List lines = service.readFileLines(FILE_NAME);
        assertNotEquals(0, lines,
                "testReadFileLines >>    Confirm reasding lines for file PASS");
    }

    @Test
    public void testReadWrods() throws IOException {

        Phrases service = new Phrases();
        List<String> lines = new ArrayList<>();
        lines.add("This is first line");
        lines.add("                   ");
        lines.add("This is second\nline");
        List words = service.readWords(lines);

        assertNotNull(words,
                "testReadWrods >>    Confirm reasding word for file PASS");
    }

    @Test
    public void testReadWordsSequance() throws IOException {

        Phrases service = new Phrases();
        List<String> words = new ArrayList<>();
        words.add("I");
        words.add("am");
        words.add("not");
        words.add("going");
        words.add("to");
        words.add("go");
        words.add("to");
        words.add("diner");

        List seq = service.readWordsSequance(words);
        assertEquals(seq.size(), 6,
                "testReadWordsSequance >>    Confirm phrases number PASS");
    }

    @Test
    public void testGetTopSequance() throws IOException {

        Phrases service = new Phrases();
        List<String> words = new ArrayList<>();
        words.add("I am not");
        words.add("am not going");
        words.add("not going to");
        words.add("going to go");
        words.add("to go to");
        words.add("go to diner");
        words.add("I am not");
        words.add("going to movie");
        words.add("going to go");
        words.add("to go to");
        words.add("go to diner");
        words.add("I am not");
        words.add("GOING to movie");

        service.setTop(3);
        List topSeq = service.getTopSequance(words);
        assertEquals(topSeq.size(), 3,
                "testGetTopSequance >>    Confirm top phrases number PASS");

    }

    @Test
    public void testGeneratePhrasesReport() throws IOException {

        try {
            Phrases service = new Phrases();
            List<String> words = new ArrayList<>();
            words.add("I am not");
            words.add("am not going");
            words.add("not going to");
            words.add("going to go");
            words.add("to go to");
            words.add("go to diner");
            words.add("I am not");
            words.add("going to movie");
            words.add("going to go");
            words.add("to go to");
            words.add("go to diner");
            words.add("I am not");
            words.add("GOING to movie");

            service.setTop(3);
            List topSeq = service.getTopSequance(words);
            service.generatePhrasesReport(topSeq);
        } catch (IOException e) {
            fail("Error generating Phrases report");
        }

    }

}
