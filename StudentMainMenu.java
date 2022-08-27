import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * Project 5 - Grademic - StudentMainMenu
 * <p>
 * The StudentMainMenu class for our Grademic Program,
 * GUI for the student main menu, where four options displayed to the user:
 * delete account, edit account, choose course to work with, or log out.
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */

public class StudentMainMenu extends JComponent implements Runnable {

    private BufferedReader br;
    private PrintWriter pw;

    public StudentMainMenu(BufferedReader br, PrintWriter pw) throws IOException {
        this.br = br;
        this.pw = pw;
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new StudentMainMenu());
    // }

    public void run() {
        JFrame frame = new JFrame("Student Main Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        JButton deleteAccount = new JButton("Delete Account");
        JButton editAccount = new JButton("Edit Account");
        JButton chooseCourse = new JButton("Choose a Course");
        JButton logout = new JButton("Logout");
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBackground(Color.orange);
        frame.setSize(600, 400);
        panel.setSize(600, 400);
        panel.add(deleteAccount);
        panel.add(editAccount);
        panel.add(chooseCourse);
        panel.add(logout);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        deleteAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // delete account
                JOptionPane.showMessageDialog(null, "Account Deleted!", "Delete Account",
                        JOptionPane.INFORMATION_MESSAGE);
                FirstMenu firstMenu;
                try {
                    pw.println("delete account");
                    pw.flush();
                    firstMenu = new FirstMenu(br, pw);
                    SwingUtilities.invokeLater(firstMenu);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });

        editAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditAccount editAccount;
                try {
                    pw.println("edit account");
                    pw.flush();
                    editAccount = new EditAccount(br, pw, "student");
                    SwingUtilities.invokeLater(editAccount);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });

        chooseCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TakeQuiz takeQuiz;
                String courseList = "";
                try {
                    pw.println("choose course");
                    pw.flush();
                    courseList = br.readLine();
                    ArrayList<String> courseArrayList = new ArrayList<String>();
                    String[] courseArray = courseList.split(",");
                    for (int i = 0; i < courseArray.length; i += 2) {
                        courseArrayList.add(courseArray[i]);
                    }
                    courseArray = new String[courseArrayList.size()];
                    for (int i = 0; i < courseArrayList.size(); i++) {
                        courseArray[i] = courseArrayList.get(i);
                    }
                    String courseChoice = (String) JOptionPane.showInputDialog(null, "Select Course to Take",
                            "Select Course",
                            JOptionPane.PLAIN_MESSAGE, null, courseArray, null);
                    pw.println(courseChoice);
                    pw.flush();
                    if (!courseList.equals("")) {
                        String quizList = br.readLine();
                        String[] quizArray = quizList.split(",");
                        String quizChoice = (String) JOptionPane.showInputDialog(null, "Select Quiz to Take",
                                "Select Quiz",
                                JOptionPane.PLAIN_MESSAGE, null, quizArray, null);
                        pw.println(quizChoice);
                        pw.flush();
                        if (!quizList.equals("")) {
                            String quizContent = br.readLine();
                            takeQuiz = new TakeQuiz(br, pw, quizContent);
                            SwingUtilities.invokeLater(takeQuiz);
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (!courseList.equals("")) {
                    frame.dispose();
                }
            }
        });

        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FirstMenu firstMenu;
                try {
                    pw.println("logout");
                    pw.flush();
                    firstMenu = new FirstMenu(br, pw);
                    SwingUtilities.invokeLater(firstMenu);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
    }
}