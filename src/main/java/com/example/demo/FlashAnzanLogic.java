package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.io.Serializable;

@Component
@SessionScope
public class FlashAnzanLogic implements Serializable {
    private int level = 1;
    private int score = 0;
    private int highScore = 0;
    private int totalSum = 0;
    private String mode = "anzan_normal_num_noflip";
    private boolean gameStarted = false;
    private final Random random = new Random();

    // ゲームリセット
    public void reset() {
        if (this.score > this.highScore) {
            this.highScore = this.score;
        }
        this.score = 0;
        this.level = 1;
        this.gameStarted = false;
    }

    // ゲーム開始
    public void startGame(String mode, int startLevel) {
        this.mode = mode;
        this.level = startLevel;
        this.score = 0;
        this.gameStarted = true;
    }

    // スコア加算
    public void addScore(double timeBonusRatio) {
        int base = this.level * 100;
        int speed = (int) (timeBonusRatio * 500);
        int total = base + speed;

        double multiplier = 1.0;
        if (this.mode.contains("challenge_2")) multiplier *= 2.0;
        if (this.mode.contains("challenge_3")) multiplier *= 4.0;
        if (this.mode.startsWith("memory")) multiplier *= 1.5;

        this.score += (int)(total * multiplier);
        if (this.score > this.highScore) this.highScore = this.score;
    }

    // レベルアップ
    public void incrementLevel() {
        this.level++;
    }

    // 質問生成
    public List<Integer> generateQuestions() {
        this.totalSum = 0;
        List<Integer> list = new ArrayList<>();
        int count = Math.max(2, level); // 安全のため最低2個
        for (int i = 0; i < count; i++) {
            int num = random.nextInt(9) + 1; // 1~9
            list.add(num);
            this.totalSum += num;
        }
        return list;
    }

    // 選択肢生成
    public List<Integer> getChoices() {
        List<Integer> choices = new ArrayList<>();
        choices.add(totalSum);

        while (choices.size() < 4) {
            int delta = random.nextInt(7) - 3; // -3～+3
            int wrong = totalSum + delta;
            if (wrong > 0 && !choices.contains(wrong)) {
                choices.add(wrong);
            }
        }

        Collections.shuffle(choices);
        return choices;
    }

    // ゲーム状態取得
    public boolean isGameStarted() { return gameStarted; }
    public int getLevel() { return level; }
    public String getMode() { return mode; }
    public int getTotalSum() { return totalSum; }
    public int getScore() { return score; }
    public int getHighScore() { return highScore; }

    // 表示速度
    public int getDisplaySpeed() {
        int speed = 600 - (level * 20);
        if (this.mode.startsWith("memory")) speed += 100; // 記憶モードは少し緩やかに
        return Math.max(150, speed);
    }
}