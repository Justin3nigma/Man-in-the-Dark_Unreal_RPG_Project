package com.example.map524_assignment3_jaehyunahn;

public class Question {
    private int question;
    private boolean answer;
    private int colour;

    public Question(int question, boolean answer, int colour) {
        this.question = question;
        this.answer = answer;
        this.colour = colour;
    }

    int getQuestion(){
        return this.question;
    }

    boolean getAnswer(){
        return this.answer;
    }

    int getColour(){
        return this.colour;
    }
}
