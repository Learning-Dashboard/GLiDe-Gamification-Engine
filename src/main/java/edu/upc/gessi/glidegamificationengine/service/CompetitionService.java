package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.CompetitionResultDto;

import java.util.HashMap;
import java.util.List;

public interface CompetitionService {

    HashMap<String, List<CompetitionResultDto>> getCompetitionResults(Long competitionId);

}
