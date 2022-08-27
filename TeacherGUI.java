import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Project 5 - Grademic - TeacherGUI
 * <p>
 * The TeacherGUI class for our Grademic Program,
 * GUI for the menu following the user clicking the 'teacher' button: where
 * the user is prompted to login, create an account, or exit.
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */

public class TeacherGUI extends JComponent implements Runnable {
    private BufferedReader br;
    private PrintWriter pw;

    public TeacherGUI(BufferedReader br, PrintWriter pw) throws IOException {
        this.br = br;
        this.pw = pw;
    }
    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new TeacherGUI());
    // }

    public void run() {
        JFrame frame = new JFrame("Teacher");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        JButton login = new JButton("Log In");
        JButton createAccount = new JButton("Create an Account");
        JButton back = new JButton("Exit");
        // panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBackground(Color.orange);
        panel.add(login);
        panel.add(createAccount);
        panel.add(back);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login login;
                try {
                    pw.println("login");
                    pw.flush();
                    login = new Login(br, pw);
                    SwingUtilities.invokeLater(login);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });

        createAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateAccount createAccount;
                try {
                    pw.println("create");
                    pw.flush();
                    createAccount = new CreateAccount(br, pw);
                    SwingUtilities.invokeLater(createAccount);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FirstMenu firstMenu;
                try {
                    pw.println("back");
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