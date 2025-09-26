package kienminh.tetrisgame.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private Long id;
    private String name;
    private String status;
    private PlayerDto host;
    private List<PlayerDto> players;
}
