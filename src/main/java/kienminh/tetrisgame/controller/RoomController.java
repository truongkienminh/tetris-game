package kienminh.tetrisgame.controller;

import kienminh.tetrisgame.entity.Player;
import kienminh.tetrisgame.entity.Room;
import kienminh.tetrisgame.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create")
    public ResponseEntity<Room> createRoom(@RequestParam String name, @RequestBody Player host) {
        Room room = roomService.createRoom(name, host);
        return ResponseEntity.ok(room);
    }

    @PostMapping("/{roomId}/join")
    public ResponseEntity<Room> joinRoom(@PathVariable Long roomId, @RequestBody Player player) {
        Room room = roomService.getRoomById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        room = roomService.addPlayer(room, player);
        return ResponseEntity.ok(room);
    }

    @PostMapping("/{roomId}/leave")
    public ResponseEntity<Room> leaveRoom(@PathVariable Long roomId, @RequestBody Player player) {
        Room room = roomService.getRoomById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        roomService.removePlayer(room, player);
        return ResponseEntity.ok(room);
    }
}
