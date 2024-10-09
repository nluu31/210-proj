package ui;

public class NotesApp {

    // EFFECTS: runs the Notes application
    public NotesApp() {
        runNotes();
    }

    public void runNotes() {
        boolean keepGoing = true;
        String command = null;

        init();

        while(keepGoing) {
            displayMenu();
            command = input.next();

            if(command.equals("l") {
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
            doAddQuestions(); //ADDS question
        } else if (command.equals("w")) {
            doNumQuestions();  //gets number of question/answer pairs
        } else if (command.equals("e")) {
            doQuestions(); // gets all questions
        } else if (command.equals("r")) {
            doAnswers(); // gets all answers
        } else if (command.equals("t")) {
            doGetAnswer(); // finds specific answer
        } else if (command.equals("y")) {
            doGetQuestion(); // finds specific question
        } else if (command.equals("u")) {
            doTest() // gets a random question
        } else {
            System.out.println("Not a valid option!");
        }
        
    }

    private void init() {
        
    }
}
