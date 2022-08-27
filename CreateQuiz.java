import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

/**
 * Project 5 - Grademic - CreateQuiz
 * <p>
 * The CreateQuiz class for our Grademic Program,
 * Creates a GUI for function to create a quiz
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 * CS 18000
 * @version May 2, 2022
 */

public class CreateQuiz extends JComponent implements Runnable {
    private BufferedReader br;
    private PrintWriter pw;

    public CreateQuiz(BufferedReader br, PrintWriter pw) throws IOException {
        this.br = br;
        this.pw = pw;
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new CreateQuiz());
    // }

    public void run() {
        boolean numOfQLoop;
        JRadioButton[] rad1;
        JRadioButton[] rad2;
        JRadioButton[] rad3;
        JRadioButton[] rad4;
        JTextField[] opt1;
        JTextField[] opt2;
        JTextField[] opt3;
        JTextField[] opt4;
        ButtonGroup[] bg;
        JTextField[] question;
        int num = 0;

        do {
            numOfQLoop = false;
            String numofq = JOptionPane.showInputDialog(null, "How many questions will the quiz have?",
                    "Number of Questions", JOptionPane.QUESTION_MESSAGE);
            try {
                num = Integer.parseInt(numofq);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Enter an Integer!", "Create Quiz", JOptionPane.ERROR_MESSAGE);
                numOfQLoop = true;
            }

            if (num <= 0) {
                JOptionPane.showMessageDialog(null, "Enter a number greater than 0", "Create Quiz",
                        JOptionPane.ERROR_MESSAGE);
                numOfQLoop = true;
            }
        } while (numOfQLoop);

        rad1 = new JRadioButton[num];
        rad2 = new JRadioButton[num];
        rad3 = new JRadioButton[num];
        rad4 = new JRadioButton[num];
        opt1 = new JTextField[num];
        opt2 = new JTextField[num];
        opt3 = new JTextField[num];
        opt4 = new JTextField[num];
        bg = new ButtonGroup[num];
        question = new JTextField[num];
        JFrame frame = new JFrame("Quiz");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel topPanel = new JPanel();
        JPanel panel = new JPanel(new GridLayout(num + 1, 9));
        panel.setBackground(Color.orange);
        topPanel.setBackground(Color.orange);

        JLabel label1 = new JLabel("Enter the quiz name:");
        JTextField quizName = new JTextField(10);
        JLabel instruction = new JLabel("Please select the radio button corresponding to the correct option.");
        JButton done = new JButton("Done");
        JLabel label2 = new JLabel("Please select if you would like to randomize the choices and questions:");
        JCheckBox randomize = new JCheckBox();

        topPanel.add(instruction);
        topPanel.add(label1);
        topPanel.add(quizName);
        topPanel.add(label2);
        topPanel.add(randomize);
        topPanel.add(done);
        frame.add(topPanel, BorderLayout.NORTH);

        JLabel q = new JLabel("Question:");
        JLabel c1 = new JLabel("Choice 1:");
        JLabel c2 = new JLabel("Choice 2:");
        JLabel c3 = new JLabel("Choice 3:");
        JLabel c4 = new JLabel("Choice 4:");
        JLabel blank = new JLabel("");
        JLabel blank1 = new JLabel("");
        JLabel blank2 = new JLabel("");
        JLabel blank3 = new JLabel("");

        panel.add(q);
        panel.add(c1);
        panel.add(blank);
        panel.add(c2);
        panel.add(blank1);
        panel.add(c3);
        panel.add(blank2);
        panel.add(c4);
        panel.add(blank3);

        for (int i = 0; i < num; i++) {
            question[i] = new JTextField(10);
            opt1[i] = new JTextField(10);
            opt2[i] = new JTextField(10);
            opt3[i] = new JTextField(10);
            opt4[i] = new JTextField(10);
            rad1[i] = new JRadioButton("");
            rad2[i] = new JRadioButton("");
            rad3[i] = new JRadioButton("");
            rad4[i] = new JRadioButton("");

            bg[i] = new ButtonGroup();
            bg[i].add(rad1[i]);
            bg[i].add(rad2[i]);
            bg[i].add(rad3[i]);
            bg[i].add(rad4[i]);

            panel.add(question[i]);
            panel.add(rad1[i]);
            panel.add(opt1[i]);
            panel.add(rad2[i]);
            panel.add(opt2[i]);
            panel.add(rad3[i]);
            panel.add(opt3[i]);
            panel.add(rad4[i]);
            panel.add(opt4[i]);

        }

        frame.add(panel);

        frame.pack();
        frame.setVisible(true);

        done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                boolean error = false;

                for (int b = 0; b < question.length; b++) {
                    if (opt1[b].getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Please enter text in every option text box!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        error = true;

                    } else if (opt2[b].getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Please enter text in every option text box!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        error = true;

                    } else if (opt3[b].getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Please enter text in every option text box!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        error = true;

                    } else if (opt4[b].getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Please enter text in every option text box!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        error = true;

                    }
                    if (bg[b].getSelection() == null) {
                        JOptionPane.showMessageDialog(null, "Please select an answer for every question!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        error = true;
                    }
                    if (question[b].getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Please enter text in every question text box!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        error = true;
                    }
                }

                if (quizName.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter a quiz name!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    error = true;
                }

                if (!error) {
                    JOptionPane.showMessageDialog(null, "Quiz Created!", "Create a Quiz",
                            JOptionPane.INFORMATION_MESSAGE);
                    ArrayList<String> questions = new ArrayList<String>();
                    String quizContent = "";
                    quizContent += quizName.getText() + ",";
                    if (randomize.isSelected()) {
                        quizContent += "true" + ",";
                    } else {
                        quizContent += "false" + ",";
                    }
                    for (int q = 0; q < rad1.length; q++) {
                        questions.add(question[q].getText());
                        questions.add(opt1[q].getText());
                        questions.add(opt2[q].getText());
                        questions.add(opt3[q].getText());
                        questions.add(opt4[q].getText());

                        if (rad1[q].isSelected()) {
                            questions.add(opt1[q].getText());
                        } else if (rad2[q].isSelected()) {
                            questions.add(opt2[q].getText());
                        } else if (rad3[q].isSelected()) {
                            questions.add(opt3[q].getText());
                        } else if (rad4[q].isSelected()) {
                            questions.add(opt4[q].getText());
                        }
                    }

                    for (int w = 0; w < questions.size() - 1; w++) {
                        quizContent += questions.get(w) + ",";
                    }
                    quizContent += questions.get(questions.size() - 1);
                    pw.println(quizContent);
                    pw.flush();
                    EditCourse editCourse;
                    try {
                        editCourse = new EditCourse(br, pw);
                        SwingUtilities.invokeLater(editCourse);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    frame.dispose();
                }
            }
        });
    }
}