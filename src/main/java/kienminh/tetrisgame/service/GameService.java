package kienminh.tetrisgame.service;

import kienminh.tetrisgame.model.GameEvent;
import kienminh.tetrisgame.model.GameState;
import kienminh.tetrisgame.model.PlayerState;
import kienminh.tetrisgame.enums.GameStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GameService {

    private final Map<String, GameState> activeGames = new HashMap<>();
    private final TetrisService tetrisService;

    public GameService(TetrisService tetrisService) {
        this.tetrisService = tetrisService;
    }

    public GameState createGame(List<Long> playerIds) {
        String roomId = UUID.randomUUID().toString();
        GameState game = new GameState(roomId);
        game.setStatus(GameStatus.WAITING);

        for (Long userId : playerIds) {
            PlayerState player = new PlayerState(10, 20);
            tetrisService.initPlayer(player);
            game.addPlayer(userId, player);
        }

        activeGames.put(roomId, game);
        return game;
    }

    public GameState startGame(String roomId) {
        GameState game = activeGames.get(roomId);
        if (game != null) game.setStatus(GameStatus.PLAYING);
        return game;
    }

    public GameState handleEvent(String roomId, GameEvent event) {
        GameState game = activeGames.get(roomId);
        if (game == null || game.getStatus() != GameStatus.PLAYING) return null;

        PlayerState player = game.getPlayers().get(event.getUserId());
        if (player == null || !player.isAlive()) return game;

        tetrisService.handleEvent(player, event);
        return game;
    }

    public void removeGame(String roomId) {
        activeGames.remove(roomId);
    }
}
