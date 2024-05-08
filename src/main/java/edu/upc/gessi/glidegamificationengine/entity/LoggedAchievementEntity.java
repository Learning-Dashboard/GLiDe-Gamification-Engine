package edu.upc.gessi.glidegamificationengine.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logged_achievement")
public class LoggedAchievementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "message_shown", nullable = false)
    private Boolean messageShown;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id", referencedColumnName = "rule_id", nullable = false)
    @JoinColumn(name = "achievement_id", referencedColumnName = "achievement_id", nullable = false)
    private AchievementAssignmentEntity achievementAssignmentEntity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "playername", nullable = false)
    private PlayerEntity playerEntity;

}
