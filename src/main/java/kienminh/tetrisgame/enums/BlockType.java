package kienminh.tetrisgame.enums;

public enum BlockType {
    I("cyan", new int[][] {
            {1, 1, 1, 1}
    }),
    O("yellow", new int[][] {
            {1, 1},
            {1, 1}
    }),
    T("purple", new int[][] {
            {0, 1, 0},
            {1, 1, 1}
    }),
    L("orange", new int[][] {
            {1, 0, 0},
            {1, 1, 1}
    }),
    J("blue", new int[][] {
            {0, 0, 1},
            {1, 1, 1}
    }),
    S("green", new int[][] {
            {0, 1, 1},
            {1, 1, 0}
    }),
    Z("red", new int[][] {
            {1, 1, 0},
            {0, 1, 1}
    });

    private final String color;
    private final int[][] shape;

    BlockType(String color, int[][] shape) {
        this.color = color;
        this.shape = shape;
    }

    public String getColor() {
        return color;
    }

    public int[][] getShape() {
        return shape;
    }
}
