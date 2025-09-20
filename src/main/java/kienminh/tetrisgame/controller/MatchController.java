package kienminh.tetrisgame.controller;

import kienminh.tetrisgame.entity.Match;
import kienminh.tetrisgame.entity.Player;
import kienminh.tetrisgame.entity.Room;
import kienminh.tetrisgame.service.MatchService;
import kienminh.tetrisgame.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;
    private final RoomService roomService;

    public MatchController(MatchService matchService, RoomService roomService) {
        this.matchService = matchService;
        this.roomService = roomService;
    }

    @PostMapping("/start/{roomId}")
    public ResponseEntity<Match> startMatch(@PathVariable Long roomId) {
        Room room = roomService.getRoomById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        Set<Player> players = room.getPlayers();
        if (players.isEmpty()) return ResponseEntity.badRequest().build();

        Match match = matchService.startMatch(room, players);
        return ResponseEntity.ok(match);
    }

    @PostMapping("/end/{matchId}")
    public ResponseEntity<Match> endMatch(@PathVariable Long matchId) {
        Match match = matchService.getMatchById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        matchService.endMatch(match);
        return ResponseEntity.ok(match);
    }

    @GetMapping("/{matchId}/players")
    public ResponseEntity<Set<Player>> getMatchPlayers(@PathVariable Long matchId) {
        Match match = matchService.getMatchById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        Set<Player> players = match.getMatchPlayers()
                .stream()
                .map(mp -> mp.getPlayer())
                .collect(Collectors.toSet());
        return ResponseEntity.ok(players);
    }
}
