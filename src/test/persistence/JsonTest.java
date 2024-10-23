package persistence;
import model.QuestionAnswer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkQA(String question, String answer, String unit, QuestionAnswer qa) {
        assertEquals(question, qa.getQuestion());
        assertEquals(answer, qa.getAnswer());
        assertEquals(unit, qa.getUnit());
    }
}
