package edu.upc.gessi.glidegamificationengine.entity;

import edu.upc.gessi.glidegamificationengine.type.AchievementCategoryType;
import edu.upc.gessi.glidegamificationengine.type.PlayerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "player")
public abstract class PlayerEntity {

    @Id
    @Column(name = "playername")
    private String playername;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlayerType type;

    @OneToMany(mappedBy = "playerEntity", fetch = FetchType.LAZY)
    private List<LoggedAchievementEntity> loggedAchievementEntities;

    public List<LoggedAchievementEntity> getLoggedAchievementEntities(AchievementCategoryType achievementCategory) {
        List<LoggedAchievementEntity> filteredLoggedAchievementEntities = new ArrayList<>();
        for (int i = 0; i < loggedAchievementEntities.size(); i++) {
            if (loggedAchievementEntities.get(i).getAchievementAssignmentEntity().getAchievementEntity().getCategory().equals(achievementCategory)) {
                filteredLoggedAchievementEntities.add(loggedAchievementEntities.get(i));
            }
        }
        return filteredLoggedAchievementEntities;
    }
}
