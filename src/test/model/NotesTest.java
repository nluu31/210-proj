package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.EmptyListException;
import Exceptions.EmptyStringException;
import Exceptions.OutOfBoundsException;

import java.util.*;

public class NotesTest {
    private Notes testNotes;
    private Notes testNotesEmpty;
    private Notes testNotesOne;
    private List<String> testQList;
    private List<String> testAList;

    @BeforeEach
    void runBefore() throws EmptyStringException {
        testNotes = new Notes("CPSC210");
        testNotes.addQA("What is an integer?", "A whole number");
        testNotes.addQA("What is a string?", "A combination of characters");
        testQList = new ArrayList<String>();
        testAList = new ArrayList<String>();
        testQList.add("What is an integer?");
        testAList.add("A whole number");
        testQList.add("What is a string?");
        testAList.add("A combination of characters");
        testNotesEmpty = new Notes("Empty");
        testNotesOne = new Notes("Empty");
        testNotesOne.addQA("Q1", "A2");
    }

    @Test
    void testConstructor() {
        assertEquals("CPSC210", testNotes.getCourse());
        assertEquals(2, testNotes.getNumQuestions());

    }

    @Test
    void testGetter() throws OutOfBoundsException {
        assertEquals(testAList, testNotes.getAllAnswers());
        assertEquals("A whole number", testNotes.getAnswer(0));
        assertEquals("What is an integer?", testNotes.getQuestion(0));
        assertThrows(OutOfBoundsException.class, () -> testNotes.getQuestion(3)); 
        assertThrows(OutOfBoundsException.class, () -> testNotes.getAnswer(3));
        assertThrows(OutOfBoundsException.class, () -> testNotes.getQuestion(-1));
        assertThrows(OutOfBoundsException.class, () -> testNotes.getAnswer(-2));  
    }

    @Test
    void testAddOneQuestionAnswer() throws OutOfBoundsException, EmptyStringException{
        assertEquals(2, testNotes.getNumQuestions());
        testNotes.addQA("What is an integer?", "A whole number");
        assertEquals(3, testNotes.getNumQuestions());
        assertEquals("What is an integer?", testNotes.getQuestion(0));
        assertEquals("A whole number", testNotes.getAnswer(0));
        assertThrows(EmptyStringException.class, () -> testNotes.addQA("", "a1"));
        assertThrows(EmptyStringException.class, () -> testNotes.addQA("q1", "")); 
        assertThrows(EmptyStringException.class, () -> testNotes.addQA("", "a1"));  
        
    }

    @Test
    void testAddMultipleQuestionAnswer() throws OutOfBoundsException, EmptyStringException {
        assertEquals(2, testNotes.getNumQuestions());
        testNotes.addQA("What is the first colour of the rainbow?", "Red");
        assertEquals(3, testNotes.getNumQuestions());
        assertEquals("What is an integer?", testNotes.getQuestion(0));
        testQList.add("What is the first colour of the rainbow?");
        assertEquals(testQList, testNotes.getAllQuestions());
    }

    @Test
    void testSize() {
        assertEquals(2, testQList.size());
        testQList.add("Another Question");
        assertEquals(3, testQList.size());
        assertEquals("Another Question", testQList.get(2));
    }

    @Test
    void testRandom() throws EmptyListException {
        int randomIndex = testNotes.getRandom();
        assertTrue(randomIndex >= 0 && randomIndex < testNotes.getNumQuestions());

    }

    @Test
    void testRandomOne() throws EmptyListException {
        int randomIndex = testNotesOne.getRandom();
        assertEquals(0, randomIndex);

    }

    @Test
    void testGetRandomQ() {
        String randomQuestion = testNotes.getRandomQuestion();
        assertTrue(testNotes.getAllQuestions().contains(randomQuestion));
    }

    @Test
    void testGetRandomNone() {
        assertEquals("No questions!", testNotesEmpty.getRandomQuestion());
    }
}
