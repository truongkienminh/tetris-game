package kienminh.tetrisgame.service;

import kienminh.tetrisgame.enums.BlockType;
import kienminh.tetrisgame.model.Block;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BlockFactory {
    private final Random random = new Random();

    public Block createRandomBlock(int boardWidth) {
        BlockType[] types = BlockType.values();
        BlockType type = types[random.nextInt(types.length)];
        int[][] shape = deepCopy(type.getShape());
        int x = boardWidth / 2 - shape[0].length / 2;
        int y = 0;
        return new Block(type, shape, x, y);
    }

    private int[][] deepCopy(int[][] src) {
        int[][] copy = new int[src.length][];
        for (int i = 0; i < src.length; i++) {
            copy[i] = src[i].clone();
        }
        return copy;
    }
}
