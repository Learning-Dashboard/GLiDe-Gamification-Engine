package edu.upc.gessi.glidegamificationengine.entity;

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
@Table(name = "Achievement")
public class AchievementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Lob
    @Column(name = "icon")
    private String icon;

    @ManyToOne(optional = false, fetch = FetchType.LAZY) //cascade = CascadeType.ALL
    @JoinColumn(name = "achievementCategoryName", nullable = false)
    private AchievementCategoryEntity achievementCategoryEntity;

}
