package kienminh.tetrisgame.repository;

import kienminh.tetrisgame.entity.Room;
import kienminh.tetrisgame.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStatus(RoomStatus status);
}
