package edu.upc.gessi.glidegamificationengine.dto;

import edu.upc.gessi.glidegamificationengine.type.RuleType;
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
public class DateRuleDto {
    private Long id;
    private String name;
    private RuleType type;
    private Integer repetitions;

    @Schema(type = "string", format = "date", pattern = "yyyy-MM-dd")
    private Date startDate;

    @Schema(type = "string", format = "date", pattern = "yyyy-MM-dd")
    private Date endDate;

    private String evaluableActionId;
}
