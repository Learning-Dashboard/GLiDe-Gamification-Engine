package edu.upc.gessi.glidegamificationengine.entity;

import edu.upc.gessi.glidegamificationengine.type.AchievementCategoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "achievement")
public class AchievementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Lob
    @Column(name = "icon")
    private String icon;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private AchievementCategoryType category;

    @OneToMany(mappedBy = "achievementEntity", fetch = FetchType.LAZY)
    private List<AchievementAssignmentEntity> achievementAssignmentEntities;

}
