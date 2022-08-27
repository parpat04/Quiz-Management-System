import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Project 5 - Grademic - FirstMenu
 * <p>
 * The FirstMenu class for our Grademic Program
 * GUI for the initial menu of the program, where the user is
 * prompted to choose either a 'teacher' or 'student' account.
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */

public class FirstMenu extends JComponent implements Runnable {
    private BufferedReader br;
    private PrintWriter pw;

    public FirstMenu(BufferedReader br, PrintWriter pw) throws IOException {
        this.br = br;
        this.pw = pw;
    }
    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new FirstMenu());
    // }

    public void run() {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setBackground(Color.orange);
        JLabel option = new JLabel("Please select an option:");
        JButton teacher = new JButton("Teacher");
        JButton student = new JButton("Student");

        panel.add(option);
        panel.add(teacher);
        panel.add(student);
        frame.add(panel);

        frame.pack();
        frame.setVisible(true);

        teacher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TeacherGUI teacherGUI;
                try {
                    pw.println("teacher");
                    pw.flush();
                    teacherGUI = new TeacherGUI(br, pw);
                    SwingUtilities.invokeLater(teacherGUI);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });

        student.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StudentGUI studentGUI;
                try {
                    pw.println("student");
                    pw.flush();
                    studentGUI = new StudentGUI(br, pw);
                    SwingUtilities.invokeLater(studentGUI);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
    }
}
