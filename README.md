# My Personal Project - Study Expert



## About the project:
For my term project in CPSC 210, I plan on creating an application that helps students study for exams within in school. The application will work by self-inputting questions/topics with their own written answers. Once you have recorded these questions and answers,there will be a feature that will allow you to "quiz yourself" by choosing a random question/topic written by the user alongside four answers to other questions(including a correct answer). It will serve as a multiple choice quiz. This application will be designed towards students and scholars looking to study in different ways. This project is of interest to me because I often just blurt out notes and look for quizzes online to challenge my memory. Creating this  app combines the two features and will hopefully allow for a similar approach to studying like I always have.

Alongside a multiple choice function, there will also be a true or false question and even a written answer. The written answer cannot be manually graded but will show the recorded answer afterwards. An example of this app in action could be that you record around 10-15 questions into the system and then click on a "quiz me" button. After which, the app will prompt you into a multiple choice/true or false/written answer quiz that will challenge you to pick the right answers in a "test" setting.

## User Stories
- As a user, I wish to be able to note down concepts I have learned in class
- As a user, I would like to be quizzed on materials I have noted in multiple choice form
- As a user, I want to be able to see what questions and answers I have already added
- As a user, I want to be able to name my course 
- As a user, I want to be able to save my data/courses 
- As a user, I want to be able to load my courses
- As a user, I want to be able to view questions from a unit
- As a user, I want to be able to remove a question

# Instructions for End User
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking the "Get all from specified Unit" button once you have created/loaded notes.
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking the "Generate Quiz" button once you have created/loaded notes. 
- You can locate my visual component by running the program for the first time, the image will appear in the starting screen.
- You can save the state of my application by clicking the "Save your notes!" button on the button after you loaded/created a course.
- You can reload the state of your application by clicking "Load Notes" in the startup screen.

# Phase 4: Task 2

Mon Nov 25 15:56:55 PST 2024
Loaded last saved notes

Mon Nov 25 15:56:55 PST 2024
Added Question: q1

Mon Nov 25 15:56:55 PST 2024
Added Question: 1

Mon Nov 25 15:57:07 PST 2024
Added Question: temp1

Mon Nov 25 15:57:13 PST 2024
Saved changes to last file

Mon Nov 25 15:57:16 PST 2024
Removed question: temp1

Mon Nov 25 15:57:17 PST 2024
Saved changes to last file

# Phase 4: Task 3

Judging by my UML diagram, I found that my NotesGUI class in the UI package requires the most amount of changes. The NotesGUI class contains a lot of code to visually display the content, but I feel like there are too many methods in that class that could easily be refactored into another external class to allow for a more coherent and readable code. For example, I have methods for both displaying panels and methods for actions related to the X's and Y's of my class. Given more time, I think it would be smart to split NotesGUI into two classes: one to display the panels and have the visual effect, and another that is responsible for generating the actions related to my X's and Y's.
