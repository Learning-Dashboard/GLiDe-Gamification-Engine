package edu.upc.gessi.glidegamificationengine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "individual_player")
public class IndividualPlayerEntity extends PlayerEntity {

    @Lob
    @Column(name = "avatar", nullable = false)
    private String avatar;

    @Column(name = "role")
    private String role;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_player_playername", nullable = false)
    private TeamPlayerEntity teamPlayerEntity;

}
