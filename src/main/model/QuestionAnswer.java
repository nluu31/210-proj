package model;

import org.json.JSONObject;

import Exceptions.EmptyStringException;

//Represents a Question and Answer Pair
public class QuestionAnswer {
    private String question; // Question 
    private String answer; // Answer
    private String unit; // the unit for the course 
    
    public QuestionAnswer(String question, String answer, String unit) throws EmptyStringException {
        if(question=="" || answer == "" || unit=="") {
            throw new EmptyStringException();
        }
        this.question = question;
        this.answer = answer;
        this.unit = unit;
    }

    // Getter for question
    //EFFECTS: returns the question 
    public String getQuestion() {
        return question;
    }

    // Getter for Answer
    //EFFECTS: returns the answer 
    public String getAnswer() {
        return answer;
    }

    // Getter for Unit
    //EFFECTS: returns the unit  
    public String getUnit() {
        return unit;
    }

    public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("question", question);
    json.put("answer", answer);
    json.put("unit", unit);
    return json;
}

    


    
}
