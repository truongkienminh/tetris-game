// Block.java
package kienminh.tetrisgame.model;

import kienminh.tetrisgame.enums.BlockType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Block {
    private BlockType type;
    private int[][] shape;
    private int x;
    private int y;

    public Block(BlockType type, int[][] shape, int x, int y) {
        this.type = type;
        this.shape = shape;
        this.x = x;
        this.y = y;
    }
}
