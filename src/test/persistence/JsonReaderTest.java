package persistence;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import model.Notes;

public class JsonReaderTest extends JsonTest {

    // tests reading when there is no file - should throw an exception
    @Test 
    void testReaderNonExistentFIle() {
        JsonReader reader = new JsonReader(".data/NA.json");
        try {
            Notes note = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }


}
