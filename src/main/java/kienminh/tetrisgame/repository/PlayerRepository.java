package kienminh.tetrisgame.repository;

import kienminh.tetrisgame.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByUsername(String username);
    List<Player> findAllByOrderByHighScoreDesc(Pageable pageable);
}
