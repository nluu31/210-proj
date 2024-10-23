package persistence;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.Test;
import java.util.List;
import Exceptions.EmptyStringException;
import model.Notes;


// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    // tests reading when there is no file - should throw an exception
    @Test 
   public void testReaderNonExistentFIle() throws EmptyStringException {
        JsonReader reader = new JsonReader(".data/NA.json");
        try {
            Notes note = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }
    // tests reading with a custom file that is empty 
     @Test
    public void testReaderEmptyNotes() throws EmptyStringException {
        JsonReader reader = new JsonReader("./data/testEmptyNotes.json");
        try {
            Notes note = reader.read();
            assertEquals("210", note.getCourse());
            assertEquals(0, note.getNumQuestions());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

     // tests reading with a custom file that is constructed normally
     @Test
    public void testReaderNotes() throws EmptyStringException {
        JsonReader reader = new JsonReader("./data/testNormalNotes.json");
        try {
            Notes note = reader.read();
            assertEquals("210", note.getCourse());
            assertEquals(2, note.getNumQuestions());
            List<String> allQuestions = note.getAllQuestions();
            assertEquals(2, allQuestions.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
