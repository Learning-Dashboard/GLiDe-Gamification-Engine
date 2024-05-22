package edu.upc.gessi.glidegamificationengine.dto.wrapper;

import edu.upc.gessi.glidegamificationengine.dto.CompetitionResultDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionResultDtoListEntry {
    private String competitionExtentIdentifier;
    private List<CompetitionResultDto> competitionResults;
}
