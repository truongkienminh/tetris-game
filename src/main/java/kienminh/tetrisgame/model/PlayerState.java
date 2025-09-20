package kienminh.tetrisgame.model;

import java.util.LinkedList;
import java.util.Queue;

public class PlayerState {
    private final int[][] board;
    private Block currentBlock;
    private final Queue<Block> nextBlocks;
    private boolean alive;
    private int score;

    public PlayerState(int width, int height) {
        this.board = new int[height][width];
        this.nextBlocks = new LinkedList<>();
        this.alive = true;
        this.score = 0;
    }

    public int[][] getBoard() {
        return board;
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(Block currentBlock) {
        this.currentBlock = currentBlock;
    }

    public Queue<Block> getNextBlocks() {
        return nextBlocks;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
