package kienminh.tetrisgame.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // mỗi subclass có table riêng
@Table(name = "players")
public abstract class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    private int highScore = 0; // điểm cao nhất

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchPlayer> matchPlayers = new HashSet<>();
}
