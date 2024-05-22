package edu.upc.gessi.glidegamificationengine.entity;

import edu.upc.gessi.glidegamificationengine.entity.key.AchievementAssignmentKey;
import edu.upc.gessi.glidegamificationengine.type.ConditionType;
import edu.upc.gessi.glidegamificationengine.type.PlayerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "achievement_assignment")
@Check(constraints = "achievement_units >= 1")
public class AchievementAssignmentEntity {

    @EmbeddedId
    private AchievementAssignmentKey id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("ruleId")
    @JoinColumn(name = "rule_id", referencedColumnName = "id")
    private RuleEntity ruleEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("achievementId")
    @JoinColumn(name = "achievement_id")
    private AchievementEntity achievementEntity;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "only_first_time", nullable = false)
    private Boolean onlyFirstTime;

    @Column(name = "condition", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConditionType condition;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="achievement_assignment_condition_parameters", joinColumns = {@JoinColumn(name = "achievement_assignment_rule_id", referencedColumnName = "rule_id"),
                                                                                        @JoinColumn(name = "achievement_assignment_achievement_id", referencedColumnName = "achievement_id")})
    private List<Float> conditionParameters;

    @Column(name = "achievement_units")
    private Integer achievementUnits;

    @Column(name = "assessment_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlayerType assessmentLevel;

    @OneToMany(mappedBy = "achievementAssignmentEntity", fetch = FetchType.LAZY)
    private List<LoggedAchievementEntity> loggedAchievementEntities;

}
