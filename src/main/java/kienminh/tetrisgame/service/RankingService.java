package kienminh.tetrisgame.service;

import kienminh.tetrisgame.entity.Player;
import kienminh.tetrisgame.repository.PlayerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingService {

    private final PlayerRepository playerRepository;

    public RankingService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }



    public List<Player> getTopPlayers(int limit) {
        return playerRepository.findAllByOrderByHighScoreDesc(PageRequest.of(0, limit));
    }
}
