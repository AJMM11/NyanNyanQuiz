package com.example.nyannyanquiz;

public class TranslateRequest {
    private String text;

    public TranslateRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
