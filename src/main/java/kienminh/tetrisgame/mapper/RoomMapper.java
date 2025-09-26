package kienminh.tetrisgame.mapper;

import kienminh.tetrisgame.dto.PlayerDto;
import kienminh.tetrisgame.dto.RoomDto;
import kienminh.tetrisgame.entity.Player;
import kienminh.tetrisgame.entity.Room;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomMapper {

    public PlayerDto toPlayerDto(Player player) {
        return new PlayerDto(
                player.getId(),
                player.getUsername(),
                player.getHighScore()
        );
    }

    public RoomDto toRoomDto(Room room) {
        List<PlayerDto> players = room.getPlayers().stream()
                .map(this::toPlayerDto)
                .toList();

        PlayerDto hostDto = room.getHost() != null ? toPlayerDto(room.getHost()) : null;

        return new RoomDto(
                room.getId(),
                room.getName(),
                room.getStatus().name(),
                hostDto,
                players
        );
    }
}
