package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

import model.Notes;
import exceptions.EmptyStringException;
import persistence.JsonReader;
import persistence.JsonWriter;

public class NotesGUI {
    private static final String JSON_STORE = "./data/notes.json";
    private JFrame frame;
    private JPanel startPanel;
    private JPanel mainPanel;
    private JButton newNoteButton;
    private JButton loadNoteButton;
    private JButton addQuestionButton;
    private JButton viewQuestionsButton;
    private JButton generateQuizButton;
    private Notes note;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JTextArea displayArea;  // Add a JTextArea to display questions and answers

    public NotesGUI() {
        frame = new JFrame("Study Expert");
        frame.setSize(400, 500);  // Increased frame size to fit the JTextArea
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // Create the JTextArea for displaying questions and answers
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Optional: set font for readability
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);

        // JScrollPane to allow scrolling if content exceeds the JTextArea area
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setPreferredSize(new Dimension(350, 200)); // Adjust size of display area

        // Start Panel setup
        startPanel = new JPanel();
        newNoteButton = new JButton("Create New Note");
        loadNoteButton = new JButton("Load Last Saved Note");
        startPanel.add(newNoteButton);
        startPanel.add(loadNoteButton);

        // Main Panel setup
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());  // Use BorderLayout for better organization
        addQuestionButton = new JButton("Add Question-Answer");
        viewQuestionsButton = new JButton("View All Questions");
        generateQuizButton = new JButton("Generate Quiz");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addQuestionButton);
        buttonPanel.add(viewQuestionsButton);
        buttonPanel.add(generateQuizButton);

        // Add components to mainPanel
        mainPanel.add(scrollPane, BorderLayout.CENTER);  // Add the JScrollPane with JTextArea in the center
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);  // Add buttons at the bottom of the panel

        // Add start panel initially
        frame.add(startPanel);
        frame.setVisible(true);

        setUpStartPanelListeners();
    }

    private void setUpStartPanelListeners() {
        newNoteButton.addActionListener(e -> {
            createNewNote();
            switchToMainPanel();
        });

        loadNoteButton.addActionListener(e -> {
            loadNote();
            switchToMainPanel();
        });
    }

    private void setUpMainPanelListeners() {
        addQuestionButton.addActionListener(e -> addQuestionAnswer());
        viewQuestionsButton.addActionListener(e -> viewAllQuestions());
        generateQuizButton.addActionListener(e -> generateQuiz());
    }

    private void createNewNote() {
        String courseName = JOptionPane.showInputDialog(frame, "Enter course name:");
        if (courseName != null && !courseName.trim().isEmpty()) {
            note = new Notes(courseName);
            JOptionPane.showMessageDialog(frame, "New note created for course: " + courseName);
        } else {
            JOptionPane.showMessageDialog(frame, "Course name cannot be empty.");
        }
    }

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

    private void addQuestionAnswer() {
        String question = JOptionPane.showInputDialog(frame, "Enter the question:");
        String answer = JOptionPane.showInputDialog(frame, "Enter the answer:");
        String unit = JOptionPane.showInputDialog(frame, "Enter the unit:");

        if (question != null && answer != null && unit != null &&
            !question.trim().isEmpty() && !answer.trim().isEmpty() && !unit.trim().isEmpty()) {
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
        StringBuilder questionDisplay = new StringBuilder("Questions and Answers:\n");
        for (String qa : questionsAndAnswers) {
            questionDisplay.append(qa).append("\n");
        }
        displayArea.setText(questionDisplay.toString());
    }

    private void generateQuiz() {
        String quiz = note.makeMultipleChoiceQuiz();
        JOptionPane.showMessageDialog(frame, quiz);
    }

    private void switchToMainPanel() {
        frame.remove(startPanel); 
        frame.add(mainPanel);     
        frame.revalidate();
        frame.repaint();

     
        setUpMainPanelListeners();
    }

    public void display() {
        frame.setVisible(true);
    }
}
