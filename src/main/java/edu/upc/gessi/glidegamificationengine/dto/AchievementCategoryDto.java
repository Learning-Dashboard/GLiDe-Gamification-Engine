package edu.upc.gessi.glidegamificationengine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AchievementCategoryDto {
    private String name;
    private String description;
    private Boolean numerical;
}
