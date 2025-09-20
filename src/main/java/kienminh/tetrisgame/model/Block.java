package kienminh.tetrisgame.model;

import kienminh.tetrisgame.enums.BlockType;

public class Block {
    private final BlockType type;
    private int[][] shape;
    private int x;
    private int y;

    public Block(BlockType type, int[][] shape, int x, int y) {
        this.type = type;
        this.shape = shape;
        this.x = x;
        this.y = y;
    }

    public Block copy() {
        int[][] newShape = new int[shape.length][];
        for (int i = 0; i < shape.length; i++) {
            newShape[i] = shape[i].clone();
        }
        return new Block(type, newShape, x, y);
    }

    public BlockType getType() {
        return type;
    }

    public int[][] getShape() {
        return shape;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
