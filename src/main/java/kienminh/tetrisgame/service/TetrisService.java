package kienminh.tetrisgame.service;

import kienminh.tetrisgame.model.Block;
import kienminh.tetrisgame.model.GameEvent;
import kienminh.tetrisgame.model.PlayerState;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TetrisService {

    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    private final BlockFactory blockFactory;
    private final GameService gameService;

    public TetrisService(BlockFactory blockFactory,@Lazy GameService gameService) {
        this.blockFactory = blockFactory;
        this.gameService = gameService;
    }

    public void initPlayer(PlayerState player) {
        spawnNewBlock(player);
    }

    public void spawnNewBlock(PlayerState player) {
        Block block = blockFactory.createRandomBlock(BOARD_WIDTH);
        player.setCurrentBlock(block);
        if (checkCollision(player, block)) {
            player.setAlive(false);
            // Sau khi 1 player chết -> check xem game còn ai sống không
            gameService.checkGameOver(player.getRoomId());
        }
    }

    public void handleEvent(PlayerState player, GameEvent event) {
        switch (event.getAction()) {
            case MOVE_LEFT -> move(player, -1);
            case MOVE_RIGHT -> move(player, 1);
            case ROTATE -> rotate(player);
            case SOFT_DROP -> softDrop(player);
            case HARD_DROP -> hardDrop(player);
        }
    }

    private void move(PlayerState player, int dx) {
        Block block = player.getCurrentBlock();
        block.setX(block.getX() + dx);
        if (checkCollision(player, block)) block.setX(block.getX() - dx);
    }

    private void rotate(PlayerState player) {
        Block block = player.getCurrentBlock();
        int[][] shape = block.getShape();
        int n = shape.length;
        int[][] rotated = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                rotated[j][n - 1 - i] = shape[i][j];
        int[][] old = block.getShape();
        block.setShape(rotated);
        if (checkCollision(player, block)) block.setShape(old);
    }

    private void softDrop(PlayerState player) {
        Block block = player.getCurrentBlock();
        block.setY(block.getY() + 1);
        if (checkCollision(player, block)) {
            block.setY(block.getY() - 1);
            mergeBlock(player, block);
            spawnNewBlock(player);
        }
    }

    private void hardDrop(PlayerState player) {
        Block block = player.getCurrentBlock();
        while (!checkCollision(player, block)) block.setY(block.getY() + 1);
        block.setY(block.getY() - 1);
        mergeBlock(player, block);
        spawnNewBlock(player);
    }

    private boolean checkCollision(PlayerState player, Block block) {
        int[][] shape = block.getShape();
        int[][] board = player.getBoard();
        for (int i = 0; i < shape.length; i++)
            for (int j = 0; j < shape[i].length; j++)
                if (shape[i][j] == 1) {
                    int x = block.getX() + j;
                    int y = block.getY() + i;
                    if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) return true;
                    if (board[y][x] != 0) return true;
                }
        return false;
    }

    private void mergeBlock(PlayerState player, Block block) {
        int[][] board = player.getBoard();
        int[][] shape = block.getShape();
        for (int i = 0; i < shape.length; i++)
            for (int j = 0; j < shape[i].length; j++)
                if (shape[i][j] == 1) board[block.getY()+i][block.getX()+j] = 1;
        clearLines(player);
    }

    private void clearLines(PlayerState player) {
        int[][] board = player.getBoard();
        int width = board[0].length;
        int height = board.length;
        for (int y = 0; y < height; y++) {
            boolean full = true;
            for (int x = 0; x < width; x++) if (board[y][x] == 0) full = false;
            if (full) {
                for (int row = y; row > 0; row--) board[row] = board[row-1].clone();
                for (int x = 0; x < width; x++) board[0][x] = 0;
                player.setScore(player.getScore() + 100);
            }
        }
    }
}
