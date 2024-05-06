package edu.upc.gessi.glidegamificationengine.entity;

import edu.upc.gessi.glidegamificationengine.type.AchievementCategoryType;
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
@Table(name = "AchievementCategory")
public class AchievementCategoryEntity {

    @Id
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private AchievementCategoryType name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "numerical", nullable = false)
    private Boolean numerical;

    public AchievementCategoryEntity(AchievementCategoryType name){
        this.name = name;
    }

}