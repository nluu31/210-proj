package model;

import java.util.List;
import java.util.ArrayList;

// Represents a notebook with the course it's related to, a question and an answer.
public class Notes {
    private String course; // The course/class of the question
    private List<String> questions; // a list of questions
    private List<String> answers; // a list of answers
    // private int reviewed; // number of times you have visited the question

    /*
     * REQUIRES: className is not an empty string
     * EFFECTS: creates a class folder and an empty list of questions and answers
     */
    public Notes(String className) {
        this.course = className;
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
    }

    /*
     * REQUIRES: question and answer are non-empty strings
     * MODIFIES: this
     * EFFECTS: adds the question and answer to the list for the class
     */

    public void addQA(String question, String answer) {
        this.questions.add(question);
        this.answers.add(answer);
    }

    /*
     * EFFECTS: returns the number of question/answer pairs in the lists
     */
    public int getNumQuestions() {
        return questions.size();
    }

    /*
     * EFFECTS: returns all questions in the list
     */
    public List<String> getAllQuestions() {
        return questions;
    }

    /*
     * EFFECTS: returns all answers in the list
     */
    public List<String> getAllAnswers() {
        return answers;
    }

    /*
     * EFFECTS: returns a specified answer in the list
     */
    public String getAnswer(int answerNumber) {
        return answers.get(answerNumber);
    }

    /*
     * EFFECTS: returns a specified question in the list
     */
    public String getQuestion(int questionNumber) {
        return questions.get(questionNumber);
    }

    /*
     * EFFECTS: returns the name of the course
     */
    public String getCourse() {
        return course;
    }
}

    /*
     * EFFECTS: returns the name of the course
     */

