package com.example.nyannyanquiz;

import java.util.List;

public class Question {
    private String type, difficulty, category;
    private String question;
    private String correct_answer;
    private List<String> incorrect_answers;

    // Getter and Setter for 'type'
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getter and Setter for 'difficulty'
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // Getter and Setter for 'category'
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Getter and Setter for 'question'
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    // Getter and Setter for 'correctAnswer'
    public String getCorrectAnswer() {
        return correct_answer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correct_answer = correctAnswer;
    }

    // Getter and Setter for 'incorrectAnswer'
    public List<String> getIncorrectAnswer() {
        return incorrect_answers;
    }

    public void setIncorrectAnswer(List<String> incorrectAnswer) {
        this.incorrect_answers = incorrectAnswer;
    }
}
