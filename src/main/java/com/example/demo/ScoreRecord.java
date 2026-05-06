package com.example.demo;

import jakarta.persistence.*;

@Entity
public class ScoreRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private int score;
    private String mode; // どのモードでのスコアか

    // ゲッターとセッター
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
}