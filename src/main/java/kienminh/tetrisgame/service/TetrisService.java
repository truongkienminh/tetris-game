package kienminh.tetrisgame.service;

import kienminh.tetrisgame.model.Block;
import kienminh.tetrisgame.model.PlayerState;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TetrisService {

    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;

    private final BlockFactory blockFactory;

    public TetrisService(BlockFactory blockFactory) {
        this.blockFactory = blockFactory;
    }

    public void initPlayer(PlayerState player) {
        generateNextBlocks(player);
        spawnNewBlock(player);
    }

    public void spawnNewBlock(PlayerState player) {
        if (player.getNextBlocks().isEmpty()) {
            generateNextBlocks(player);
        }

        Block newBlock = player.getNextBlocks().poll();
        player.setCurrentBlock(newBlock);
        generateNextBlocks(player);

        if (checkCollision(player, newBlock)) {
            player.setAlive(false); // game over
        }
    }

    private void generateNextBlocks(PlayerState player) {
        while (player.getNextBlocks().size() < 5) {
            player.getNextBlocks().add(blockFactory.createRandomBlock(BOARD_WIDTH));
        }
    }

    // --- Movement ---
    public void moveLeft(PlayerState player) {
        Block block = player.getCurrentBlock();
        block.setX(block.getX() - 1);
        if (checkCollision(player, block)) {
            block.setX(block.getX() + 1);
        }
    }

    public void moveRight(PlayerState player) {
        Block block = player.getCurrentBlock();
        block.setX(block.getX() + 1);
        if (checkCollision(player, block)) {
            block.setX(block.getX() - 1);
        }
    }

    public void rotate(PlayerState player) {
        Block block = player.getCurrentBlock();
        Block rotated = rotateBlock(block);

        if (!checkCollision(player, rotated)) {
            player.setCurrentBlock(rotated);
        }
    }

    private Block rotateBlock(Block block) {
        int n = block.getShape().length;
        int m = block.getShape()[0].length;
        int[][] rotated = new int[m][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rotated[j][n - 1 - i] = block.getShape()[i][j];
            }
        }

        Block clone = block.copy();
        clone.setShape(rotated);
        return clone;
    }

    public void softDrop(PlayerState player) {
        Block block = player.getCurrentBlock();
        block.setY(block.getY() + 1);

        if (checkCollision(player, block)) {
            block.setY(block.getY() - 1);
            mergeBlock(player, block);
            spawnNewBlock(player);
        }
    }

    public void hardDrop(PlayerState player) {
        Block block = player.getCurrentBlock();
        while (!checkCollision(player, block)) {
            block.setY(block.getY() + 1);
        }
        block.setY(block.getY() - 1);
        mergeBlock(player, block);
        spawnNewBlock(player);
    }

    // --- Helper ---
    private boolean checkCollision(PlayerState player, Block block) {
        int[][] shape = block.getShape();
        int[][] board = player.getBoard();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int x = block.getX() + j;
                    int y = block.getY() + i;

                    if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {
                        return true;
                    }
                    if (board[y][x] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void mergeBlock(PlayerState player, Block block) {
        int[][] board = player.getBoard();
        int[][] shape = block.getShape();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int boardX = block.getX() + j;
                    int boardY = block.getY() + i;
                    board[boardY][boardX] = 1;
                }
            }
        }
        clearLines(player);
    }

    private void clearLines(PlayerState player) {
        int[][] board = player.getBoard();
        int width = board[0].length;
        int height = board.length;

        for (int y = 0; y < height; y++) {
            boolean fullLine = true;
            for (int x = 0; x < width; x++) {
                if (board[y][x] == 0) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                for (int row = y; row > 0; row--) {
                    board[row] = Arrays.copyOf(board[row - 1], width);
                }
                Arrays.fill(board[0], 0);
                player.setScore(player.getScore() + 100);
            }
        }
    }
}
