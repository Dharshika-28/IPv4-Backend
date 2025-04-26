package com.backend.IPv4.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "final_quiz_results")
public class FinalQuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // foreign key user_id
    @JsonBackReference
    private UserEntity user; // link to the UserEntity

    // Constructors
    public FinalQuizResult() {}

    public FinalQuizResult(int score, boolean completed, UserEntity user) {
        this.score = score;
        this.completed = completed;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }
}
