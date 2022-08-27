import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Project 5 - Grademic - CreateAccount
 * <p>
 * The CreateAccount class for our Grademic Program,
 * Creates a GUI for function to create an account.
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */

public class CreateAccount extends JComponent implements Runnable {
    private BufferedReader br;
    private PrintWriter pw;

    public CreateAccount(BufferedReader br, PrintWriter pw) throws IOException {
        this.br = br;
        this.pw = pw;
    }
    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new CreateAccount());
    // }

    public void run() {
        JFrame frame = new JFrame("Registration");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setBackground(Color.orange);
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userTextField = new JTextField(10);
        JTextField passTextField = new JTextField(10);
        JButton create = new JButton("Create");
        JButton exit = new JButton("Exit");

        panel.add(userLabel);
        panel.add(userTextField);
        panel.add(passLabel);
        panel.add(passTextField);
        panel.add(create, BOTTOM_ALIGNMENT);
        panel.add(exit, BOTTOM_ALIGNMENT);
        frame.add(panel);

        frame.pack();
        frame.setVisible(true);

        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                String password = passTextField.getText();
                String receive = "";
                pw.println("continue");
                pw.flush();
                pw.println(username);
                pw.flush();
                pw.println(password);
                pw.flush();
                try {
                    receive = br.readLine();
                    System.out.printf("Received from server:\n%s\n", receive);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (receive.equals("Create account successfully! (teacher)")) {
                    TeacherMainMenu tm;
                    try {
                        tm = new TeacherMainMenu(br, pw);
                        SwingUtilities.invokeLater(tm);
                    } catch (IOException w) {
                        JOptionPane.showMessageDialog(null, "Error", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    frame.dispose();
                } else if (receive.equals("Create account successfully! (student)")) {
                    StudentMainMenu sm;
                    try {
                        sm = new StudentMainMenu(br, pw);
                        SwingUtilities.invokeLater(sm);

                    } catch (IOException w) {
                        JOptionPane.showMessageDialog(null, "Error", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "This username has already existed!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FirstMenu firstMenu;
                try {
                    pw.println("exit");
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
