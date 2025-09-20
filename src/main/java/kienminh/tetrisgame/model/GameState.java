package kienminh.tetrisgame.model;

import kienminh.tetrisgame.enums.GameStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameState {
    private final Long roomId;                 // Id của phòng chơi
    private GameStatus status;                 // WAITING, PLAYING, GAME_OVER
    private final Map<Long, PlayerState> players; // Danh sách player trong game

    public GameState(Long roomId) {
        this.roomId = roomId;
        this.status = GameStatus.WAITING;
        this.players = new HashMap<>();
    }

    public Long getRoomId() {
        return roomId;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Map<Long, PlayerState> getPlayers() {
        return Collections.unmodifiableMap(players);
    }

    public void addPlayer(Long playerId, PlayerState playerState) {
        players.put(playerId, playerState);
    }

    public PlayerState getPlayer(Long playerId) {
        return players.get(playerId);
    }
}
