package kienminh.tetrisgame.repository;

import kienminh.tetrisgame.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Guest findBySessionId(String sessionId);
}
