package com.backend.IPv4.entity;

import jakarta.persistence.*;

@Entity
public class ProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String moduleName;
    private String sectionName;
    private boolean completed;
    private int progressPercentage;

    public ProgressEntity() {}

    public ProgressEntity(UserEntity user, String moduleName, String sectionName, boolean completed, int progressPercentage) {
        this.user = user;
        this.moduleName = moduleName;
        this.sectionName = sectionName;
        this.completed = completed;
        this.progressPercentage = progressPercentage;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public UserEntity getUser() { return user; }

    public void setUser(UserEntity user) { this.user = user; }

    public String getModuleName() { return moduleName; }

    public void setModuleName(String moduleName) { this.moduleName = moduleName; }

    public String getSectionName() { return sectionName; }

    public void setSectionName(String sectionName) { this.sectionName = sectionName; }

    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) { this.completed = completed; }

    public int getProgressPercentage() { return progressPercentage; }

    public void setProgressPercentage(int progressPercentage) { this.progressPercentage = progressPercentage; }
}
