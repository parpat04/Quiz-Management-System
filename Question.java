import java.util.ArrayList;
import java.util.Collections;

/**
 * Project 5 - Grademic - Question
 * <p>
 * The Grademic class for our Brightspace 2 Program,
 * Function to store and send varibales for quiz questions
 *
 * @authors Nathan Ehrenberger, Parthiv Patel, Long Truong, Isaiah Black Purdue
 *          CS 18000
 * @version May 2, 2022
 */

public class Question {

    private final boolean randomChoices;
    private String question;
    private ArrayList<String> choices;
    private String answer;

    public Question(String question, String[] choices, String answer, boolean randomChoices) {
        this.question = question;
        this.choices = new ArrayList<String>();
        Collections.addAll(this.choices, choices);
        this.answer = answer;
        this.randomChoices = randomChoices;
        if (this.randomChoices) {
            Collections.shuffle(this.choices);
        }

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
