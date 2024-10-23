package persistence;

import org.junit.jupiter.api.Test;

import exceptions.EmptyStringException;
import model.Notes;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest {

    // test to write an illegal file
    @Test
    void testWriterInvalidFile() {
        try {
            Notes note = new Notes("210");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyNote() throws EmptyStringException {
        try {
            Notes note = new Notes("210");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyNotes.json");
            writer.open();
            writer.write(note);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyNotes.json");
            note = reader.read();
            assertEquals("210", note.getCourse());
            assertEquals(0, note.getNumQuestions());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralNote() throws EmptyStringException {
        try {
            Notes note = new Notes("210");
            note.addQA("q1", "a1", "1");
            note.addQA("q2", "a2", "2");
            JsonWriter writer = new JsonWriter("./data/testWriterNormalNotes.json");
            writer.open();
            writer.write(note);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalNotes.json");
            note = reader.read();
            assertEquals("210", note.getCourse());
            List<String> questions = note.getAllQuestions();
            assertEquals(2, questions.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
