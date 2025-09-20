package kienminh.tetrisgame.controller;

import kienminh.tetrisgame.entity.Guest;
import kienminh.tetrisgame.entity.Player;
import kienminh.tetrisgame.entity.User;
import kienminh.tetrisgame.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestParam String username,
                                             @RequestParam String password,
                                             @RequestParam String email) {
        User user = playerService.createUser(username, password, email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/guest")
    public ResponseEntity<Guest> createGuest() {
        Guest guest = playerService.createGuest();
        return ResponseEntity.ok(guest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        Optional<Player> player = playerService.findById(id);
        return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
