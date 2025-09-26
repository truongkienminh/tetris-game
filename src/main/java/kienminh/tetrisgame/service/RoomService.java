package kienminh.tetrisgame.service;

import kienminh.tetrisgame.dto.RoomDto;
import kienminh.tetrisgame.entity.Player;
import kienminh.tetrisgame.entity.Room;
import kienminh.tetrisgame.enums.RoomStatus;
import kienminh.tetrisgame.mapper.RoomMapper;
import kienminh.tetrisgame.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    // Map lưu tạm token -> roomId
    private final Map<String, Long> inviteTokens = new HashMap<>();

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public RoomDto createRoom(String name, Player host) {
        Room room = new Room();
        room.setName(name);
        room.setStatus(RoomStatus.WAITING);
        room.setHost(host);
        room.getPlayers().add(host);
        host.setRoom(room);
        Room saved = roomRepository.save(room);
        return roomMapper.toRoomDto(saved);
    }

    public Room addPlayer(Room room, Player player) {
        room.getPlayers().add(player);
        player.setRoom(room);
        return roomRepository.save(room);
    }

    public void removePlayer(Room room, Player player) {
        room.getPlayers().remove(player);
        player.setRoom(null);

        // Nếu host rời đi -> chuyển host
        if (room.getHost() != null && room.getHost().equals(player)) {
            if (!room.getPlayers().isEmpty()) {
                room.setHost(room.getPlayers().iterator().next()); // chọn người đầu tiên còn lại làm host
            } else {
                deleteRoom(room.getId());
                return;
            }
        }

        // Nếu phòng trống -> xóa
        if (room.getPlayers().isEmpty()) {
            deleteRoom(room.getId());
        } else {
            roomRepository.save(room);
        }
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    // ========================
    // Mời người chơi bằng link
    // ========================
    public String generateInviteToken(Long roomId) {
        String token = UUID.randomUUID().toString();
        inviteTokens.put(token, roomId);
        return token;
    }
    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }


    public List<RoomDto> getActiveRooms() {
        return roomRepository.findByStatus(RoomStatus.WAITING)
                .stream()
                .map(roomMapper::toRoomDto)
                .toList();
    }

    public Optional<RoomDto> getRoomDetails(Long id) {
        return roomRepository.findById(id).map(roomMapper::toRoomDto);
    }

    public void transferHost(Room room, Player newHost) {
        if (!room.getPlayers().contains(newHost)) {
            throw new IllegalArgumentException("Người chơi không ở trong phòng");
        }
        room.setHost(newHost);
        roomRepository.save(room);
    }
    public Optional<Long> getRoomIdByToken(String token) {
        return Optional.ofNullable(inviteTokens.get(token));
    }
}
