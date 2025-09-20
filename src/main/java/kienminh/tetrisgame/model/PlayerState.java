// PlayerState.java
package kienminh.tetrisgame.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

@Getter
@Setter
public class PlayerState {
    private final int[][] board;
    private Block currentBlock;
    private final Queue<Block> nextBlocks = new LinkedList<>();
    private boolean alive = true;
    private int score = 0;

    public PlayerState(int width, int height) {
        this.board = new int[height][width];
    }
}
