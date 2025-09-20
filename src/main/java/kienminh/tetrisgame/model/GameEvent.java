package kienminh.tetrisgame.model;

import kienminh.tetrisgame.enums.GameAction;

public class GameEvent {
    private final Long userId;             // Ai thực hiện hành động
    private final GameAction action;

    public GameEvent(Long userId, GameAction action) {
        this.userId = userId;
        this.action = action;
    }

    public Long getUserId() {
        return userId;
    }

    public GameAction getAction() {
        return action;
    }
}
