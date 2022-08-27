import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Runnable {
    Socket socket;
    Teacher teacher = new Teacher();
    Student student = new Student();
    boolean regis = false;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.printf("connection received from %s\n", socket);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());

            while (true) {
                String role = br.readLine();
                System.out.printf("Role: %s\n", role);
                if (role.equals("teacher")) {
                    String regisOption = br.readLine();
                    System.out.printf("Regis option: %s\n", regisOption);
                    if (regisOption.equals("login")) {
                        boolean logIn = false;
                        while (logIn == false) {
                            String exit = br.readLine();
                            System.out.printf("Exit: %s\n", exit);
                            if (exit.equals("exit")) {
                                break;
                            }
                            String username = br.readLine();
                            System.out.printf("Username: %s\n", username);
                            String password = br.readLine();
                            System.out.printf("Password: %s\n", password);
                            try {
                                regis = teacher.confirmLogIn(username, password, "TeacherAccount.txt");
                            } catch (FileNotFoundException e) {
                                System.out.println("File not Found!");
                            }
                            if (regis) {
                                logIn = true;
                                teacher.getSavedCourses();
                                pw.println("Log in successfully! (teacher)");
                            } else {
                                pw.println("Log in Fail! (teacher)");
                            }
                            pw.flush();
                        }
                    } else if (regisOption.equals("create")) {
                        boolean createAccount = false;
                        while (createAccount == false) {
                            String exit = br.readLine();
                            System.out.printf("Exit: %s\n", exit);
                            if (exit.equals("exit")) {
                                break;
                            }
                            String username = br.readLine();
                            System.out.printf("Username: %s\n", username);
                            String password = br.readLine();
                            System.out.printf("Password: %s\n", password);
                            try {
                                regis = teacher.confirmCreateAccount(username, password, "TeacherAccount.txt");
                            } catch (FileNotFoundException e) {
                                System.out.println("File not Found!");
                            }
                            if (regis) {
                                createAccount = true;
                                teacher.createCourseSpace("TeacherCourses.txt");
                                pw.println("Create account successfully! (teacher)");
                            } else {
                                pw.println("Create account Fail! (teacher)");
                            }
                            pw.flush();
                        }
                    }
                    System.out.printf("Account number: %d\n", teacher.getAccountNum());
                    boolean mainMenuExit = false;
                    while (mainMenuExit == false) {
                        String teacherOption = br.readLine();
                        System.out.printf("Teacher option: %s\n", teacherOption);
                        if (teacherOption.equals("delete account")) {
                            teacher.delete("TeacherAccount.txt");
                            teacher.delete("TeacherCourses.txt");
                            mainMenuExit = true;
                        } else if (teacherOption.equals("edit account")) {
                            boolean editAccountExit = false;
                            while (editAccountExit == false) {
                                String editAccountOption = br.readLine();
                                System.out.printf("Edit account option: %s\n", editAccountOption);
                                if (editAccountOption.equals("change username")) {
                                    String newUsername = br.readLine();
                                    System.out.printf("New username: %s\n", newUsername);
                                    teacher.changeUsername(newUsername, "TeacherAccount.txt");
                                } else if (editAccountOption.equals("change password")) {
                                    String newPassword = br.readLine();
                                    System.out.printf("New password: %s\n", newPassword);
                                    teacher.changePassword(newPassword, "TeacherAccount.txt");
                                } else {
                                    editAccountExit = true;
                                }
                            }
                        } else if (teacherOption.equals("edit course")) {
                            String courseList = teacher.displayAccessCodes();
                            pw.println(courseList);
                            pw.flush();
                            String courseChoice = br.readLine();
                            System.out.printf("Course choice: %s\n", courseChoice);
                            if (!courseChoice.equals("")) {
                                teacher.setCourse(courseChoice);
                                teacher.getSavedQuizzes();
                                boolean editCourseExit = false;
                                while (editCourseExit == false) {
                                    String editCourseOption = br.readLine();
                                    System.out.printf("Edit course option: %s\n", editCourseOption);
                                    if (editCourseOption.equals("create quiz")) {
                                        String quizContent = br.readLine();
                                        String[] quizContentArray = quizContent.split(",");
                                        Quiz quiz = new Quiz(quizContentArray[0]);
                                        teacher.getCourse().getQuizzes().add(quiz);
                                        teacher.saveQuizzes();
                                        File f = new File(quizContentArray[0] + ".txt");
                                        FileOutputStream fos = new FileOutputStream(f, true);
                                        PrintWriter pwquiz = new PrintWriter(fos);
                                        for (int i = 0; i < quizContentArray.length; i++) {
                                            pwquiz.println(quizContentArray[i]);
                                            pwquiz.flush();
                                        }
                                        pwquiz.close();
                                    } else if (editCourseOption.equals("edit quiz")) {
                                        String quizList = teacher.getCourse().displayQuizzes();
                                        pw.println(quizList);
                                        pw.flush();
                                        String quizChoice = br.readLine();
                                        System.out.printf("Quiz choice: %s\n", quizChoice);
                                        ArrayList<String> list = Teacher.readFile(quizChoice + ".txt");
                                        String quizContent = "";
                                        for (int i = 0; i < list.size(); i++) {
                                            if (i + 1 == list.size()) {
                                                quizContent += list.get(i);
                                            } else {
                                                quizContent += list.get(i) + ",";
                                            }
                                        }
                                        System.out.println(quizContent);
                                        pw.println(quizContent);
                                        pw.flush();
                                        quizContent = br.readLine();
                                        String[] quizContentArray = quizContent.split(",");
                                        Quiz quiz = new Quiz(quizContentArray[0]);
                                        teacher.getCourse().getQuizzes().add(quiz);
                                        teacher.getCourse().deleteQuiz(quizChoice);
                                        teacher.saveQuizzes();
                                        File f = new File(quizContentArray[0] + ".txt");
                                        FileOutputStream fos = new FileOutputStream(f, true);
                                        PrintWriter pwquiz = new PrintWriter(fos);
                                        for (int i = 0; i < quizContentArray.length; i++) {
                                            pwquiz.println(quizContentArray[i]);
                                            pwquiz.flush();
                                        }
                                        pwquiz.close();
                                    } else if (editCourseOption.equals("delete quiz")) {
                                        String quizList = teacher.getCourse().displayQuizzes();
                                        pw.println(quizList);
                                        pw.flush();
                                        String quizChoice = br.readLine();
                                        if (!quizChoice.equals("")) {
                                            System.out.printf("Quiz choice: %s\n", quizChoice);
                                            teacher.getCourse().deleteQuiz(quizChoice);
                                            teacher.saveQuizzes();
                                        }
                                    } else if (editCourseOption.equals("import quiz")) {
                                        String filepath = br.readLine();
                                        if (!filepath.equals("")) {
                                            try {
                                                System.out.printf("Filepath: %s\n", filepath);
                                                ArrayList<String> list = Teacher.readFile(filepath);
                                                Quiz quiz = new Quiz(list.get(0));
                                                teacher.getCourse().getQuizzes().add(quiz);
                                                teacher.saveQuizzes();
                                                FileWriter writer = new FileWriter(list.get(0) + ".txt");
                                                for (String str : list) {
                                                    writer.write(str + "\n");
                                                }
                                                writer.flush();
                                                writer.close();
                                            } catch (FileNotFoundException e) {
                                                System.out.println("File not Found!");
                                            }
                                        }
                                    } else if (editCourseOption.equals("display quiz submission")) {
                                        String quizList = teacher.getCourse().displayQuizzes();
                                        pw.println(quizList);
                                        pw.flush();
                                        String quizChoice = br.readLine();
                                        System.out.printf("Quiz choice: %s\n", quizChoice);
                                        if (!quizChoice.equals("")) {
                                            try {
                                                ArrayList<String> quizSubmissionArray = Teacher.readFile(
                                                        teacher.getCourse().getName() + quizChoice + "Submission.txt");
                                                String quizSubmission = "";
                                                for (int i = 0; i < quizSubmissionArray.size(); i++) {
                                                    if (i + 1 == quizSubmissionArray.size()) {
                                                        quizSubmission += quizSubmissionArray.get(i);
                                                    } else {
                                                        quizSubmission += quizSubmissionArray.get(i) + ",";
                                                    }
                                                }
                                                pw.println(quizSubmission);
                                                pw.flush();
                                            } catch (FileNotFoundException e) {
                                                System.out.println("File not Found!");
                                                pw.println("File not Found!");
                                                pw.flush();
                                            }
                                        }
                                    } else if (editCourseOption.equals("back")) {
                                        teacher.getCourse().getQuizzes().clear();
                                        editCourseExit = true;
                                    }
                                }
                            }
                        } else if (teacherOption.equals("create course")) {
                            String courseName = br.readLine();
                            System.out.printf("Course name: %s\n", courseName);
                            String accessCode = br.readLine();
                            System.out.printf("Access code: %s\n", accessCode);
                            Course newCourse = new Course(courseName, Integer.valueOf(accessCode));
                            teacher.getCourses().add(newCourse);
                            try {
                                teacher.saveCourses();
                                newCourse.createQuizSpace();
                            } catch (FileNotFoundException e) {
                                System.out.println("File not Found!");
                            }
                        } else if (teacherOption.equals("display course IDs")) {
                            String courseList = teacher.displayAccessCodes();
                            pw.println(courseList);
                            pw.flush();
                        } else if (teacherOption.equals("logout")) {
                            mainMenuExit = true;
                        }
                    }

                } else if (role.equals("student")) {
                    String regisOption = br.readLine();
                    System.out.printf("Regis option: %s\n", regisOption);
                    if (regisOption.equals("login")) {
                        boolean logIn = false;
                        while (logIn == false) {
                            String exit = br.readLine();
                            System.out.printf("Exit: %s\n", exit);
                            if (exit.equals("exit")) {
                                break;
                            }
                            String username = br.readLine();
                            System.out.printf("Username: %s\n", username);
                            String password = br.readLine();
                            System.out.printf("Password: %s\n", password);
                            try {
                                regis = student.confirmLogIn(username, password, "StudentAccount.txt");
                            } catch (FileNotFoundException e) {
                                System.out.println("File not Found!");
                            }
                            if (regis) {
                                logIn = true;
                                pw.println("Log in successfully! (student)");
                            } else {
                                pw.println("Log in Fail! (student)");
                            }
                            pw.flush();
                        }
                    } else if (regisOption.equals("create")) {
                        boolean createAccount = false;
                        while (createAccount == false) {
                            String exit = br.readLine();
                            System.out.printf("Exit: %s\n", exit);
                            if (exit.equals("exit")) {
                                break;
                            }
                            String username = br.readLine();
                            System.out.printf("Username: %s\n", username);
                            String password = br.readLine();
                            System.out.printf("Password: %s\n", password);
                            try {
                                regis = student.confirmCreateAccount(username, password, "StudentAccount.txt");
                            } catch (FileNotFoundException e) {
                                System.out.println("File not Found!");
                            }
                            if (regis) {
                                createAccount = true;
                                pw.println("Create account successfully! (student)");
                            } else {
                                pw.println("Create account Fail! (student)");
                            }
                            pw.flush();
                        }
                    }
                    System.out.printf("Account number: %d\n", student.getAccountNum());
                    boolean mainMenuExit = false;
                    while (mainMenuExit == false) {
                        String studentOption = br.readLine();
                        System.out.printf("Student option: %s\n", studentOption);
                        if (studentOption.equals("delete account")) {
                            student.delete("StudentAccount.txt");
                        } else if (studentOption.equals("edit account")) {
                            boolean editAccountExit = false;
                            while (editAccountExit == false) {
                                String editAccountOption = br.readLine();
                                System.out.printf("Edit account option: %s\n", editAccountOption);
                                if (editAccountOption.equals("change username")) {
                                    String newUsername = br.readLine();
                                    System.out.printf("New username: %s\n", newUsername);
                                    student.changeUsername(newUsername, "StudentAccount.txt");
                                } else if (editAccountOption.equals("change password")) {
                                    String newPassword = br.readLine();
                                    System.out.printf("New password: %s\n", newPassword);
                                    student.changePassword(newPassword, "StudentAccount.txt");
                                } else {
                                    editAccountExit = true;
                                }
                            }
                        } else if (studentOption.equals("choose course")) {
                            String courseList = student.showAllCourses();
                            System.out.println(courseList);
                            pw.println(courseList);
                            pw.flush();
                            String courseChoice = br.readLine();
                            System.out.printf("Course choice: %s\n", courseChoice);
                            if (!courseChoice.equals("")) {
                                String accessCode = "";
                                for (int i = 0; i < courseList.split(",").length; i += 2) {
                                    if (courseChoice.equals(courseList.split(",")[i])) {
                                        accessCode = courseList.split(",")[i + 1];
                                    }
                                }
                                student.getCourses().add(new Course(courseChoice, Integer.valueOf(accessCode)));
                                student.setCourse(courseChoice);
                                student.getSavedQuizzes();
                                String quizList = student.getCourse().displayQuizzes();
                                pw.println(quizList);
                                pw.flush();
                                String quizChoice = br.readLine();
                                System.out.printf("Quiz choice: %s\n", quizChoice);
                                if (!quizChoice.equals("")) {
                                    ArrayList<String> list = Student.readFile(quizChoice + ".txt");
                                    String quizContent = "";
                                    for (int i = 0; i < list.size(); i++) {
                                        if (i + 1 == list.size()) {
                                            quizContent += list.get(i);
                                        } else {
                                            quizContent += list.get(i) + ",";
                                        }
                                    }
                                    System.out.println(quizContent);
                                    pw.println(quizContent);
                                    pw.flush();
                                    String studentSubmission = br.readLine();
                                    File f = new File(courseChoice + quizChoice + "Submission" + ".txt");
                                    FileOutputStream fos = new FileOutputStream(f, true);
                                    PrintWriter pwquiz = new PrintWriter(fos);
                                    pwquiz.println(student.getUsername());
                                    pwquiz.flush();
                                    String[] studentSubmissionArray = studentSubmission.split(",");
                                    for (int i = 0; i < studentSubmissionArray.length; i++) {
                                        pwquiz.println(studentSubmissionArray[i]);
                                        pwquiz.flush();
                                        if (i + 1 == studentSubmissionArray.length) {
                                            pw.println(studentSubmissionArray[i]);
                                            pw.flush();
                                        }
                                    }
                                    pwquiz.close();
                                }
                            }
                        } else if (studentOption.equals("logout")) {
                            mainMenuExit = true;
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.printf("socket open, waiting for connections on %s\n",
                serverSocket);
        // infinite server loop: accept connection,
        // spawn thread to handle...
        while (true) {
            Socket socket = serverSocket.accept();
            Server server = new Server(socket);
            new Thread(server).start();
        }
    }
}
