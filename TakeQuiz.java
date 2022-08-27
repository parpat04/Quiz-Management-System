import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

/**
 * Project 5 - Grademic - TakeQuiz
 * <p>
 * The T class for our Grademic Program,
 * GUI for the student main menu, where four options displayed to the user:
 * delete account, edit account, choose course to work with, or log out.
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */

public class TakeQuiz extends JComponent implements Runnable {
    private BufferedReader br;
    private PrintWriter pw;
    private String quizContent;
    String[] elements;

    // String quizContent = "Quiz Name,true,Question,Choice 1,Choice 2,Choice
    // 3,Choice 4,answer,Question 2,Choice 11,Choice 21,Choice 31,Choice
    // 41,answer1";

    public TakeQuiz(BufferedReader br, PrintWriter pw, String quizContent) throws IOException {
        this.br = br;
        this.pw = pw;
        this.quizContent = quizContent;
        elements = quizContent.split(",");
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new TakeQuiz());
    // }

    ArrayList<String> list = new ArrayList<String>();
    ArrayList<Question> imp = new ArrayList<>();
    String[] choiceOptions = new String[4];
    boolean randomChoices = false;
    int correct = 0;

    public void run() {
        list.addAll(Arrays.asList(elements));
        for (int i = 2; i < list.size(); i += 6) {
            for (int j = 1; j <= 4; j++) {
                choiceOptions[j - 1] = list.get(i + j);
            }
            if (list.get(1).equals("true")) {
                this.randomChoices = true;
            } else {
                this.randomChoices = false;
            }

            imp.add(new Question(list.get(i), choiceOptions, list.get(i + 5), this.randomChoices));
        }
        if (this.randomChoices) {
            Collections.shuffle(imp);
        }
        JFrame frame = new JFrame("Taking Quiz");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(imp.size(), 6));
        panel.setBackground(Color.orange);
        JRadioButton[] rad1 = new JRadioButton[imp.size()];
        JRadioButton[] rad2 = new JRadioButton[imp.size()];
        JRadioButton[] rad3 = new JRadioButton[imp.size()];
        JRadioButton[] rad4 = new JRadioButton[imp.size()];
        String[] importQuizFilePath = new String[imp.size()];

        ButtonGroup[] ch = new ButtonGroup[imp.size()];
        JLabel[] question = new JLabel[imp.size()];
        JButton[] importFile = new JButton[question.length];
        JPanel panelBot = new JPanel();
        JButton done = new JButton("Submit");

        for (int i = 0; i < question.length; i++) {

            question[i] = new JLabel(imp.get(i).getQuestion());
            int c = 0;
            rad1[i] = new JRadioButton(imp.get(i).getChoices().get(c));
            rad2[i] = new JRadioButton(imp.get(i).getChoices().get(c + 1));
            rad3[i] = new JRadioButton(imp.get(i).getChoices().get(c + 2));
            rad4[i] = new JRadioButton(imp.get(i).getChoices().get(c + 3));
            ch[i] = new ButtonGroup();
            importFile[i] = new JButton("Import File");
            ch[i].add(rad1[i]);
            ch[i].add(rad2[i]);
            ch[i].add(rad3[i]);
            ch[i].add(rad4[i]);
            ch[i].add(importFile[i]);
            rad1[i].setBackground(Color.orange);
            rad2[i].setBackground(Color.orange);
            rad3[i].setBackground(Color.orange);
            rad4[i].setBackground(Color.orange);

            panel.add(question[i]);
            panel.add(rad1[i]);
            panel.add(rad2[i]);
            panel.add(rad3[i]);
            panel.add(rad4[i]);
            panel.add(importFile[i]);
            frame.add(panel);
        }

        panelBot.add(done, BorderLayout.SOUTH);
        frame.add(panelBot, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

        for (int w = 0; w < imp.size(); w++) {

            int finalW = w;
            importFile[w].addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {

                            ch[finalW].clearSelection();

                            Enumeration<AbstractButton> enumeration = ch[finalW].getElements();
                            while (enumeration.hasMoreElements()) {
                                enumeration.nextElement().setEnabled(false);
                            }

                            File f;
                            do {
                                importQuizFilePath[finalW] = JOptionPane.showInputDialog(null,
                                        "What is the filepath of the file?",
                                        "Import File", JOptionPane.QUESTION_MESSAGE);

                                f = new File(importQuizFilePath[finalW]);
                                if ((importQuizFilePath[finalW] == null) || (importQuizFilePath[finalW].isEmpty())
                                        || !f.exists()) {
                                    JOptionPane.showMessageDialog(null, "Filepath cannot be empty or invalid!", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } while ((importQuizFilePath[finalW] == null) || (importQuizFilePath[finalW].isEmpty())
                                    || !f.exists());
                        }
                    });
        }
        done.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "Quiz Complete!", "Take a Quiz",
                                JOptionPane.INFORMATION_MESSAGE);
                        String str = "";
                        for (int q = 0; q < imp.size(); q++) {
                            if (rad1[q].isSelected()) {
                                str = str + (rad1[q].getText()) + ",";
                                if (imp.get(q).getAnswer().equals(rad1[q].getText())) {
                                    correct++;
                                }
                            } else if (rad2[q].isSelected()) {
                                str = str + (rad2[q].getText()) + ",";
                                if (imp.get(q).getAnswer().equals(rad2[q].getText())) {
                                    correct++;
                                }
                            } else if (rad3[q].isSelected()) {
                                str = str + (rad3[q].getText()) + ",";
                                if (imp.get(q).getAnswer().equals(rad3[q].getText())) {
                                    correct++;
                                }
                            } else if (rad4[q].isSelected()) {
                                str = str + (rad4[q].getText()) + ",";
                                if (imp.get(q).getAnswer().equals(rad4[q].getText())) {
                                    correct++;
                                }
                            } else {
                                str = str + importQuizFilePath[q] + ",";
                            }
                        }

                        str = str.replace("null", "unanswered");
                        double grade = ((double) correct / question.length) * 100.0;
                        DecimalFormat df = new DecimalFormat("#####0.00");
                        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
                        dfs.setDecimalSeparator('.');
                        df.setDecimalFormatSymbols(dfs);
                        // String gradefin = String.format("%.2f", grade);
                        String gradefin = df.format(grade);
                        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                        str = str + timeStamp + ",";
                        str = str + gradefin;

                        pw.println(str);
                        pw.flush();
                        StudentMainMenu studentMainMenu;
                        try {
                            String result = br.readLine();
                            JOptionPane.showMessageDialog(null, result, "Result", JOptionPane.INFORMATION_MESSAGE);
                            studentMainMenu = new StudentMainMenu(br, pw);
                            SwingUtilities.invokeLater(studentMainMenu);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        frame.dispose();

                    }
                });
    }
}