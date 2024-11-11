package ui;

import model.Notes;
import model.Quiz;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import exceptions.EmptyStringException;
import exceptions.OutOfBoundsException;

// Referenced from the Teller App
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class NotesApp {
    private static final String JSON_STORE = "./data/notes.json";
    private Scanner input;
    private Notes notes;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Notes application
    public NotesApp() throws EmptyStringException {
        runNotes();

    }

    public void runNotes() throws EmptyStringException {
        boolean keepGoing = true;
        String command = null;
        init();
        while (keepGoing) {
            displayMenu();
            command = input.next();
            if (command.equals("x")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes the user inputs
    @SuppressWarnings("methodlength")
    private void processCommand(String command) throws EmptyStringException {
        if (command.equals("q")) {
            doAddQuestions(); // ADDS question
        } else if (command.equals("w")) {
            System.out.println("Number of questions: " + doNumQuestions());
        } else if (command.equals("e")) {
            System.out.println("Questions: " + doQuestions());
        } else if (command.equals("r")) {
            System.out.println("Answers: " + doAnswers());
        } else if (command.equals("t")) {
            String answer = doGetAnswer(); // finds specific answer
            System.out.println("Answer: " + answer);
        } else if (command.equals("y")) {
            String question = doGetQuestion(); // finds specific question
            System.out.println("Question: " + question);
        } else if (command.equals("u")) {
            String randomQuestion = doTest(); // gets a random question
            System.out.println("Random Question: " + randomQuestion);
        } else if (command.equals("i")) {
            String className = doGetClass(); // gets a random question
            System.out.println("Your course name is " + className);
        } else if (command.equals("o")) {
            System.out.println(doMakeQuiz());
        } else if (command.equals("s")) {
            saveNotes();
        } else {
            System.out.println("Not a valid option!");
        }
    }

    // MODIFIES: this
    // EFFECTS: initiates the program
    private void init() throws EmptyStringException {
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
        notes = new Notes("");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        initMenu();
    }

    // EFFECTS: Generates a multiple-choice quiz and returns the result
    private String doMakeQuiz() {
        Quiz quiz = new Quiz(notes);
        quiz.generateQuiz();
    
        quiz.displayQuestion();
    
        boolean correct = false;
    
        while (!correct) {
            System.out.println("Please select an answer (A, B, C, or D):");
            String userAnswer = input.next().toUpperCase();
    
            if (userAnswer.length() == 1 && userAnswer.matches("[A-D]")) {
                if (quiz.checkAnswer(userAnswer)) {
                    return "Correct! Well done!";
                } else {
                    System.out.println("Incorrect. Try again!");
                }
            } else {
                System.out.println("Invalid input.");
            }
        }
        return "Quiz completed!";
    }
    
    // EFFECTS: displays the initialization menu
    private void initMenu() throws EmptyStringException {
        boolean validInput = true;
        while (validInput) {
            System.out.println("\nSelect from:");
            System.out.println("\tn -> Enter the name of a new course");
            System.out.println("\tl -> Load a previously saved course");
            String command = input.nextLine();
            if (command.equals("n")) {
                doNewNote();
                validInput = false;
            } else if (command.equals("l")) {
                loadNotes();
                validInput = false;
            } else {
                System.out.println("Invalid option. Please enter n or l.");
            }
        }
    }

    private void doNewNote() throws EmptyStringException {
        System.out.println("Enter the name of the course!");
        String courseName = input.nextLine();
        if (courseName.trim().isEmpty()) {
            System.out.println("Course name cannot be empty!");
            init();
        }
        notes = new Notes(courseName);
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
        System.out.println("\ti -> View class name");
        System.out.println("\to -> Take a multiple-choice quiz!");
        System.out.println("\ts -> save questions and answers to file");
        System.out.println("\tx -> quit");
        ;
    }

    // MODIFIES: this
    // EFFECTS: adds question and answer pair to the notes
    private void doAddQuestions() {
        System.out.println("Enter your question:");
        String question = input.next();
        System.out.println("Enter the answer:");
        String answer = input.next();
        System.out.println("Which unit is your question from?");
        String unit = input.next();
        if (question.trim().isEmpty() || answer.trim().isEmpty()) {
            System.out.println("Question and answer cannot be empty!");
            return;
        }
        try {
            notes.addQA(question, answer, unit);
        } catch (EmptyStringException e) {
            System.out.println("Please enter a valid question/answer!");
        }
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

    // EFFECTS: returns a specific question from the list or returns message if
    // exception is caught
    private String doGetQuestion() {
        System.out.println("Enter the index of the question you want");
        int question = input.nextInt();
        try {
            return notes.getQuestion(question);
        } catch (OutOfBoundsException e) {
            return "Value is not within the bounds of the list";
        }
    }

    // EFFECTS: returns a specific answer from the list or returns message if
    // exception is caught
    private String doGetAnswer() {
        System.out.println("Enter the index of the answer you want");
        int answer = input.nextInt();
        try {
            return notes.getAnswer(answer);
        } catch (OutOfBoundsException e) {
            return "Value is not within the bounds of the list";
        }
    }

    // EFFECTS: returns a random question from the list
    private String doTest() {
        return notes.getRandomQuestion();
    }

    // EFFECTS: returns the course name
    private String doGetClass() {
        return notes.getCourse();
    }

    // EFFECTS: saves the notes to file
    private void saveNotes() {
        try {
            jsonWriter.open();
            jsonWriter.write(notes);
            jsonWriter.close();
            System.out.println("Saved " + notes.getAllQuestions() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads notes from file
    private void loadNotes() throws EmptyStringException {
        try {
            notes = jsonReader.read();
            System.out.println("Loaded " + notes.getAllQuestions() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
