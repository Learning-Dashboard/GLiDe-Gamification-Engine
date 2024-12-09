package edu.upc.gessi.glidegamificationengine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameGroupDTO {
    private String gameSubjectAcronym;
    private String gamePeriod;
    private Integer gameCourse;
    private Integer group;
}