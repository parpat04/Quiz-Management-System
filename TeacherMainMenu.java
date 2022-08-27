import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * Project 5 - Grademic - TeacherMainMenu
 * <p>
 * 
 
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */

public class TeacherMainMenu extends JComponent implements Runnable {
    private BufferedReader br;
    private PrintWriter pw;

    public TeacherMainMenu(BufferedReader br, PrintWriter pw) throws IOException {
        this.br = br;
        this.pw = pw;
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new TeacherMainMenu());
    // }

    public void run() {
        JFrame frame = new JFrame("Teacher Main Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        JButton deleteAccount = new JButton("Delete Account");
        JButton editAccount = new JButton("Edit Account");
        JButton editCourse = new JButton("Edit a Course");
        JButton createCourse = new JButton("Create a Course");
        JButton displayCourseID = new JButton("Display Course IDs");
        JButton logout = new JButton("Logout");
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBackground(Color.orange);
        frame.setSize(600, 400);
        panel.setSize(600, 400);
        panel.add(deleteAccount);
        panel.add(editAccount);
        panel.add(editCourse);
        panel.add(createCourse);
        panel.add(displayCourseID);
        panel.add(logout);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        deleteAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                // implement deleting account in server
                JOptionPane.showMessageDialog(null, "Account Deleted", "Delete Account",
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
                    editAccount = new EditAccount(br, pw, "teacher");
                    SwingUtilities.invokeLater(editAccount);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });

        editCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditCourse editCourse;
                String courseList = "";
                try {
                    pw.println("edit course");
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
                    boolean chooseCourseLoop;
                    do {
                        chooseCourseLoop = false;
                        String courseChoice = (String) JOptionPane.showInputDialog(null, "Select Course to Edit",
                                "Edit Course",
                                JOptionPane.PLAIN_MESSAGE, null, courseArray, null);
                        if (courseChoice == null || courseChoice.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please select a course or click 'ok'.", "Edit Course",
                                    JOptionPane.ERROR_MESSAGE);
                            chooseCourseLoop = true;
                        } else {
                            pw.println(courseChoice);
                            pw.flush();
                            if (!courseList.equals("")) {
                                editCourse = new EditCourse(br, pw);
                                SwingUtilities.invokeLater(editCourse);
                                frame.dispose();
                            }
                        }
                    } while (chooseCourseLoop);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        createCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String courseName = JOptionPane.showInputDialog(null, "What Would You Like to Name the Course?",
                        "Create Course",
                        JOptionPane.QUESTION_MESSAGE);
                if (courseName != null) {
                    Random rand = new Random();
                    int accessCode = Integer.parseInt(String.format("%04d", rand.nextInt(10000)));
                    // implement adding course in server
                    pw.println("create course");
                    pw.flush();
                    pw.println(courseName);
                    pw.flush();
                    pw.println(Integer.toString(accessCode));
                    pw.flush();
                    JOptionPane.showMessageDialog(null, "The access code for your course is " + accessCode,
                            "Create Course", JOptionPane.INFORMATION_MESSAGE);
                }
                // run();
                // frame.dispose();
            }
        });

        displayCourseID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    pw.println("display course IDs");
                    pw.flush();
                    String courseList = br.readLine();
                    JOptionPane.showMessageDialog(null, courseList.split(","), "Display Course ID's",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                // frame.dispose();
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