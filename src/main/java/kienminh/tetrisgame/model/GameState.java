package kienminh.tetrisgame.model;

import kienminh.tetrisgame.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameState {

    private Long roomId;
    private GameStatus status = GameStatus.WAITING;

    private Map<Long, PlayerState> players = new HashMap<>();

    public GameState(Long roomId) {
        this.roomId = roomId;
    }

    public void addPlayer(PlayerState player) {
        players.put(player.getUserId(), player);
    }

    public void removePlayer(Long userId) {
        players.remove(userId);
    }

}