package com.backend.IPv4.entity;

public class FinalQuizRequest {

    private String username; // ✅ use username now
    private int score;
    private boolean completed;

    public FinalQuizRequest() {}

    public FinalQuizRequest(String username, int score, boolean completed) {
        this.username = username;
        this.score = score;
        this.completed = completed;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
