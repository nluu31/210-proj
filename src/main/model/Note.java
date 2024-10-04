package model;

public class Notes {

    private String question;  // question
    private String answer;    // answer
    private int reviewed;     // number of times you have visited the question

    /*
     * REQUIRES: questionName and answerName are not empty strings
     * EFFECTS:  creates a note with a question and an answer; 
     *           the note is created with a reviewed integer of 0 
     */
    public Note(String questionName, String answerName) {
        this.reviewed = 0;
        this.answer = answerName;
        this.question = questionName;
    }


    /* 
    EFFECTS: returns the questionName
    */
 
    public String getQuestion() {
        return empty; //stub
    }

    /* 
    EFFECTS: returns the answerName
    */
    public String getAnswer() {
        return empty; //stub
    }

    /* 
    EFFECTS: returns the amount of times Reviewed
    */

    public int getReviewed() {
        return 0; //stub
    }

    /* 
    MODIFIES: this
    EFFECTS: increments the number of times reviewed by one
    */
    public int addReviewed() {
        this.reviewed = this.reviewed++; //STUB
    }

}
