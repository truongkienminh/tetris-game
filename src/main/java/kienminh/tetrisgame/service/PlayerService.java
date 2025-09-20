package kienminh.tetrisgame.service;

import kienminh.tetrisgame.entity.Guest;
import kienminh.tetrisgame.entity.Player;
import kienminh.tetrisgame.entity.User;
import kienminh.tetrisgame.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public User createUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setHighScore(0);
        return playerRepository.save(user);
    }

    public Guest createGuest() {
        Guest guest = new Guest();
        guest.setUsername("Guest_" + UUID.randomUUID().toString().substring(0, 8));
        guest.setSessionId(UUID.randomUUID().toString());
        guest.setHighScore(0);
        return playerRepository.save(guest);
    }

    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }
}
