package ui;

import java.util.Scanner;

import model.Notes;
import java.util.*;

public class NotesApp {
        private Scanner input;
        private Notes notes;

        // EFFECTS: runs the Notes application
        public NotesApp() {
            runNotes();
        }

        public void runNotes() {
            boolean keepGoing = true;
            String command = null;

            init();

            while (keepGoing) {
                displayMenu();
                command = input.next();

                if (command.equals("l")) {
                    keepGoing = false;
                } else {
                    processCommand(command);
                }
            }
            System.out.println("Goodbye!");
        }

        // MODIFIES: this
        // EFFECTS: processes the user inputs
        private void processCommand(String command) {
            if (command.equals("q")) {
                doAddQuestions(); // ADDS question
            } else if (command.equals("w")) {
                doNumQuestions(); // gets number of question/answer pairs
            } else if (command.equals("e")) {
                doQuestions(); // gets all questions
            } else if (command.equals("r")) {
                doAnswers(); // gets all answers
            } else if (command.equals("t")) {
                doGetAnswer(); // finds specific answer
            } else if (command.equals("y")) {
                doGetQuestion(); // finds specific question
            } else if (command.equals("u")) {
                doTest(); // gets a random question
            } else {
                System.out.println("Not a valid option!");
            }

        }

        // MODIFIES: this
        // EFFECTS: initiates the program
        private void init() {
            notes = new Notes("New");
            input = new Scanner(System.in);
            input.useDelimiter("\r?\n|\r");
        }

        // EFFECTS: displays menu of options
        private void displayMenu() {
            System.out.println("\nSelect from:");
            System.out.println("\tq -> Add Question");
            System.out.println("\tw -> Get Number of Questions");
            System.out.println("\te -> Get all questions");
            System.out.println("\tr -> Get all answers");
            System.out.println("\tt -> Find specific answer");
            System.out.println("\ty -> Find specific question");
            System.out.println("\tu -> Test yourself with a random question!");
            ;
        }

        // MODIFIES: this
        // EFFECTS: adds question and answer pair to the notes
        private void doAddQuestions() {
            System.out.println("Enter your question:");
            String question = input.next();
            System.out.println("Enter the answer:");
            String answer = input.next();
            notes.addQA(question, answer);
            System.out.println("Question and answer added successfully!");
        }

        // EFFECTS: returns the number of question/answer pairs
        private int doNumQuestions() {
            return notes.getNumQuestions();
        }

        // EFFECTS: returns all the questions currently in the list
        private List<String> doQuestions() {
            return notes.getAllQuestions();
        }

        // EFFECTS: returns all the answers currently in the list
        private List<String> doAnswers() {
            return notes.getAllAnswers();
        }

        // EFFECTS: returns a specific question from the list
        private String doGetQuestion() {
            System.out.println("Enter the index of the question you want");
            int question = input.nextInt();
            return notes.getQuestion(question);
        }

        // EFFECTS: returns a specific answer from the list
        private String doGetAnswer() {
            System.out.println("Enter the index of the answer you want");
            int answer = input.nextInt();
            return notes.getQuestion(answer);
        }

        // EFFECTS: returns a random question from the list
        private String doTest() {
            return notes.getRandomQuestion();
        }
    }
