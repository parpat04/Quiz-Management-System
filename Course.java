import java.util.ArrayList;
import java.io.*;

/**
 * Project 5 - Grademic - Course
 * <p>
 * The Course class for our Grademic Program,
 * deals with course methods and data storage for teacher courses
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 5, 2022
 */
public class Course {
    String name;
    int accessCode;
    ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
    String fileName = "Course.txt";

    public Course() {
    }

    public Course(String inputName, int accessCode) {
        this.name = inputName;
        this.accessCode = accessCode;
    }

    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }

    public String getName() {
        return name;
    }

    public int getAccessCode() {
        return accessCode;
    }

    public void createQuizSpace() throws FileNotFoundException {
        File f = new File("CourseQuizzes.txt");
        FileOutputStream fos = new FileOutputStream(f, true);
        PrintWriter pw = new PrintWriter(fos);
        String code = Integer.toString(accessCode);
        pw.println(code);
        pw.println("");
        pw.close();
    }

    public String displayQuizzes() {
        String quizList = "";
        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println(i + 1 + ". " + quizzes.get(i).getQuizName());
            if (i + 1 == quizzes.size()) {
                quizList += quizzes.get(i).getQuizName();
            } else {
                quizList += quizzes.get(i).getQuizName() + ",";
            }
        }
        return quizList;
    }

    public String displayQuizSubmissions(String courseName, int choose) throws FileNotFoundException {
        String fileName = "";
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < quizzes.size(); i++) {
            if (i == choose) {
                fileName = courseName + quizzes.get(i).getQuizName() + "Submissions.txt";
                System.out.println(fileName);
            }
        }
        File f = new File(fileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        try {
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < list.size(); i += 5) {
            System.out.println(i / 5 + 1 + ". " + list.get(i + 1) + " submission");
        }
        return fileName;
    }

    public void getSubmission(String fileName, int choose) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<String>();
        File f = new File(fileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        try {
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(list.get(choose * 5 + 1));
        System.out.println(list.get(choose * 5 + 2));
        System.out.println(list.get(choose * 5 + 3));
        System.out.println(list.get(choose * 5 + 4));
    }

    public String chooseQuizzes(int choose) {
        String quizName = "";
        for (int i = 0; i < quizzes.size(); i++) {
            if (i == choose) {
                quizName = quizzes.get(i).getQuizName();
            }
        }
        return quizName;
    }

    public void addQuizCourse(String quizName) {
        Quiz tempQuiz = new Quiz(quizName);
        tempQuiz.makeQuiz(quizName);
        quizzes.add(tempQuiz);
    }

    public void deleteQuiz(String quizName) {
        int delete = 0;
        File f = new File(quizName + ".txt");
        for (int i = 0; i < this.quizzes.size(); i++) {
            if (this.quizzes.get(i).getQuizName().equals(quizName)) {
                this.quizzes.remove(i);
                f.delete();
                System.out.println("The quiz has been deleted!");
                delete++;
            }

        }
        if (delete == 0) {
            System.out.println("Could not find that quiz!");
        }
    }
}
