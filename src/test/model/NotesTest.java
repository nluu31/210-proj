package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.EmptyListException;
import Exceptions.OutOfBoundsException;

import java.util.*;


public class NotesTest {
    private Notes testNotes;
    private Notes testNotesEmpty;
    private Notes testNotesOne;
    private List<String> testQList;
    private List<String> testAList;
    


    @BeforeEach
    void runBefore() {
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
void testGetter() {
    assertEquals(testAList, testNotes.getAllAnswers());
    
    try {
        assertEquals("A whole number", testNotes.getAnswer(0));
        assertEquals("What is an integer?", testNotes.getQuestion(0));
    } catch (OutOfBoundsException e) {
        fail("Should not have thrown an OutOfBoundsException");
    }

    // Check for out-of-bounds access
    try {
        testNotes.getAnswer(2);
        fail("Expected an OutOfBoundsException to be thrown");
    } catch (OutOfBoundsException e) {
        // Expected exception
    }

    try {
        testNotes.getQuestion(2);
        fail("Expected an OutOfBoundsException to be thrown");
    } catch (OutOfBoundsException e) {
        // Expected exception
    }
}


@Test
void testAddOneQuestionAnswer() {
    assertEquals(2, testNotes.getNumQuestions());
    
    // Add a new question and answer
    testNotes.addQA("What is an integer?", "A whole number");
    
    // Check the number of questions
    assertEquals(3, testNotes.getNumQuestions());
    
    // Check the specific questions and answers
    try {
        assertEquals("What is an integer?", testNotes.getQuestion(2)); // Get the new question
        assertEquals("A whole number", testNotes.getAnswer(2)); // Get the new answer
    } catch (OutOfBoundsException e) {
        fail("Should not have thrown an OutOfBoundsException");
    }

    // Check that accessing an out-of-bounds index throws an exception
    try {
        testNotes.getQuestion(3);
        fail("Expected an OutOfBoundsException to be thrown");
    } catch (OutOfBoundsException e) {
        // Expected exception
    }

    try {
        testNotes.getAnswer(3);
        fail("Expected an OutOfBoundsException to be thrown");
    } catch (OutOfBoundsException e) {
        // Expected exception
    }
}

@Test
void testAddMultipleQuestionAnswer() {
    assertEquals(2, testNotes.getNumQuestions());
    
    // Add a new question and answer
    testNotes.addQA("What is the first colour of the rainbow?", "Red");
    
    // Check the updated number of questions
    assertEquals(3, testNotes.getNumQuestions());
    
    // Update testQList with the new question
    testQList.add("What is the first colour of the rainbow?");
    
    // Assert that the list of questions matches the expected list
    assertEquals(testQList, testNotes.getAllQuestions());
    
    // Test out of bounds for the getQuestion method
    assertThrows(OutOfBoundsException.class, () -> testNotes.getQuestion(3)); // Invalid index
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

