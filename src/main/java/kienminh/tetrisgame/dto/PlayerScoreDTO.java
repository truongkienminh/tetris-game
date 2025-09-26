package kienminh.tetrisgame.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerScoreDTO {
    private Long playerId;
    private String username;
    private int score;

}