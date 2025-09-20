package kienminh.tetrisgame.controller;

import kienminh.tetrisgame.dto.GameEventDTO;
import kienminh.tetrisgame.dto.GameStateDTO;
import kienminh.tetrisgame.model.GameState;
import kienminh.tetrisgame.model.PlayerState;
import kienminh.tetrisgame.service.GameService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;

    public GameController(GameService gameService, SimpMessagingTemplate messagingTemplate) {
        this.gameService = gameService;
        this.messagingTemplate = messagingTemplate;
    }

    // Tạo game mới
    @PostMapping("/create")
    public GameStateDTO createGame(@RequestBody List<Long> playerIds) {
        GameState game = gameService.createGame(playerIds);
        return toDTO(game);
    }

    // Bắt đầu game
    @PostMapping("/start/{roomId}")
    public GameStateDTO startGame(@PathVariable String roomId) {
        GameState game = gameService.startGame(roomId);
        return toDTO(game);
    }

    // REST gửi event (có thể dùng WebSocket tốt hơn)
    @PostMapping("/event")
    public GameStateDTO sendEvent(@RequestBody GameEventDTO eventDTO) {
        GameState game = gameService.handleEvent(eventDTO.getRoomId(), mapToEvent(eventDTO));
        // broadcast realtime đến tất cả client trong room
        messagingTemplate.convertAndSend("/topic/" + eventDTO.getRoomId(), toDTO(game));
        return toDTO(game);
    }

    // --- WebSocket nhận event từ client ---
    @MessageMapping("/play")
    public void handleWS(GameEventDTO eventDTO) {
        GameState game = gameService.handleEvent(eventDTO.getRoomId(), mapToEvent(eventDTO));
        messagingTemplate.convertAndSend("/topic/" + eventDTO.getRoomId(), toDTO(game));
    }

    // --- Helpers ---
    private GameStateDTO toDTO(GameState game) {
        if (game == null) return null;
        GameStateDTO dto = new GameStateDTO();
        dto.setRoomId(game.getRoomId());
        dto.setStatus(game.getStatus().name());

        Map<Long, int[][]> boards = new HashMap<>();
        Map<Long, Integer> scores = new HashMap<>();
        game.getPlayers().forEach((uid, player) -> {
            boards.put(uid, player.getBoard());
            scores.put(uid, player.getScore());
        });

        dto.setPlayerBoards(boards);
        dto.setPlayerScores(scores);
        return dto;
    }

    private kienminh.tetrisgame.model.GameEvent mapToEvent(GameEventDTO dto) {
        var event = new kienminh.tetrisgame.model.GameEvent();
        event.setRoomId(dto.getRoomId());
        event.setUserId(dto.getUserId());
        event.setAction(dto.getAction());
        return event;
    }
}
