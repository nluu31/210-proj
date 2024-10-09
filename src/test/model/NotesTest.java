package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;


public class NotesTest {
    private Notes testNotes;
    private List<String> testQList;
    private List<String> testAList;
    private List<String> testEmptyList;

    @BeforeEach
    void runBefore() {
        testNotes = new Notes("CPSC210");
        testQList = new ArrayList<String>();
        testAList = new ArrayList<String>();
        testQList.add("What is an integer?");
        testAList.add("A whole number");
        testQList.add("What is a string?");
        testAList.add("A combination of characters");
        testEmptyList = new ArrayList<String>();
    }

    @Test
    void testConstructor() {
        assertEquals("CPSC210", testNotes.getCourse());
        assertEquals(0, testNotes.getNumQuestions());
    }

    @Test
    void testAddOneQuestionAnswer() {
        assertEquals(0, testNotes.getNumQuestions());
        testNotes.addQA("What is an integer?", "A whole number");
        assertEquals(1, testNotes.getNumQuestions());
        assertEquals("What is an integer?", testNotes.getQuestion(0));
        assertEquals("A whole number", testNotes.getAnswer(0));
    }

    @Test
    void testAddMultipleQuestionAnswer() {
        assertEquals(0, testNotes.getNumQuestions());
        testNotes.addQA("What is an integer?", "A whole number");
        testNotes.addQA("What is a string?", "A combination of characters");
        assertEquals(2, testNotes.getNumQuestions());
        assertEquals("What is an integer?", testNotes.getQuestion(0));
        assertEquals(testQList, testNotes.getAllQuestions());
    }


    @Test
    void testSize() {
        assertEquals(0, testEmptyList.size());
        assertEquals(2, testQList.size());
        testQList.add("Another Question");
        assertEquals(3, testQList.size());
        assertEquals("Another Question", testQList.get(2));
    }


    @Test
    void testRandom() {
        testNotes.addQA("What is an integer?", "A whole number");
        testNotes.addQA("What is a string?", "A combination of characters");
        int randomIndex = testNotes.getRandom();
        assertTrue(randomIndex >= 0 && randomIndex < testNotes.getNumQuestions(),
                   "Random index should be within the valid range");
    }
    @Test 
    void testGetRandomQ() {
        testNotes.addQA("What is an integer?", "A whole number");
        testNotes.addQA("What is a string?", "A combination of characters");
        String randomQuestion = testNotes.getRandomQuestion();
        assertTrue(testNotes.getAllQuestions().contains(randomQuestion));
    }
}

