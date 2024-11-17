package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Quiz {
    private Notes notes;
    private String question;
    private List<String> options;
    private String correctAnswer;
    private String correctAnswerLabel;

    public Quiz(Notes notes) {
        this.notes = notes;
    }

    // EFFECTS: generates a quiz
    public void generateQuiz() {
        try {
            int randomIndex = getRandomQuestionIndex();
            question = notes.getQuestion(randomIndex);
            correctAnswer = notes.getAnswer(randomIndex);

            options = new ArrayList<>();
            options.add(correctAnswer);

            addOtherOptions();

            Collections.shuffle(options);

            correctAnswerLabel = getCorrectAnswerLabel(correctAnswer);
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
    }

    // EFFECTS: adds wrong options to the quiz from the list of previously set
    // answers
    private void addOtherOptions() {
        Random random = new Random();
        List<String> allAnswers = notes.getAllAnswers();
        List<String> wrongAnswers = new ArrayList<>();

        while (wrongAnswers.size() < 3) {
            String randomAnswer = allAnswers.get(random.nextInt(allAnswers.size()));
            if (!randomAnswer.equals(correctAnswer) && !wrongAnswers.contains(randomAnswer)) {
                wrongAnswers.add(randomAnswer);
            }
        }

        options.addAll(wrongAnswers);
    }

    // EFFECTS: assigns the corresponding character to the answer
    private String getCorrectAnswerLabel(String correctAnswer) {
        int index = options.indexOf(correctAnswer);
        char label = (char) ('A' + index);
        return String.valueOf(label);
    }

    // EFFECTS: generates a random index
    private int getRandomQuestionIndex() {
        Random random = new Random();
        return random.nextInt(notes.getNumQuestions());
    }

    // EFFECTS: displays the question
    public void displayQuestion() {
        System.out.println("Question: " + question);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((char) ('A' + i) + ". " + options.get(i));
        }
    }

    // EFFECTS: returns true if the submitted answer is correct
    public boolean checkAnswer(String userAnswer) {
        return userAnswer.equalsIgnoreCase(correctAnswerLabel);
    }
}
