import java.util.*;
import java.io.*;

/**
 * Project 5 - Brightspace 2 - Student
 * <p>
 * The Student class for our Brightspace 2 Program,
 * deals with Student methods, permissions, and data storage for student
 * accounts
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version April 11, 2022
 */
public class Student extends Account {
    String fileName = "StudentAccount.txt";
    ArrayList<Course> courses = new ArrayList<>();
    private Course course;

    public Student() {
        super();
    }

    public Course getCourse() {
        return course;
    }

    public String getFileName() {
        return fileName;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourse(String courseName) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(courseName)) {
                course = courses.get(i);
            }
        }
    }

    public String showAllCourses() throws FileNotFoundException {
        ArrayList<String> list = readFile("TeacherCourses.txt");
        String courseList = "";
        String[] course;
        String[] accessCode;
        for (int i = 0; i < list.size(); i += 3) {
            if (!list.get(i + 2).equals("")) {
                course = list.get(i + 1).split(",");
                accessCode = list.get(i + 2).split(",");
                for (int j = 0; j < course.length; j++) {
                    System.out.println(course[j] + ": " + accessCode[j]);
                    courseList += course[j] + "," + accessCode[j] + ",";
                }
            }
        }
        return courseList.substring(0, courseList.length() - 1);
    }

    // public void displayCourses() throws FileNotFoundException {
    // ArrayList<String> list = readAccountFile("StudentCourses.txt");
    // String[] courseList;
    // String[] accessCode;
    // for (int i = 0; i < list.size(); i += 3) {
    // if (!list.get(i + 2).equals("")) {
    // courseList = list.get(i + 1).split(",");
    // accessCode = list.get(i + 2).split(",");
    // for (int j = 0; j < courseList.length; j++) {
    // System.out.println(courseList[j] + ": " + accessCode[j]);
    // }
    // }
    // }
    // }

    public boolean checkCourseEnter(String courseName, int courseAccessCode) throws FileNotFoundException {
        boolean enter = false;
        ArrayList<String> list = readFile("TeacherCourses.txt");
        String[] courseList;
        String[] accessCode;
        for (int i = 0; i < list.size(); i += 3) {
            if (!list.get(i + 2).equals("")) {
                courseList = list.get(i + 1).split(",");
                accessCode = list.get(i + 2).split(",");
                for (int j = 0; j < courseList.length; j++) {
                    if (courseList[j].equals(courseName) && accessCode[j].equals(Integer.toString(courseAccessCode))) {
                        course = new Course(courseName, courseAccessCode);
                        enter = true;
                        break;
                    }
                }
            }
        }
        return enter;
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

    // public void getSavedCourses() throws FileNotFoundException {
    // ArrayList<String> list = readAccountFile("StudentCourses.txt");
    // String[] courseList;
    // String[] accessCode;
    // for (int i = 0; i < list.size(); i += 3) {
    // if (list.get(i).equals(Integer.toString(getAccountNum()))) {
    // if (!list.get(i + 2).equals("")) {
    // courseList = list.get(i + 1).split(",");
    // accessCode = list.get(i + 2).split(",");
    // for (int j = 0; j < courseList.length; j++) {
    // courses.add(new Course(courseList[j], Integer.valueOf(accessCode[j])));
    // }
    // break;
    // }
    // }
    // }
    // }

    // public String courseNameToString() {
    // String str = "";
    // for (int i = 0; i < courses.size(); i++) {
    // if (i + 1 == courses.size()) {
    // str += courses.get(i).getName();
    // } else {
    // str += courses.get(i).getName();
    // str += ",";
    // }
    // }
    // return str;
    // }

    // public String courseCodeToString() {
    // String str = "";
    // for (int i = 0; i < courses.size(); i++) {
    // if (i + 1 == courses.size()) {
    // str += Integer.toString(courses.get(i).getAccessCode());
    // } else {
    // str += Integer.toString(courses.get(i).getAccessCode());
    // str += ",";
    // }
    // }
    // return str;
    // }

    // public void saveCourses() throws FileNotFoundException {
    // ArrayList<String> list = readAccountFile("StudentCourses.txt");
    // File f = new File("StudentCourses.txt");
    // FileOutputStream fos = new FileOutputStream(f, false);
    // PrintWriter pw = new PrintWriter(fos);
    // for (int i = 0; i < list.size(); i += 3) {
    // if (list.get(i).equals(Integer.toString(getAccountNum()))) {
    // list.set(i + 1, courseNameToString());
    // list.set(i + 2, courseCodeToString());
    // break;
    // }
    // }
    // for (int i = 0; i < list.size(); i++) {
    // pw.println(list.get(i));
    // }
    // pw.close();
    // }

    // public void takeQuiz(String quizName) {
    // tempquiz.takeQuiz(quizName);
    // }
}
