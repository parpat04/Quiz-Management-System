import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Project 5 - Grademic - EditAccount
 * <p>
 * The EditAccount class for our Grademic Program,
 * GUI for user to change their username or password
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */

public class EditAccount extends JComponent implements Runnable {
    private BufferedReader br;
    private PrintWriter pw;
    private String role;

    public EditAccount(BufferedReader br, PrintWriter pw, String role) throws IOException {
        this.br = br;
        this.pw = pw;
        this.role = role;
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new EditAccount());
    // }

    public void run() {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setBackground(Color.orange);
        JLabel option = new JLabel("Please select an option:");
        JButton changeuser = new JButton("Change username");
        JButton changepass = new JButton("Change Password");
        JButton back = new JButton("Back");

        panel.add(option);
        panel.add(changeuser);
        panel.add(changepass);
        panel.add(back);
        frame.add(panel);

        frame.pack();
        frame.setVisible(true);

        changeuser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newUsername = JOptionPane.showInputDialog(null,
                        "What Would You Like to Change Your Username to?", "Edit Account",
                        JOptionPane.QUESTION_MESSAGE);
                if (newUsername != null) {
                    // implement change in server
                    pw.println("change username");
                    pw.flush();
                    pw.println(newUsername);
                    pw.flush();
                    JOptionPane.showMessageDialog(null, "Username Changed!", "Edit Account",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        changepass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newPassword = JOptionPane.showInputDialog(null,
                        "What Would You Like to Change Your Password to?", "Edit Account",
                        JOptionPane.QUESTION_MESSAGE);
                if (newPassword != null) {
                    // implement change in server
                    pw.println("change password");
                    pw.flush();
                    pw.println(newPassword);
                    pw.flush();
                    JOptionPane.showMessageDialog(null, "Password Changed!", "Edit Account",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // needs differentiation between going back to student or teacher menu.
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (role.equals("student")) {
                    StudentMainMenu studentMainMenu;
                    try {
                        pw.println("exit");
                        pw.flush();
                        studentMainMenu = new StudentMainMenu(br, pw);
                        SwingUtilities.invokeLater(studentMainMenu);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    frame.dispose();
                } else if (role.equals("teacher")) {
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
            }
        });
    }
}
