// GameState.java
package kienminh.tetrisgame.model;

import kienminh.tetrisgame.enums.GameStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class GameState {
    private String roomId;
    private GameStatus status = GameStatus.WAITING;
    private Map<Long, PlayerState> players = new HashMap<>();

    public GameState(String roomId) {
        this.roomId = roomId;
    }

    public void addPlayer(Long userId, PlayerState player) {
        players.put(userId, player);
    }
}
