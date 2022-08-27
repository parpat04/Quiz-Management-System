
Project 5 CS 180 Readme.md
Program Title: Grademic

This program is an academic interface in which both students and teachers can interact with. All functions of this program utilize GUI’s (Graphical User Interfaces), a visual interface which the user can interact with via clicking and typing. There are two main interfaces in this program: The teacher interface and the student interface. In order to access either of these interfaces, an account must be made and logged into. Both types of accounts have their own features and permissions. In order to log in to one of these accounts, the user must first choose their role by clicking the ‘teacher’ button or the ‘student’ button. Then, the user will be prompted to either log in or create an account.

In order to log in, an account must be made first. To do this, click the ‘create an account’ button. After being prompted to enter a username and password, type in your desire username and password into their respective text boxes, then click the ‘create’ button and  he account will be created. From there, the user can click ‘log in’ and using their username and password that they inputted, will be given access to that account.

Once in an account the user will be given a specific menu respective of the type of account they are logged into. 

The student account main menu has four options, to delete their account, edit their username or password, to choose a course to work with., or logout. In order to choose a course, a teacher must create a course.

The teacher account main menu has six options, to delete their account, edit their username or password, edit a course, create a course, display course ID’s, or logout.

Whenever our program asks for a number, an integer input is required to access that corresponding menu.

Nathan Ehrenberger - Submitted Report on Brightspace
Parthiv Patel - Submitted Vocareum Workspace
Classes:

Server - The server for our program. Once the client connects to the server, our program will utilize the server to take in inputs from the GUI, and concurrently implement them into variables that are sent back into GUI for the user to interact with.

Client - The client for our Program. This method initiates the program by connecting to the server, checking to see if the connection is successful, then calling the FirstMenu GUI, which then branches off into every other GUI in our program. Testing was done by inputting valid and invalid options for each prompt in the program, and ensuring it works as intended and does not crash.

Account - The account class for our program. This class deals with account methods and data storage for both student and teacher accounts. The fields stored in each account are a username, password, and account number to further differentiate and identify the accounts. This class also includes methods to add features like changing the username and password, confirming an account login, deleting the account, and displaying the account details. Testing was done by testing two classes at a time, testing smaller components and making sure there was no errors, meshing the smaller components together to have a working program.

Student - This class is an extension of the account class. Its functionality is to store data specific to student accounts, such as courses enrolled and reading and writing to the file in which the student account data is stored. Furthermore, features in this class were created by writing methods to check for an entered course, and to display the courses that the student is enrolled in. Testing for this class was mainly done through the main method and the “StudentAccount.txt” file to ensure the reading and writing were in proper working order.

Teacher - This class is another extension of the account class. Its functionality is to store data specific to Teacher Accounts, such as courses created, quizzes created, reading and writing to the file in which the teacher account data is stored. Features for this class were created by writing methods to save quizzes and courses, and to display the teacher’s created curses. Testing for this class was mainly done through the main method and the “TeacherAccount.txt” file to ensure the reading and writing were in proper working order.

Course - The course class for our program. This class includes fields specific to each course that is created in the program, such as a course name, a course access code, and a list of quizzes created by the teacher in that course. It also includes methods for features having to do with courses, such as displaying courses, adding a course, deleting a course, and editing a course. Testing was done by testing two classes at a time, testing smaller components and making sure there was no errors, meshing the smaller components together to have a working program.

Quiz - This class includes fields for all of the components specific to quizzes, such as the quiz names, answer options, correct answers, and a list of quizzes. The methods in this class include all of the functionality to make a quiz, taking user input by steps to create questions, enter options, and enter the correct choice. Testing for this class was done through the main method and the “CourseQuizzes.txt” file, taking input and checking to see if the data was stored correctly and the correct output was given to the user

Question - This class mainly includes a randomization feature in which the quiz randomizes the order of the answer choices, in order to ensure that two different students taking the same quiz will not have the exact same quiz, to prevent cheating. Testing for this class was done using the main method and by creating two different student accounts to take the same quiz.

FirstMenu - The FirstMenu class for our Grademic Program, which displays a GUI for the initial menu of the program, where the user is prompted to choose either a 'teacher' or 'student' account. Within this menu are action listeners attatched to the buttons that call the next menu depending on which button the user clicks. For example, after choosing ‘teacher’ the user will then be taken to the TeacherGUI menu. Testing for this menu was done by going through each button/option of the GUI and ensuring that the intended output is given, and that the program does not crash if given certain inputs.

Login - The Login class for our Grademic Program, which displays a GUI for the login menu where users are prompted to enter a username and password. Within this menu are action listeners that call the function of its respective button such as confirming the login our exiting the menu.
Testing for this menu was done by going through each button/option of the GUI and ensuring that the intended output is given, and that the program does not crash if given certain inputs.

Student/TeacherGUI - The GUI that the user uses whenever they select ‘teacher’ or ‘student.’ This menu contains three buttons, a ‘log in’ ‘create an account’ and ‘back’ button. Each of these buttons contains an actionListener which will take the user either enter a username or password to log in, or to create a username or password, or to go back to the main menu. Testing for this menu was done by going through each button/option of the GUI and ensuring that the intended output is given, and that the program does not crash if given certain inputs.

StudentMainMenu - The StudentMainMenu class for our Grademic Program, displays a GUI for the student main menu, where four options displayed to the user: delete account, edit account, choose course to work with, or log out. Testing for this menu was done by going through each button/option of the GUI and ensuring that the intended output is given, and that the program does not crash if given certain inputs.

TeacherMainMenu - The TeacherMainMenu class for our Grademic Program, displays a GUI for the teacher main menu, where six options displayed to the user: delete account, edit account, edit a course, create a course, display course ID's, and log out. Testing for this menu was done by going through each button/option of the GUI and ensuring that the intended output is given, and that the program does not crash if given certain inputs.

CreateAccount - The CreateAccount class for our Grademic Program. Creates a GUI for function to create an account, where the user can type in their username and password, and their data will be sent to the server for storage. Testing for this menu was done by going through each button/option of the GUI and ensuring that the intended output is given, and that the program does not crash if given certain inputs.

CreateQuiz - The CreateQuiz class for our Grademic Program. Creates a GUI for function to create a quiz, asks the user to enter an integer for how many questions they want, and pulls up a table menu depending on that number, with the number of questions coupled with four options, that the user can select to choose the correct one for. Testing for this menu was done by going through each button/option of the GUI and ensuring that the intended output is given, and that the program does not crash if given certain inputs.

EditAccount- The EditAccount class for our Grademic Program. Creates a GUI for user (either student or teacher) to change their username or password, where they can type their new password or username into a text box, and the storage data is then set to that String. Testing for this menu was done by going through each button/option of the GUI and ensuring that the intended output is given, and that the program does not crash if given certain inputs.

EditCourse - The EditCourse class for our Grademic Program. Displays a GUI for the EditCourse function, a function that allows the user to create and manage quizzes for their respective course. They can also view student submissions of the course and their grades as well. This GUI includes action listeners that call the functions of their specific buttons. For example, ‘create a quiz’ button calls the CreateQuiz GUI. Testing for this menu was done by going through each button/option of the GUI and ensuring that the intended output is given, and that the program does not crash if given certain inputs.

EditQuiz - The EditQuiz class for our Grademic Program. Contains a GUI that the teacher can use to edit the contents of their quiz that they have created. Testing for this menu was done by going through each button/option of the GUI and ensuring that the intended output is given, and that the program does not crash if given certain inputs.
