import java.util.*;
import java.io.*;
import java.util.Scanner;

/**
 * Project 5 - Grademic - Teacher
 * <p>
 * The Student class for our Grademic Program,
 * deals with Student methods, permissions, and data storage for teacher
 * accounts
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */
public class Teacher extends Account {
    String fileName = "TeacherAccount.txt";
    ArrayList<Course> courses = new ArrayList<>();
    private Course course;

    public Teacher() {
        super();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(String courseName) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(courseName)) {
                course = courses.get(i);
            }
        }
    }

    public void getSavedCourses() throws FileNotFoundException {
        ArrayList<String> list = readFile("TeacherCourses.txt");
        String[] courseList;
        String[] accessCode;
        for (int i = 0; i < list.size(); i += 3) {
            if (list.get(i).equals(Integer.toString(getAccountNum()))) {
                if (!list.get(i + 2).equals("")) {
                    courseList = list.get(i + 1).split(",");
                    accessCode = list.get(i + 2).split(",");
                    for (int j = 0; j < courseList.length; j++) {
                        courses.add(new Course(courseList[j], Integer.valueOf(accessCode[j])));
                    }
                    break;
                }
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getUsernameTeacher() {
        return username;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public boolean checkCourseEnter(String courseName) throws FileNotFoundException {
        boolean enter = false;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(courseName)) {
                course = courses.get(i);
                enter = true;
                break;
            }
        }
        return enter;
    }

    // public static ArrayList<String> readAccountFile(String fileName)
    // throws FileNotFoundException, NullPointerException {
    // File f = new File(fileName);
    // ArrayList<String> list = new ArrayList<>();
    // FileReader fr = new FileReader(f);
    // BufferedReader bfr = new BufferedReader(fr);
    // try {
    // String line = bfr.readLine();
    // while (line != null) {
    // list.add(line);
    // line = bfr.readLine();
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // } finally {
    // if (bfr != null) {
    // try {
    // bfr.close();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    // }
    // return list;
    // }

    public String displayAccessCodes() throws FileNotFoundException {
        ArrayList<String> list = readFile("TeacherCourses.txt");
        String courseList = "";
        String[] course;
        String[] accessCode;
        for (int i = 0; i < list.size(); i += 3) {
            if (!list.get(i + 2).equals("")) {
                if (list.get(i).equals(Integer.toString(getAccountNum()))) {
                    course = list.get(i + 1).split(",");
                    accessCode = list.get(i + 2).split(",");
                    for (int j = 0; j < course.length; j++) {
                        System.out.println(course[j] + ": " + accessCode[j]);
                        if (j + 1 == course.length) {
                            courseList += course[j] + "," + accessCode[j];
                        } else {
                            courseList += course[j] + "," + accessCode[j] + ",";
                        }
                    }
                    break;
                }
            }
        }
        return courseList;
    }

    // add quiz to specified course
    public void addQuiz(String courseName, String quizName) {
        int courseIndex = 0;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(courseName)) {
                courseIndex = i;
            }
        }
        courses.get(courseIndex).addQuizCourse(quizName);
    }

    public String courseNameToString() {
        String str = "";
        for (int i = 0; i < courses.size(); i++) {
            if (i + 1 == courses.size()) {
                str += courses.get(i).getName();
            } else {
                str += courses.get(i).getName();
                str += ",";
            }
        }
        return str;
    }

    public String courseCodeToString() {
        String str = "";
        for (int i = 0; i < courses.size(); i++) {
            if (i + 1 == courses.size()) {
                str += Integer.toString(courses.get(i).getAccessCode());
            } else {
                str += Integer.toString(courses.get(i).getAccessCode());
                str += ",";
            }
        }
        return str;
    }

    public void importQuiz() {
        Scanner scan = new Scanner(System.in);
        boolean error = false;
        ArrayList<String> importQuiz = new ArrayList<>();
        System.out.println(
                "The file must follow these guidelines:\nQuiz Name\nRandomization? (Yes/No)\nQuestion 1\nChoice 1\nChoice 2\nChoice 3\nChoice 4\nCorrect Answer\nRepeat for more questions");
        System.out.println("Please enter the filepath of the file: ");
        try {
            String fileimp = scan.nextLine();
            File file = new File(fileimp);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                importQuiz.add(line);
                line = br.readLine();
            }
            if (((importQuiz.size() - 2) % 6) != 0) {
                System.out.println("The size of the quiz is invalid!");
                error = true;
            }
            if (!importQuiz.get(1).equals("true") && !importQuiz.get(1).equals("false")) {
                System.out.println("The second line must have either true or false!");
                error = true;
            }
            if (!error) {
                for (int i = 3; i < importQuiz.size(); i += 6) {
                    if (!importQuiz.get(i).equals(importQuiz.get(i + 4))
                            && !importQuiz.get(i + 1).equals(importQuiz.get(i + 4))
                            && !importQuiz.get(i + 2).equals(importQuiz.get(i + 4))
                            && !importQuiz.get(i + 3).equals(importQuiz.get(i + 4))) {
                        System.out.println("Invalid options/choices for some of the questions!");
                        error = true;
                    }
                }
            }
            if (!error) {
                FileWriter writer = new FileWriter(importQuiz.get(0) + ".txt");
                for (String str : importQuiz) {
                    writer.write(str + "\n");
                }
                writer.flush();
                writer.close();
            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Invalid file!");
        }
        scan.close();

    }

    public void saveCourses() throws FileNotFoundException {
        ArrayList<String> list = readFile("TeacherCourses.txt");
        File f = new File("TeacherCourses.txt");
        FileOutputStream fos = new FileOutputStream(f, false);
        PrintWriter pw = new PrintWriter(fos);
        for (int i = 0; i < list.size(); i += 3) {
            if (list.get(i).equals(Integer.toString(getAccountNum()))) {
                list.set(i + 1, courseNameToString());
                list.set(i + 2, courseCodeToString());
                break;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            pw.println(list.get(i));
        }
        pw.close();
    }

    public String quizToString() {
        String str = "";
        for (int i = 0; i < course.getQuizzes().size(); i++) {
            if (i + 1 == course.getQuizzes().size()) {
                str += course.getQuizzes().get(i).getQuizName();
            } else {
                str += course.getQuizzes().get(i).getQuizName();
                str += ",";
            }
        }
        return str;
    }

    public void getSavedQuizzes() throws FileNotFoundException {
        ArrayList<String> list = readFile("CourseQuizzes.txt");
        String[] quizName;
        for (int i = 0; i < list.size(); i += 2) {
            if (list.get(i).equals(Integer.toString(course.getAccessCode()))) {
                if (!list.get(i + 1).equals("")) {
                    quizName = list.get(i + 1).split(",");
                    for (int j = 0; j < quizName.length; j++) {
                        course.getQuizzes().add(new Quiz(quizName[j]));
                    }
                    break;
                }
            }
        }
    }

    public void saveQuizzes() throws FileNotFoundException {
        ArrayList<String> list = readFile("CourseQuizzes.txt");
        File f = new File("CourseQuizzes.txt");
        FileOutputStream fos = new FileOutputStream(f, false);
        PrintWriter pw = new PrintWriter(fos);
        for (int i = 0; i < list.size(); i += 2) {
            if (list.get(i).equals(Integer.toString(course.getAccessCode()))) {
                list.set(i + 1, quizToString());
                break;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            pw.println(list.get(i));
        }
        pw.close();
    }
}