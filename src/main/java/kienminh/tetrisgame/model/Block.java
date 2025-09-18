package kienminh.tetrisgame.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Block {

    private String type;
    private String color;
    private int x;
    private int y;
    private int[][] shape;

    public Block(String type, String color, int[][] shape) {
        this.type = type;
        this.color = color;
        this.shape = shape;
        this.x = 3;
        this.y = 0;
    }

}
