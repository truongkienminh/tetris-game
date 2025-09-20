package kienminh.tetrisgame.repository;

import kienminh.tetrisgame.entity.MatchPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchPlayerRepository extends JpaRepository<MatchPlayer, Long> {
}
