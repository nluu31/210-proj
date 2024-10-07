package model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotesTest {
    
    @BeforeEach
    void runBefore() {
        testNotes = new Note("CPSC210");
    }

    @Test
    void testConstructor() {
        assertEqual("CPSC210", testNotes.getCourse());
        assertEqual(0, testNotes.getNumQuestions());
    }

    @Test
    void testAddOneQuestionAnswer() {
        assertEqual(0, testNotes.getNumQuestions());
        testNotes.addQA("What is an integer?", "A Whole Number");
        assertEqual(1, testNotes.getNumQuestions());
        assertEqual("What is an integer?", testNotes.getQuestion(0));
        assertEqual8("A Whole Number", testNotes.getAnswer(0));
    }
    @Test
    testList = new ArrayList<>;
    testList.add("What is an integer?");
    testList.add("What is a string?");

    void testAddMultipleQuestionAnswer() {
        assertEqual(0, testNotes.getNumQuestions());
        testNotes.addQA("What is an integer?", "A Whole Number");
        testNotes.addQA("What is a string?", "A combination of characters");
        assertEqual(2, testNotes.getNumQuestions());
        assertEqual("What is an integer?", testNotes.getQuestion(0));
        assertEqual("A Whole Number", testNotes.getAllQuestions());
    }
}
