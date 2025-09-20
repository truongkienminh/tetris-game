package kienminh.tetrisgame.service;

import kienminh.tetrisgame.entity.Player;
import kienminh.tetrisgame.entity.Room;
import kienminh.tetrisgame.enums.RoomStatus;
import kienminh.tetrisgame.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room createRoom(String name, Player host) {
        Room room = new Room();
        room.setName(name);
        room.setStatus(RoomStatus.WAITING);
        room.getPlayers().add(host);
        host.setRoom(room);
        return roomRepository.save(room);
    }

    public Room addPlayer(Room room, Player player) {
        room.getPlayers().add(player);
        player.setRoom(room);
        return roomRepository.save(room);
    }

    public void removePlayer(Room room, Player player) {
        room.getPlayers().remove(player);
        player.setRoom(null);
        roomRepository.save(room);
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }
}
