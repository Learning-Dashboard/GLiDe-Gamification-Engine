package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.LeaderboardDto;
import edu.upc.gessi.glidegamificationengine.dto.LeaderboardResultDto;

import java.util.HashMap;
import java.util.List;

public interface LeaderboardService {

    List<LeaderboardDto> getLeaderboards(String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

    LeaderboardDto getLeaderboard(Long leaderboardId);

    HashMap<String, List<LeaderboardResultDto>> getLeaderboardResults(Long leaderboardId);

}
