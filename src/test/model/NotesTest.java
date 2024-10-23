package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.EmptyListException;
import exceptions.EmptyStringException;
import exceptions.OutOfBoundsException;

import java.util.*;

public class NotesTest {
    private Notes testNotes;
    private Notes testNotesEmpty;
    private Notes testNotesOne;
    private List<String> testQList;
    private List<String> testAList;
    private List<String> unit1List;

    @BeforeEach
    void runBefore() throws EmptyStringException {
        testNotes = new Notes("CPSC210");
        testNotes.addQA("What is an integer?", "A whole number", "Unit 1");
        testNotes.addQA("What is a string?", "A combination of characters", "Unit 1");
        testNotes.addQA("What is a character?", "A single letter", "Unit 2");
        testQList = new ArrayList<String>();
        testAList = new ArrayList<String>();
        testQList.add("What is an integer?");
        testAList.add("A whole number");
        testQList.add("What is a string?");
        testAList.add("A combination of characters");
        testQList.add("What is a character?");
        testAList.add("A single letter");
        testNotesEmpty = new Notes("Empty");
        testNotesOne = new Notes("Empty");
        testNotesOne.addQA("Q1", "A2", "U1");
        unit1List = new ArrayList<>();
        unit1List.add("What is an integer?");
        unit1List.add("What is a string?");

    }

    @Test
    void testConstructor() {
        assertEquals("CPSC210", testNotes.getCourse());
        assertEquals(3, testNotes.getNumQuestions());
        ArrayList<QuestionAnswer> emptyQA = new ArrayList<>();
        assertEquals(emptyQA, testNotes.getAllFromUnit("Unit 3"));
        assertEquals(unit1List, testNotes.getAllFromUnit("Unit 1"));

    }

    @Test
    void testConstructorException() {
        try {
            QuestionAnswer newQA = new QuestionAnswer("", "a1", "1");
            fail("Should have thrown an EmptyStringException");
        } catch (EmptyStringException e) {
            // pass
        }
        try {
            QuestionAnswer newQA = new QuestionAnswer("q1", "", "1");
            fail("Should have thrown an EmptyStringException");
        } catch (EmptyStringException e) {
            // pass
        }
        try {
            QuestionAnswer newQA = new QuestionAnswer("q1", "a1", "");
            fail("Should have thrown an EmptyStringException");
        } catch (EmptyStringException e) {
            // pass
        }

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
        try {
            testNotes.getAnswer(3);
            fail("Expected an OutOfBoundsException to be thrown");
        } catch (OutOfBoundsException e) {
            // Expected exception
        }

        try {
            testNotes.getQuestion(3);
            fail("Expected an OutOfBoundsException to be thrown");
        } catch (OutOfBoundsException e) {
            // Expected exception
        }
        try {
            testNotes.getQuestion(-1);
            fail("Expected an OutOfBoundsException to be thrown");
        } catch (OutOfBoundsException e) {
            // pass
        }
    }

    @Test
    void testAddOneQuestionAnswer() throws EmptyStringException {
        assertEquals(3, testNotes.getNumQuestions());
        testNotes.addQA("What is an integer?", "A whole number", "Unit 1");
        assertEquals(4, testNotes.getNumQuestions());
        try {
            assertEquals("What is a character?", testNotes.getQuestion(2));
            assertEquals("A single letter", testNotes.getAnswer(2));
        } catch (OutOfBoundsException e) {
            fail("Should not have thrown an OutOfBoundsException");
        }
        try {
            testNotes.getQuestion(4);
            fail("Expected an OutOfBoundsException to be thrown");
        } catch (OutOfBoundsException e) {
            // pass
        }
    }

    @Test
    void testAddMultipleQuestionAnswer() throws EmptyStringException {
        assertEquals(3, testNotes.getNumQuestions());
        testNotes.addQA("What is the first colour of the rainbow?", "Red", "Unit 2");
        assertEquals(4, testNotes.getNumQuestions());
        testQList.add("What is the first colour of the rainbow?");
        assertEquals(testQList, testNotes.getAllQuestions());
        assertThrows(OutOfBoundsException.class, () -> testNotes.getQuestion(4));
    }

    @Test
    void testGetAnswerException() throws OutOfBoundsException {
        try {
            testNotes.getAnswer(3);
            fail("Expected exception");
        } catch (OutOfBoundsException e) {
            // pass
        }
        try {
            testNotes.getAnswer(-1);
            fail("Expected exception");
        } catch (OutOfBoundsException e) {
            // pass
        }
    }

    @Test
    void testSize() {
        assertEquals(3, testQList.size());
        testQList.add("Another Question");
        assertEquals(4, testQList.size());
        assertEquals("Another Question", testQList.get(3));
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

    @Test
    void testToJson() throws EmptyStringException {
        Notes testNotesJson = new Notes("CPSC210");
        testNotesJson.addQA("What is an integer?", "A whole number", "Unit 1");
        testNotesJson.addQA("What is a string?", "A combination of characters", "Unit 1");
        JSONObject expectedJson = new JSONObject();
        expectedJson.put("course", "CPSC210");
        JSONArray questionsArray = new JSONArray();
        JSONObject qa1 = new JSONObject();
        qa1.put("question", "What is an integer?");
        qa1.put("answer", "A whole number");
        qa1.put("unit", "Unit 1");
        JSONObject qa2 = new JSONObject();
        qa2.put("question", "What is a string?");
        qa2.put("answer", "A combination of characters");
        qa2.put("unit", "Unit 1");
        questionsArray.put(qa1);
        questionsArray.put(qa2);
        expectedJson.put("questions", questionsArray);
        JSONObject actualJson = testNotesJson.toJson();
        assertEquals(expectedJson.toString(), actualJson.toString());
    }
}
