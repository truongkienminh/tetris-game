package kienminh.tetrisgame.model;


import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

@Getter
@Setter
public class PlayerState {

    private Long userId;
    private int score;
    private boolean alive = true;
    private int[][] board;
    private Block currentBlock;
    private Queue<Block> nextBlocks = new LinkedList<>();

    public PlayerState(Long userId) {
        this.userId = userId;
        this.score = 0;
        this.board = new int[20][10];
    }


}