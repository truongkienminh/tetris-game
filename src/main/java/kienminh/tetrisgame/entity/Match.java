package kienminh.tetrisgame.entity;

import jakarta.persistence.*;
import kienminh.tetrisgame.enums.MatchStatus;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MatchStatus status = MatchStatus.PLAYING;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchPlayer> matchPlayers = new HashSet<>();
}
