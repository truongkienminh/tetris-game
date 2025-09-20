package kienminh.tetrisgame.service;

import kienminh.tetrisgame.model.PlayerState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class GameService {

    private final Map<String, PlayerState> players = new HashMap<>();
    private final TetrisService tetrisService;

    public GameService(TetrisService tetrisService) {
        this.tetrisService = tetrisService;
    }

    public String createNewGame() {
        PlayerState player = new PlayerState(10, 20);
        tetrisService.initPlayer(player);
        String gameId = UUID.randomUUID().toString();
        players.put(gameId, player);
        return gameId;
    }

    public PlayerState getPlayerState(String gameId) {
        return players.get(gameId);
    }

    public void removeGame(String gameId) {
        players.remove(gameId);
    }
}
