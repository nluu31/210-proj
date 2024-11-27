package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.EmptyListException;
import exceptions.EmptyStringException;
import exceptions.OutOfBoundsException;
import persistence.Writable;

// Represents a notebook with the course it's related to, a question and an answer.
public class Notes implements Writable {
    private String course; // The course/class of the question
    private List<QuestionAnswer> questionAnswerList; // a list of question answer pairs

    /*
     * REQUIRES: className is not an empty string
     * EFFECTS: creates a class folder and an empty list of questions and answers
     */
    public Notes(String className) {
        this.course = className;
        this.questionAnswerList = new ArrayList<>();
        questionAnswerList = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Created a Class: " + className));
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
        EventLog.getInstance().logEvent(new Event("Added Question: " + qas.getQuestion()));
    }

    // MODIFIES: this
    // EFFECTS: removes the first question matching the given text and returns true
    // if successful;
    // returns false if no matching question is found.
    //
    public boolean removeQuestion(String question) {
        for (int i = 0; i < questionAnswerList.size(); i++) {
            if (questionAnswerList.get(i).getQuestion().equals(question)) {
                questionAnswerList.remove(i);
                EventLog.getInstance().logEvent(new Event("Removed question: " + question));
                return true;
            }
        }
        return false;
    }

    /*
     * EFFECTS: returns the number of question/answer pairs in the lists
     */
    public int getNumQuestions() {
        return questionAnswerList.size();
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
     * EFFECTS: returns all questions from a unit in the list
     */
    public List<String> getAllFromUnit(String unitName) throws EmptyStringException {
        ArrayList<String> unitList = new ArrayList<>();
        if (unitName == "") {
            throw new EmptyStringException();
        } else {
            for (QuestionAnswer qa : questionAnswerList) {
                if (qa.getUnit().equals(unitName)) {
                    unitList.add(qa.getQuestion());
                }
            }
            return unitList;
        }
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

    // EFFECTS: returns a random number
    public int getRandom() throws EmptyListException {
        if (questionAnswerList.isEmpty()) {
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
            return questionAnswerList.get(getRandom()).getQuestion();
        } catch (EmptyListException e) {
            return "No questions!";
        }
    }

    // EFFECTS: generates a multiple choice questions quiz
    public String makeMultipleChoiceQuiz() {
        if (questionAnswerList.size() < 4 && questionAnswerList.size() > 0) {
            return "Not enough questions for a quiz.";
        }
        try {
            int randomIndex = getRandom();
            QuestionAnswer correctQA = questionAnswerList.get(randomIndex);
            List<String> options = generateOptions(correctQA.getAnswer());

            StringBuilder quizDisplay = new StringBuilder("Question: " + correctQA.getQuestion() + "\n");
            for (int i = 0; i < options.size(); i++) {
                quizDisplay.append((char) ('A' + i)).append(". ").append(options.get(i)).append("\n");
            }
            return quizDisplay.toString();
        } catch (EmptyListException e) {
            return "No questions available to create a quiz.";
        }
    }

    // EFFECTS: returns a list of questions and answers
    public List<String> getAllQuestionsAndAnswers() {
        List<String> allQAs = new ArrayList<>();
        for (QuestionAnswer qa : questionAnswerList) {
            allQAs.add("Q: " + qa.getQuestion() + ", A: " + qa.getAnswer() + ", Unit: " + qa.getUnit());
        }
        return allQAs;
    }

    // EFFECTS: returns a list of questions and answers
    public List<String> showOnlyQuestions() {
        List<String> allQAs = new ArrayList<>();
        for (QuestionAnswer qa : questionAnswerList) {
            allQAs.add("Q: " + qa.getQuestion() + ", Unit: " + qa.getUnit());
        }
        return allQAs;
    }

    // EFFECTS: generates a list with the correct answer and 3 unique distractors
    public List<String> generateOptions(String correctAnswer) {
        List<String> options = new ArrayList<>(List.of(correctAnswer));
        Random rand = new Random();

        while (options.size() < 4) {
            String wrongAnswer = questionAnswerList.get(rand.nextInt(questionAnswerList.size())).getAnswer();
            if (!options.contains(wrongAnswer)) {
                options.add(wrongAnswer);
            }
        }
        Collections.shuffle(options);
        return options;
    }

    // EFFECTS: creates a new Json object with courses and questions
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("course", course);
        json.put("questions", questionsToJson());
        EventLog.getInstance().logEvent(new Event("Saved changes to last file"));
        return json;
    }

    // EFFECTS: returns things in this notes as a JSON array
    private JSONArray questionsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (QuestionAnswer qa : questionAnswerList) {
            jsonArray.put(qa.toJson());
        }
        return jsonArray;
    }

}
