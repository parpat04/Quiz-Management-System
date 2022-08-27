import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

/**
 * Project 5 - Grademic - EditQuiz
 * <p>
 * The EditQuiz class for our Grademic Program,
 * The edit quiz functions for the EditQuiz Menu
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */

public class EditQuiz extends JComponent implements Runnable {
    private BufferedReader br;
    private PrintWriter pw;
    private String quizContent;
    String[] elements;
    int numofq;

    public EditQuiz(BufferedReader br, PrintWriter pw, String quizContent) throws IOException {
        this.br = br;
        this.pw = pw;
        this.quizContent = quizContent;
        elements = quizContent.split(",");
        numofq = (elements.length - 2) / 6;
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new EditQuiz());
    // }

    // String quiz = "Quiz Name,true,Question,Choice 1,Choice 2,Choice 3,Choice
    // 4,answer,Question 2,Choice 11,Choice 21,Choice 31,Choice 41,answer1";

    ArrayList<String> list = new ArrayList<>();
    ArrayList<Question> imp = new ArrayList<>();
    String[] choiceOptions = new String[4];
    boolean randomChoices = false;

    public void run() {
        list.addAll(Arrays.asList(elements));

        for (int i = 2; i < list.size(); i += 6) {
            for (int j = 1; j <= 4; j++) {
                choiceOptions[j - 1] = list.get(i + j);
            }

            imp.add(new Question(list.get(i), choiceOptions, list.get(i + 5), this.randomChoices));
        }

        JRadioButton[] rad1 = new JRadioButton[numofq];
        JRadioButton[] rad2 = new JRadioButton[numofq];
        JRadioButton[] rad3 = new JRadioButton[numofq];
        JRadioButton[] rad4 = new JRadioButton[numofq];
        JTextField[] opt1 = new JTextField[numofq];
        JTextField[] opt2 = new JTextField[numofq];
        JTextField[] opt3 = new JTextField[numofq];
        JTextField[] opt4 = new JTextField[numofq];
        ButtonGroup[] bg = new ButtonGroup[numofq];
        JTextField[] question = new JTextField[numofq];

        JFrame frame = new JFrame("Edit Quiz");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel topPanel = new JPanel();
        JPanel panel = new JPanel(new GridLayout(numofq + 1, 9));
        panel.setBackground(Color.orange);
        topPanel.setBackground(Color.orange);

        JLabel label1 = new JLabel("Enter the quiz name:");
        JTextField quizName = new JTextField(10);
        quizName.setText(list.get(0));
        JLabel instruction = new JLabel("Please select the radio button corresponding to the correct option.");
        JButton done = new JButton("Done");
        JLabel label2 = new JLabel("Please select if you would like to randomize the choices and questions:");
        JCheckBox randomize = new JCheckBox();
        if (list.get(1).equals("true")) {
            randomize.setSelected(true);
        }

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

        for (int i = 0; i < numofq; i++) {
            question[i] = new JTextField(10);
            question[i].setText(imp.get(i).getQuestion());
            opt1[i] = new JTextField(10);
            opt1[i].setText(imp.get(i).getChoices().get(0));
            opt2[i] = new JTextField(10);
            opt2[i].setText(imp.get(i).getChoices().get(1));
            opt3[i] = new JTextField(10);
            opt3[i].setText(imp.get(i).getChoices().get(2));
            opt4[i] = new JTextField(10);
            opt4[i].setText(imp.get(i).getChoices().get(3));
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

                    ArrayList<String> questions = new ArrayList<String>();
                    String send = "";
                    questions.add(quizName.getText());
                    if (randomize.isSelected()) {
                        questions.add("true");
                    } else {
                        questions.add("false");
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

                        send = send + questions.get(w) + ",";
                    }
                    send = send + questions.get(questions.size() - 1);
                    pw.println(send);
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