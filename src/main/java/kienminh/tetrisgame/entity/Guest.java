package kienminh.tetrisgame.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "guests")
public class Guest extends Player {

    private String sessionId; // có thể dùng UUID để định danh guest
}
