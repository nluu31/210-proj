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
    private List<QuestionAnswer> questionAnswerList ; // a list of question answer pairs

    /*
     * REQUIRES: className is not an empty string
     * EFFECTS: creates a class folder and an empty list of questions and answers
     */
    public Notes(String className) {
        this.course = className;
        this.questionAnswerList  = new ArrayList<>();
        questionAnswerList = new ArrayList<>();
    
    }

    /*
     * 
     * MODIFIES: this
     * EFFECTS: adds the question and answer to the list for the class or throws
     * EmptyStringException if there is an empty string
     */

    public void addQA(String question, String answer, String unit) throws EmptyStringException {
        QuestionAnswer qas = new QuestionAnswer(question, answer, unit);
        this.questionAnswerList.add(qas);
    }


    /*
     * EFFECTS: returns the number of question/answer pairs in the lists
     */
    public int getNumQuestions() {
        return questionAnswerList .size();
    }

    /*
     * EFFECTS: returns all questions in the list
     */
    public List<String> getAllQuestions() {
        ArrayList<String> questionList = new ArrayList<>();
        for (QuestionAnswer qa : questionAnswerList) {
            questionList.add(qa.getQuestion());
        }
        return questionList;
    }

    /*
     * EFFECTS: returns all answers in the list
     */
    public List<String> getAllAnswers() {
        ArrayList<String> answerList = new ArrayList<>();
        for (QuestionAnswer qa : questionAnswerList) {
            answerList.add(qa.getAnswer());
        }
        return answerList;
    }

     /*
     * EFFECTS: returns all answers in the list
     */
    public List<String> getAllFromUnit(String unitName) {
        ArrayList<String> unitList = new ArrayList<>();
        for (QuestionAnswer qa : questionAnswerList) {
            if(qa.getUnit() == unitName) {
                unitList.add(qa.getAnswer());
            } 
        }
        return unitList;
    }

    /*
     * EFFECTS: returns a specified answer in the list or throws
     * an exception if the answer number < 0 or answer number >= size
     */
    public String getAnswer(int answerNumber) throws OutOfBoundsException {
        if (answerNumber < 0 || answerNumber >= questionAnswerList.size()) {
            throw new OutOfBoundsException();
        }
        return questionAnswerList.get(answerNumber).getAnswer();
    }

    /*
     * EFFECTS: returns a specified question in the list
     */
    public String getQuestion(int questionNumber) throws OutOfBoundsException {
        if (questionNumber < 0 || questionNumber >= questionAnswerList.size()) {
            throw new OutOfBoundsException();
        }
        
        return questionAnswerList.get(questionNumber).getQuestion();
    }

    /*
     * EFFECTS: returns the name of the course or 
     * returns an EmptyListException if the list is empty
     */
    public String getCourse() {
        return course;
    }

        public int getRandom() throws EmptyListException {
            if (questionAnswerList.isEmpty()) {
                throw new EmptyListException();
            }
            Random random = new Random();
            return random.nextInt(getNumQuestions());
        }

    public
    
    /*
     * EFFECTS: returns a random question with the index generated from 
     * the getRandom method
     * 
     */
    public String getRandomQuestion() {
        try {
            return questionAnswerList.get(getRandom()).getQuestion();
        } catch(EmptyListException e) {
            return "No questions!";
    }
}
}



    
