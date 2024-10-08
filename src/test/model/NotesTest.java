package model;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotesTest{
    private Notes testNotes;
    
    @BeforeEach
    void runBefore() {
        testNotes = new Notes("CPSC210");
    }

    @Test
    void testConstructor() {
        assertEquals("CPSC210", testNotes.getCourse());
        assertEquals(0, testNotes.getNumQuestions());
    }

    @Test
    void testAddOneQuestionAnswer() {
        assertEquals(0, testNotes.getNumQuestions());
        testNotes.addQA("What is an integer?", "A Whole Number");
        assertEquals(1, testNotes.getNumQuestions());
        assertEquals("What is an integer?", testNotes.getQuestion(0));
        assertEquals("A Whole Number", testNotes.getAnswer(0));
    }
    @Test
    void testAddMultipleQuestionAnswer() {
        assertEquals(0, testNotes.getNumQuestions());
        testNotes.addQA("What is an integer?", "A Whole Number");
        testNotes.addQA("What is a string?", "A combination of characters");
        assertEquals(2, testNotes.getNumQuestions());
        assertEquals("What is         assertEquals("A Whole Number", testNotes.getAllQuestions());
an integer?", testNotes.getQuestion(0));
    }
}
