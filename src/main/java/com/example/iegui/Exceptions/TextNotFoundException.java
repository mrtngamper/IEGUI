package com.example.iegui.Exceptions;

public class TextNotFoundException extends Exception {
    private String text;
    private String language;

    public TextNotFoundException(String text, String language) {
        this.text = text;
        this.language = language;
    }

    @Override
    public String getMessage() {
        return String.format("The following text was not found: " + text + " in the language: " + language);
    }
}
