package kienminh.tetrisgame.controller;

import kienminh.tetrisgame.entity.Player;
import kienminh.tetrisgame.service.RankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/top")
    public ResponseEntity<List<Player>> getTopPlayers(@RequestParam(defaultValue = "20") int limit) {
        List<Player> topPlayers = rankingService.getTopPlayers(limit);
        return ResponseEntity.ok(topPlayers);
    }
}
