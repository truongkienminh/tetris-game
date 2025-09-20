package kienminh.tetrisgame.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerStateDTO {
    private int[][] board;
    private int[][] currentBlockShape;
    private int currentBlockX;
    private int currentBlockY;
    private int score;
    private boolean alive;
}