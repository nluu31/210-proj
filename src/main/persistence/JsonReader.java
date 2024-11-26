package persistence;

import model.Event;
import model.EventLog;
import model.Notes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.*;

import exceptions.EmptyStringException;



// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;
    // EFFECTS: constructs reader to read from source file

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Notes from file and returns it;
    // throws IOException if an error occurs 
    public Notes read() throws IOException, EmptyStringException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded last saved notes"));
        return parseNotes(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses notes from JSON object and returns it
    private Notes parseNotes(JSONObject jsonObject) throws EmptyStringException {
        String course = jsonObject.getString("course");
        Notes notes = new Notes(course);
        addQAs(notes, jsonObject);
        return notes;
    }

    // MODIFIES: notes
    // EFFECTS: parses questionAnswers from JSON object and adds them to notes
    private void addQAs(Notes notes, JSONObject jsonObject) throws EmptyStringException {
        JSONArray jsonArray = jsonObject.getJSONArray("questions");
        for (Object json : jsonArray) {
            JSONObject nextQA = (JSONObject) json;
            addQA(notes, nextQA);
        }
    }

   // MODIFIES: notes
    // EFFECTS: parses a single question/answer pair from JSON object and adds it to Notes
    private void addQA(Notes notes, JSONObject jsonObject) throws EmptyStringException {
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        String unit = jsonObject.getString("unit");
        notes.addQA(question, answer, unit);
    }
}

