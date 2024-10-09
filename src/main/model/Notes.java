package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import Exceptions.EmptyListException;
import Exceptions.EmptyStringException;
import Exceptions.OutOfBoundsException;

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
     * 
     * MODIFIES: this
     * EFFECTS: adds the question and answer to the list for the class or throws
     * EmptyStringException if there is an empty string
     */

    public void addQA(String question, String answer) throws EmptyStringException {
        if(question=="" || answer == "") {
            throw new EmptyStringException();
        }
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
     * EFFECTS: returns a specified answer in the list or throws
     * an exception if the answer number < 0 or answer number >= size
     */
    public String getAnswer(int answerNumber) throws OutOfBoundsException {
        if (answerNumber < 0 || answerNumber >= answers.size()) {
            throw new OutOfBoundsException();
        }
        return answers.get(answerNumber);
    }

    /*
     * EFFECTS: returns a specified question in the list
     */
    public String getQuestion(int questionNumber) throws OutOfBoundsException {
        if (questionNumber < 0 || questionNumber >= questions.size()) {
            throw new OutOfBoundsException();
        }
        
        return questions.get(questionNumber);
    }

    /*
     * EFFECTS: returns the name of the course or 
     * returns an EmptyListException if the list is empty
     */
    public String getCourse() {
        return course;
    }

        public int getRandom() throws EmptyListException {
            if (questions.isEmpty()) {
                throw new EmptyListException();
            }
            Random random = new Random();
            return random.nextInt(getNumQuestions());
        }
    
    /*
     * EFFECTS: returns a random question with the index generated from 
     * the getRandom method
     * 
     */
    public String getRandomQuestion() {
        try {
            return questions.get(getRandom());
        } catch(EmptyListException e) {
            return "No questions!";
    }
}
}



    
