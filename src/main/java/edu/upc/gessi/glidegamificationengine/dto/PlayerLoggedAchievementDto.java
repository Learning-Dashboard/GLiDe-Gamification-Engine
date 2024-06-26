package edu.upc.gessi.glidegamificationengine.dto;

import edu.upc.gessi.glidegamificationengine.type.AchievementCategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerLoggedAchievementDto {
    private Long id;

    @Schema(type = "string", format = "date", pattern = "yyyy-MM-dd")
    private Date date;

    private AchievementCategoryType category;
    private String name;
    private String icon;
    private Integer units;
    private String message;
    private Boolean viewed;
}
