package kienminh.tetrisgame.model;

import kienminh.tetrisgame.enums.GameAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameEvent {

    private Long userId;
    private GameAction action;
    private long timestamp;

    public GameEvent(Long userId, GameAction action) {
        this.userId = userId;
        this.action = action;
        this.timestamp = System.currentTimeMillis();
    }

}
