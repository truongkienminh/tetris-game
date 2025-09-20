package kienminh.tetrisgame.entity;

import jakarta.persistence.*;
import kienminh.tetrisgame.enums.RoomStatus;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private RoomStatus status = RoomStatus.WAITING;

    // Host của phòng
    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    private Player host;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Player> players = new HashSet<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Match> matches = new HashSet<>();
}

