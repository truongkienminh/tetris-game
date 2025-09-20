package kienminh.tetrisgame.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GameStateDTO {
    private String roomId;
    private String status;
    private Map<Long, int[][]> playerBoards;
    private Map<Long, Integer> playerScores;
}
