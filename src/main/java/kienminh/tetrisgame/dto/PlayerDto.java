package kienminh.tetrisgame.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {
    private Long id;
    private String username;
    private int highScore;
}
