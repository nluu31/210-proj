package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import model.Event;
import model.EventLog;
import model.Notes;
import model.Quiz;
import exceptions.EmptyStringException;
import persistence.JsonReader;
import persistence.JsonWriter;

public class NotesGUI implements WindowListener {
    private static final String JSON_STORE = "./data/notes.json";
    private JFrame frame;
    private JPanel startPanel;
    private JPanel mainPanel;
    private JButton newNoteButton;
    private JButton loadNoteButton;
    private JButton addQuestionButton;
    private JButton viewQuestionsButton;
    private JButton generateQuizButton;
    private JButton hideAnswerButton;
    private JButton getAllFromUnitButton;
    private JButton saveNotesButton;
    private JButton removeQuestionButton;
    private Notes note;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JTextArea displayArea;

    // EFFECTS: creates a JFrame for the GUI
    public NotesGUI() {
        frame = new JFrame("Study Expert");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(this);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Helvetica", Font.PLAIN, 20));
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(350, 200));

        JPanel imagePanel = getImagePanel();
        JPanel buttonPanel1 = getButtonPanel1();
        getStartPanel(imagePanel, buttonPanel1);

        JPanel buttonPanel = mainPanelSetup();

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(startPanel);
        frame.setVisible(true);

        setUpStartPanelListeners();
    }

    // EFFECTS: sets up the main panel for display
    private JPanel mainPanelSetup() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        addQuestionButton = new JButton("Add Question-Answer");
        viewQuestionsButton = new JButton("View All Questions");
        generateQuizButton = new JButton("Generate Quiz");
        getAllFromUnitButton = new JButton("Get all from specified Unit");
        saveNotesButton = new JButton("Save your notes!");
        removeQuestionButton = new JButton("Remove a question");
        hideAnswerButton = new JButton("Hide Answers");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addQuestionButton);
        buttonPanel.add(getAllFromUnitButton);
        buttonPanel.add(viewQuestionsButton);
        buttonPanel.add(hideAnswerButton);
        buttonPanel.add(removeQuestionButton);
        buttonPanel.add(generateQuizButton);
        buttonPanel.add(saveNotesButton);
        return buttonPanel;
    }

    // EFFECTS: creates the start panel
    private void getStartPanel(JPanel imagePanel, JPanel buttonPanel1) {
        newNoteButton = new JButton("Create New Note");
        loadNoteButton = new JButton("Load Last Saved Note");
        buttonPanel1.add(newNoteButton);
        buttonPanel1.add(loadNoteButton);
        startPanel.add(buttonPanel1);
        startPanel.add(imagePanel, BorderLayout.CENTER);
    }

    // EFFECTS: creates start panel layout
    private JPanel getButtonPanel1() {
        startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        return buttonPanel1;
    }

    // EFFECTS: creates the image
    private JPanel getImagePanel() {
        ImageIcon imageIcon = new ImageIcon("image.png");
        JLabel imageLabel = new JLabel(imageIcon);
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.add(imageLabel);
        return imagePanel;
    }

    // EFFECTS: generates the action listeners for each button
    private void setUpStartPanelListeners() {
        newNoteButton.addActionListener(e -> {
            try {
                createNewNote();
                switchToMainPanel();
            } catch (EmptyStringException es) {
                JOptionPane.showMessageDialog(frame, "Course name cannot be empty.");
            }

        });

        loadNoteButton.addActionListener(e -> {
            loadNote();
            switchToMainPanel();
        });
    }

    // EFFECTS: generates the action listeners for each button
    private void setUpMainPanelListeners() {
        addQuestionButton.addActionListener(e -> addQuestionAnswer());
        saveNotesButton.addActionListener(e -> saveNotes());
        generateQuizButton.addActionListener(e -> generateQuiz());
        getAllFromUnitButton.addActionListener(e -> getAllFromUnitGUI());
        removeQuestionButton.addActionListener(e -> removeQuestion());
        hideAnswerButton.addActionListener(e -> hideAnswers());
        viewQuestionsButton.addActionListener(e -> viewAllQuestions());

    }

    // EFFECTS: creates a new class note
    private void createNewNote() throws EmptyStringException {
        String courseName = JOptionPane.showInputDialog(frame, "Enter course name:");
        if (courseName != null && !courseName.trim().isEmpty()) {
            note = new Notes(courseName);
            JOptionPane.showMessageDialog(frame, "New note created for course: " + courseName);
        } else {
            throw new EmptyStringException();
        }
    }

    // EFFECTS: loads notes as a json file
    private void loadNote() {
        try {
            note = jsonReader.read();
            System.out.println("Loaded " + note.getAllQuestions() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (Exception e) {
            System.out.println("Error has occurred");
        }
        viewAllQuestions();
        JOptionPane.showMessageDialog(frame, "Last saved note loaded.");
    }

    // MODIFIES: this
    // EFFECTS: saves notes as a json file
    private void saveNotes() {
        try {
            jsonWriter.open();
            jsonWriter.write(note);
            jsonWriter.close();
            System.out.println("Saved " + note.getAllQuestions() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        JOptionPane.showMessageDialog(frame, "Saved your notes.");
    }

    // MODIFIES: this
    // EFFECTS: adds a question, answer, and unit to notes
    private void addQuestionAnswer() {
        String question = JOptionPane.showInputDialog(frame, "Enter the question:");
        String answer = JOptionPane.showInputDialog(frame, "Enter the answer:");
        String unit = JOptionPane.showInputDialog(frame, "Enter the unit:");

        if (question != null && answer != null && unit != null) {
            try {
                note.addQA(question, answer, unit);
                JOptionPane.showMessageDialog(frame, "Question-Answer added successfully.");
            } catch (EmptyStringException e) {
                JOptionPane.showMessageDialog(frame, "Error: Fields cannot be empty.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "All fields must be filled.");
        }
        viewAllQuestions();
    }

    // EFFECTS: returns all the questions from a unit
    private void getAllFromUnitGUI() {
        String unit = JOptionPane.showInputDialog(frame, "Enter the unit:");

        if (unit != null) {
            try {
                List<String> allFromUnit = note.getAllFromUnit(unit);
                StringBuilder questionDisplay = new StringBuilder("Questions and Answers from unit" + unit + "\n");
                for (String s : allFromUnit) {
                    questionDisplay.append(s).append("\n");
                }
                JOptionPane.showMessageDialog(frame, questionDisplay);
            } catch (EmptyStringException e) {
                JOptionPane.showMessageDialog(frame, "Error: Field cannot be empty.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Field must be filled.");
        }

    }

    // EFFECTS: displays all questions, answers, and units
    private void viewAllQuestions() {
        if (note == null) {
            displayArea.setText("No note loaded. Please load or create a note.");
            return;
        }
        List<String> questionsAndAnswers = note.getAllQuestionsAndAnswers();
        if (questionsAndAnswers.isEmpty()) {
            displayArea.setText("No questions available.");
            return;
        }
        StringBuilder questionDisplay = new StringBuilder("Course Name: " + note.getCourse());
        questionDisplay.append("\n\nQuestions and Answers: \n");
        for (String qa : questionsAndAnswers) {
            questionDisplay.append(qa).append("\n");
        }
        displayArea.setText(questionDisplay.toString());
    }

    // EFFECTS: displays only questions and answers
    private void hideAnswers() {
        if (note == null) {
            displayArea.setText("No note loaded. Please load or create a note.");
            return;
        }
        List<String> questionsAndAnswers = note.showOnlyQuestions();
        if (questionsAndAnswers.isEmpty()) {
            displayArea.setText("No questions available.");
            return;
        }
        StringBuilder questionDisplay = new StringBuilder("Course Name: " + note.getCourse());
        questionDisplay.append("\n\nQuestions: \n");
        for (String qa : questionsAndAnswers) {
            questionDisplay.append(qa).append("\n");
        }
        displayArea.setText(questionDisplay.toString());
    }

    // EFFECTS: generates a multiple-choice quiz
    private void generateQuiz() {
        Quiz quizObject = new Quiz(note);
        quizObject.generateQuiz();

        boolean correct = false;

        while (!correct) {
            StringBuilder quizDisplay = new StringBuilder("Question:\n");
            quizDisplay.append(quizObject.getQuestion()).append("\n\nOptions:\n");
            List<String> options = quizObject.getOptions();

            for (int i = 0; i < options.size(); i++) {
                quizDisplay.append((char) ('A' + i)).append(": ").append(options.get(i)).append("\n");
            }

            String userAnswer = JOptionPane.showInputDialog(
                    frame,
                    quizDisplay.toString(),
                    "Multiple Choice Quiz",
                    JOptionPane.QUESTION_MESSAGE);

            correct = answerQuestion(quizObject, correct, userAnswer);
        }

        JOptionPane.showMessageDialog(frame, "Quiz completed!");
    }

    // EFFECTS: generates the prompt for the user to answer the question
    private boolean answerQuestion(Quiz quizObject, boolean correct, String userAnswer) {
        if (userAnswer.length() == 1) {
            userAnswer = userAnswer.toUpperCase();

            if (userAnswer.matches("[A-D]")) {
                if (quizObject.checkAnswer(userAnswer)) {
                    JOptionPane.showMessageDialog(frame, "Correct! Well done!");
                    correct = true;
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect! Try again.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Input! Please enter A, B, C, or D.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Input! Please enter a single letter.");
        }
        return correct;
    }

    // EFFECTS: changes the panel to the main panel
    private void switchToMainPanel() {
        frame.remove(startPanel);
        frame.add(mainPanel);
        frame.revalidate();
        frame.repaint();

        setUpMainPanelListeners();
    }

    // EFFECTS: displays the frame by setting it's visibility to true
    public void display() {
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: removes a question from the list and updates the display
    private void removeQuestion() {
        String questionToRemove = JOptionPane.showInputDialog(frame, "Enter the question to remove:");

        if (questionToRemove != null) {
            boolean removed = note.removeQuestion(questionToRemove);
            if (removed) {
                JOptionPane.showMessageDialog(frame, "Question removed successfully.");
                viewAllQuestions();
            } else {
                JOptionPane.showMessageDialog(frame, "Question not found.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a valid question.");
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        for (Event event: EventLog.getInstance()) {
            System.out.println(event.toString());
        }
        System.exit(0);
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

}
