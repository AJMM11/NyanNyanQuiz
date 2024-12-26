package com.example.nyannyanquiz;

public class Question {
    private String type, difficulty, category;
    private String question;
    private String correctAnswer;
    private String[] incorrectAnswer;

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
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    // Getter and Setter for 'incorrectAnswer'
    public String[] getIncorrectAnswer() {
        return incorrectAnswer;
    }

    public void setIncorrectAnswer(String[] incorrectAnswer) {
        this.incorrectAnswer = incorrectAnswer;
    }
}
