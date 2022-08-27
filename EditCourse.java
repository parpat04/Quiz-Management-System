import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Project 5 - Grademic - EditCourse
 * <p>
 * The EditCourse class for our Grademic Program,
 * GUI for the EditCourse function, a function that allows
 * the user to manage quizzes and view submissions
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */

public class EditCourse extends JComponent implements Runnable {
    private BufferedReader br;
    private PrintWriter pw;

    public EditCourse(BufferedReader br, PrintWriter pw) throws IOException {
        this.br = br;
        this.pw = pw;
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new EditCourse());
    // }

    public void run() {
        JFrame frame = new JFrame("Edit Course");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        JButton createQuiz = new JButton("Create a Quiz");
        JButton editQuiz = new JButton("Edit a Quiz");
        JButton deleteQuiz = new JButton("Delete a Quiz");
        JButton importQuiz = new JButton("Import a Quiz");
        JButton displayQuizSubmissions = new JButton("Display Quiz Submissions");
        JButton back = new JButton("Back");
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBackground(Color.orange);
        frame.setSize(600, 400);
        panel.setSize(600, 400);
        panel.add(createQuiz);
        panel.add(editQuiz);
        panel.add(deleteQuiz);
        panel.add(importQuiz);
        panel.add(displayQuizSubmissions);
        panel.add(back);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        createQuiz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    pw.println("create quiz");
                    pw.flush();
                    CreateQuiz createQuiz = new CreateQuiz(br, pw);
                    SwingUtilities.invokeLater(createQuiz);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });

        editQuiz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    pw.println("edit quiz");
                    pw.flush();
                    String quizList = br.readLine();
                    String[] quizArray = quizList.split(",");
                    String quizChoice = (String) JOptionPane.showInputDialog(null, "Select Course to Edit",
                            "Edit Course",
                            JOptionPane.PLAIN_MESSAGE, null, quizArray, null);
                    pw.println(quizChoice);
                    pw.flush();
                    if (!quizList.equals("")) {
                        String quizContent = br.readLine();
                        System.out.println(quizContent);
                        EditQuiz editQuiz = new EditQuiz(br, pw, quizContent);
                        SwingUtilities.invokeLater(editQuiz);
                        frame.dispose();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        deleteQuiz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    pw.println("delete quiz");
                    pw.flush();
                    String quizList = br.readLine();
                    String[] quizArray = quizList.split(",");
                    String quizChoice = (String) JOptionPane.showInputDialog(null, "Select Course to Delete",
                            "Delete Course",
                            JOptionPane.PLAIN_MESSAGE, null, quizArray, null);
                    pw.println(quizChoice);
                    pw.flush();
                    if (!quizChoice.equals("")) {
                        JOptionPane.showMessageDialog(null, "Quiz Deleted!", "Delete a Quiz",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        importQuiz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pw.println("import quiz");
                pw.flush();
                String instructions = "The file must follow these guidelines:\n" +
                        "Quiz Name\n" +
                        "Randomization? (Yes/No)\n" +
                        "Question 1\n" +
                        "Choice 1\n" +
                        "Choice 2\n" +
                        "Choice 3\n" +
                        "Choice 4\n" +
                        "Correct Answer\n" +
                        "Repeat for more questions";
                File f;
                String filepath;
                do {
                    String enterFilepath = "Please enter the filepath of the file: ";
                    JOptionPane.showMessageDialog(null, instructions, "Import a Quiz", JOptionPane.INFORMATION_MESSAGE);

                    filepath = JOptionPane.showInputDialog(null, enterFilepath, "Import a Quiz",
                            JOptionPane.QUESTION_MESSAGE);

                    f = new File(filepath);

                    if (filepath == null || filepath.isEmpty() || !f.exists()) {
                        JOptionPane.showMessageDialog(null, "Filepath cannot be empty or invalid!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } while (filepath == null || filepath.isEmpty() || !f.exists());
                pw.println(filepath);
                pw.flush();
                JOptionPane.showMessageDialog(null, "Quiz Imported!", "Import a Quiz", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        displayQuizSubmissions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    pw.println("display quiz submission");
                    pw.flush();
                    String quizList = br.readLine();
                    String[] quizArray = quizList.split(",");
                    String quizChoice = (String) JOptionPane.showInputDialog(null, "Select Course to Edit",
                            "Edit Course",
                            JOptionPane.PLAIN_MESSAGE, null, quizArray, null);
                    pw.println(quizChoice);
                    pw.flush();
                    if (!quizList.equals("")) {
                        String quizSubmission = br.readLine();
                        if (!quizSubmission.equals("File not Found!")) {
                            JOptionPane.showMessageDialog(null, quizSubmission.split(","), "Display Course ID's",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TeacherMainMenu teacherMainMenu;
                try {
                    pw.println("back");
                    pw.flush();
                    teacherMainMenu = new TeacherMainMenu(br, pw);
                    SwingUtilities.invokeLater(teacherMainMenu);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();

            }
        });
    }
}
