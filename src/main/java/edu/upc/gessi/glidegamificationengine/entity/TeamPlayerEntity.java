package edu.upc.gessi.glidegamificationengine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "team_player")
public class TeamPlayerEntity extends PlayerEntity {

    @Lob
    @Column(name = "logo", nullable = false)
    private String logo;

}
