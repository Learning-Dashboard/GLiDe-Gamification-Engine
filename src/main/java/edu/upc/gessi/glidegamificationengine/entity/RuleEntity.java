package edu.upc.gessi.glidegamificationengine.entity;

import edu.upc.gessi.glidegamificationengine.type.RuleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "rule")
public abstract class RuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RuleType type;

    @OneToOne(mappedBy = "ruleEntity", fetch = FetchType.LAZY)
    private AchievementAssignmentEntity achievementAssignmentEntity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "game_subject_acronym", referencedColumnName = "subject_acronym", nullable = false)
    @JoinColumn(name = "game_course", referencedColumnName = "course", nullable = false)
    @JoinColumn(name = "game_period", referencedColumnName = "period", nullable = false)
    private GameEntity gameEntity;

}
