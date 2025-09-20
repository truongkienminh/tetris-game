package kienminh.tetrisgame.dto;

import kienminh.tetrisgame.enums.GameAction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameEventDTO {
    private String roomId;
    private Long userId;
    private GameAction action;
}
