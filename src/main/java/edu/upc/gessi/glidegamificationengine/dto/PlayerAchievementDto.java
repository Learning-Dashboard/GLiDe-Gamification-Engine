package edu.upc.gessi.glidegamificationengine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerAchievementDto {
    private String name;
    private String icon;
    private Integer units;
    private Date date;
}
