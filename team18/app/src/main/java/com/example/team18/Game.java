package com.example.team18;

public class Game {
    private Sprite player;

    private int[] playerPosition;

    private int blockSize;
    private int score;
    private String difficulty;

    public Game(Sprite player, String difficulty, int deviceWidth) {
        this.player = player;
        this.blockSize = deviceWidth / 9;
        this.playerPosition = new int[] {4 * blockSize , blockSize};
        this.score = 0;
        this.difficulty = difficulty;
    }

    public void changePosition(int deltaX, int deltaY) {
        playerPosition[0] += deltaX * blockSize;
        playerPosition[1] += deltaY * blockSize;
    }

    public void setScore(int newScore) {
        score = newScore;
    }

    public int getScore() {
        return score;
    }

}
