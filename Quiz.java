import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Project 5 - Brightspace 2 - Quiz
 * <p>
 * The Quiz class for our Brightspace 2 Program,
 * deals with Quiz methods, features, and data storage for Quizzes
 *
 * @authors Nathan Ehrenberger Purdue CS 18000
 * @version May 2, 2022
 */

public class Quiz {
    Scanner scan = new Scanner(System.in);
    private String quizName;
    private String teacherInput;
    private String[] choicesArray;
    private String options;
    private String answer;
    private boolean randomChoices;
    private String askRandomQuestions;
    private String teacherEdit;
    private String teacherQuestion;
    private String currentQuestion;
    private boolean match;
    private boolean delete;
    private int correct;

    public Quiz(String quizName) {
        this.quizName = quizName;
        this.match = false;
        this.delete = false;

    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public void makeQuiz(String quizName) {
        try {
            FileWriter writer = new FileWriter(quizName + ".txt");
            writer.write(quizName + "\n");

            do {

                System.out.println(
                        "Would you like to randomize the questions and choices for the students? Enter yes or no.");
                this.askRandomQuestions = scan.nextLine();
                if (!this.askRandomQuestions.equalsIgnoreCase("Yes") &&
                        !this.askRandomQuestions.equalsIgnoreCase("No")) {
                    System.out.println("You must enter Yes or No!");
                }
                if (this.askRandomQuestions.equalsIgnoreCase("yes")) {
                    writer.write("true" + "\n");
                } else if (this.askRandomQuestions.equalsIgnoreCase("no")) {
                    writer.write("false" + "\n");
                }

            } while (!this.askRandomQuestions.equalsIgnoreCase("Yes") &&
                    !this.askRandomQuestions.equalsIgnoreCase("No"));

            do {

                System.out.println("Please enter a question. Enter \"done\" once finished");
                this.teacherInput = scan.nextLine();
                if (!this.teacherInput.equals("done")) {
                    writer.write(this.teacherInput + "\n");
                }
                if (this.teacherInput.equals("done")) {
                    this.askRandomQuestions = "";
                    break;
                }

                do {
                    System.out.println(
                            "Please enter the answer choices with a comma (no space) in between each choice. There must be only 4 choices");
                    this.options = scan.nextLine();
                    this.choicesArray = options.split(",");
                    if (this.choicesArray.length != 4) {
                        System.out.println("You must enter exactly 4 choices!");
                    }
                } while (this.choicesArray.length != 4);

                writer.write(this.choicesArray[0] + "\n");
                writer.write(this.choicesArray[1] + "\n");
                writer.write(this.choicesArray[2] + "\n");
                writer.write(this.choicesArray[3] + "\n");

                do {
                    System.out.println(
                            "What is the answer? Please type out the entire choice exactly as typed out previously.");
                    this.answer = scan.nextLine();

                    if (!this.answer.equals(choicesArray[0]) && !this.answer.equals(choicesArray[1]) &&
                            !this.answer.equals(choicesArray[2]) && !this.answer.equals(choicesArray[3])) {
                        System.out.println("You must enter a choice exactly as typed previously!");
                    }
                } while (!this.answer.equals(choicesArray[0]) && !this.answer.equals(choicesArray[1]) &&
                        !this.answer.equals(choicesArray[2]) && !this.answer.equals(choicesArray[3]));

                writer.write(this.answer + "\n");

            } while (!this.teacherInput.equals("done"));

            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Error writing the file!");
        }
    }

    public void takeQuiz(String courseName, String fileName, String userName, String accountNum) {
        double grade;
        ArrayList<Question> imp = null;
        PrintWriter writer = null;
        try {
            boolean loop = true;
            ArrayList<String> list = new ArrayList<>();
            imp = new ArrayList<>();
            String[] choiceOptions = new String[4];
            File f = new File(fileName + ".txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            File sub = new File(courseName + fileName + "Submissions.txt");
            FileOutputStream fos = new FileOutputStream(sub, true);
            writer = new PrintWriter(fos);
            grade = 0.0;
            try {
                String line = bfr.readLine();
                while (line != null) {
                    list.add(line);
                    line = bfr.readLine();
                }
            } catch (IOException e) {
                System.out.println("The file entered is invalid!");
            } finally {
                bfr.close();
            }
            System.out.println(list.get(0));
            System.out.println(
                    "Please enter a number between 1-5, otherwise the question will be marked wrong. Enter 5 to attach a file as a response.");

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

            System.out.println(
                    "Please enter the number corresponding to the choice. An invalid number will result in losing points.\n");
            int ans = 0;
            writer.print(accountNum + "\n" + userName + "\n");

            while (loop) {
                try {

                    do {

                        for (int w = 0; w < imp.size(); w++) {
                            System.out.println(imp.get(w).getQuestion());
                            for (int c = 0; c < 4; c++) {
                                System.out.println((c + 1) + ": " + imp.get(w).getChoices().get(c));
                            }
                            ans = scan.nextInt();
                            if (ans == 1 || ans == 2 || ans == 3 || ans == 4) {
                                ArrayList<String> checkChoice = imp.get(w).getChoices();
                                String correctAnswer = imp.get(w).getAnswer();
                                if (w == 0) {
                                    writer.print(checkChoice.get(ans - 1));
                                } else {
                                    writer.print("," + checkChoice.get(ans - 1));
                                }
                                int correctIndex = checkChoice.indexOf(correctAnswer);
                                if (ans == correctIndex + 1) {
                                    this.correct++;
                                }
                            } else if (ans == 5) {
                                scan.nextLine();
                                System.out.println("Please enter the pathname of the file you would like to attach:");
                                String fileimp = scan.nextLine();
                                File file = new File(fileimp);
                                FileReader flr = new FileReader(file);
                                BufferedReader br = new BufferedReader(flr);
                                ArrayList<String> fileResponse = new ArrayList<>();
                                String line = br.readLine();
                                while (line != null) {
                                    fileResponse.add(line);
                                    line = br.readLine();
                                }
                                System.out.println(fileResponse);

                                File res = new File(userName + fileName + "Question" + (w + 1));
                                PrintWriter response = new PrintWriter(res);
                                for (int c = 0; c < fileResponse.size(); c++) {
                                    response.print(fileResponse.get(c) + "\n");
                                }
                                response.close();

                                writer.print("Student uploaded a file. Please open the file: " + userName + fileName
                                        + "Question:" + (w + 1));
                                br.close();
                            } else {
                                System.out.println("You must enter a number between 1-4!");
                                w--;
                            }
                        }
                    } while (ans != 1 && ans != 2 && ans != 3 && ans != 4 && ans != 5);
                    loop = false;
                } catch (InputMismatchException e) {
                    System.out.println("You must enter a number!");
                    scan.nextLine();
                }
            }
            writer.print("\n");
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            writer.print(timeStamp + "\n");

        } catch (FileNotFoundException e) {
            System.out.println("That quiz doesn't exist!");
        } catch (Exception e) {
            System.out.println("Error taking the quiz!");
        }

        grade = ((double) this.correct / imp.size()) * 100.00;
        String temp = String.format("%.2f", grade);
        writer.print(temp + "\n");
        writer.close();

    }

    public void modifyQuiz(String fileName) {
        int choice;
        ArrayList<Question> imp = null;
        try {
            ArrayList<String> list = new ArrayList<>();
            imp = new ArrayList<>();
            String[] choiceOptions = new String[4];
            File f = new File(fileName + ".txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            try {
                String line = bfr.readLine();
                while (line != null) {
                    list.add(line);
                    line = bfr.readLine();
                }
            } catch (IOException e) {
                System.out.println("The file entered is invalid!");
            } finally {
                bfr.close();
            }
            for (int i = 2; i < list.size(); i += 6) {
                for (int j = 1; j <= 4; j++) {
                    choiceOptions[j - 1] = list.get(i + j);
                }

                imp.add(new Question(list.get(i), choiceOptions, list.get(i + 5), this.randomChoices));
            }

            do {
                System.out.println("Please select an option.\n1. Modify question\n2. Delete question");
                this.teacherEdit = scan.nextLine();
                choice = Integer.parseInt(this.teacherEdit);
                if (choice == 1) {
                    do {
                        System.out.println(
                                "What is the current name of the question you would like to edit? Please enter it exactly!");
                        this.currentQuestion = scan.nextLine();

                        for (int i = 0; i < imp.size(); i++) {
                            if (imp.get(i).getQuestion().equals(this.currentQuestion)) {
                                this.match = true;
                                System.out.println("Please enter the name of the new question.");
                                this.teacherQuestion = scan.nextLine();
                                imp.get(i).setQuestion(this.teacherQuestion);
                                do {
                                    System.out.println(
                                            "Please enter the answer choices with a comma (no space) in between each choice. There must be only 4 choices");
                                    this.options = scan.nextLine();
                                    this.choicesArray = options.split(",");
                                    if (this.choicesArray.length == 4) {
                                        ArrayList<String> temporary = new ArrayList<>();
                                        Collections.addAll(temporary, this.choicesArray);
                                        imp.get(i).setChoices(temporary);
                                    }
                                    if (this.choicesArray.length != 4) {
                                        System.out.println("You must enter exactly 4 choices!");
                                    }
                                } while (this.choicesArray.length != 4);

                                do {
                                    System.out.println(
                                            "What is the answer? Please type out the entire choice exactly as typed out previously.");
                                    this.answer = scan.nextLine();

                                    if (!this.answer.equals(choicesArray[0]) && !this.answer.equals(choicesArray[1]) &&
                                            !this.answer.equals(choicesArray[2])
                                            && !this.answer.equals(choicesArray[3])) {
                                        System.out.println("You must enter a choice exactly as typed previously!");
                                    } else {
                                        imp.get(i).setAnswer(this.answer);
                                    }
                                } while (!this.answer.equals(choicesArray[0]) && !this.answer.equals(choicesArray[1]) &&
                                        !this.answer.equals(choicesArray[2]) && !this.answer.equals(choicesArray[3]));

                            }
                        }

                        if (!this.match) {
                            System.out.println("Could not find the question!");
                        }
                    } while (!this.match);
                } else if (choice == 2) {
                    do {
                        System.out.println(
                                "What is the current name of the question you would like to delete? Please enter it exactly!");
                        this.currentQuestion = scan.nextLine();

                        for (int i = 0; i < imp.size(); i++) {
                            if (imp.get(i).getQuestion().equals(this.currentQuestion)) {
                                this.delete = true;
                                imp.remove(i);
                            }
                        }

                        if (!this.delete) {
                            System.out.println("Could not find the question!");
                        }
                    } while (!this.delete);
                } else {
                    System.out.println("Invalid choice! Please select 1 or 2!");
                }
                if (choice == 1 || choice == 2) {
                    PrintWriter pw = new PrintWriter(fileName + ".txt");
                    pw.write(fileName + "\n");
                    pw.write(this.randomChoices + "\n");
                    for (int x = 0; x < imp.size(); x++) {
                        pw.write(imp.get(x).getQuestion() + "\n");
                        pw.write(imp.get(x).getChoices().get(0) + "\n");
                        pw.write(imp.get(x).getChoices().get(1) + "\n");
                        pw.write(imp.get(x).getChoices().get(2) + "\n");
                        pw.write(imp.get(x).getChoices().get(3) + "\n");
                        if (x < imp.size() - 1) {
                            pw.write(imp.get(x).getAnswer() + "\n");
                        } else {
                            pw.write(imp.get(x).getAnswer());
                        }

                    }
                    pw.flush();
                    pw.close();
                }

            } while (choice > 2 || choice < 1);
        } catch (FileNotFoundException e) {
            System.out.println("That quiz doesn't exist!");
        } catch (IOException e) {
            System.out.println("Error editing the quiz!");
        }

    }

}