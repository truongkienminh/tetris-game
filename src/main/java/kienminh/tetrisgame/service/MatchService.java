package kienminh.tetrisgame.service;

import kienminh.tetrisgame.dto.PlayerScoreDTO;
import kienminh.tetrisgame.entity.Match;
import kienminh.tetrisgame.entity.MatchPlayer;
import kienminh.tetrisgame.entity.Player;
import kienminh.tetrisgame.entity.Room;
import kienminh.tetrisgame.enums.MatchStatus;
import kienminh.tetrisgame.repository.MatchPlayerRepository;
import kienminh.tetrisgame.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchPlayerRepository matchPlayerRepository;

    public MatchService(MatchRepository matchRepository,
                        MatchPlayerRepository matchPlayerRepository) {
        this.matchRepository = matchRepository;
        this.matchPlayerRepository = matchPlayerRepository;
    }

    public Match startMatch(Room room, Set<Player> players) {
        Match match = new Match();
        match.setRoom(room);
        match.setStatus(MatchStatus.PLAYING);

        for (Player player : players) {
            MatchPlayer mp = new MatchPlayer();
            mp.setMatch(match);
            mp.setPlayer(player);
            mp.setScore(0);
            match.getMatchPlayers().add(mp);
        }

        matchRepository.save(match);
        match.getMatchPlayers().forEach(matchPlayerRepository::save);
        return match;
    }

    public void endMatch(Match match) {
        match.setStatus(MatchStatus.FINISHED);
        matchRepository.save(match);

        // cập nhật highScore cho player
        match.getMatchPlayers().forEach(mp -> {
            Player p = mp.getPlayer();
            p.setHighScore(Math.max(p.getHighScore(), mp.getScore()));
        });
    }

    public List<PlayerScoreDTO> getFinalScores(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        return match.getMatchPlayers().stream()
                .map(mp -> new PlayerScoreDTO(
                        mp.getPlayer().getId(),
                        mp.getPlayer().getUsername(),
                        mp.getScore()
                ))
                .collect(Collectors.toList());
    }

    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }
}
