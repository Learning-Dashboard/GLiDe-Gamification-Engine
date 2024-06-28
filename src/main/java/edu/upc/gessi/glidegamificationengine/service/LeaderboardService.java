package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.LeaderboardDto;
import edu.upc.gessi.glidegamificationengine.dto.LeaderboardResultDto;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public interface LeaderboardService {

    LeaderboardDto createLeaderboard(String leaderboardName, Date leaderboardStartDate, Date leaderboardEndDate, String leaderboardAssessmentLevel, String leaderboardExtent, String leaderboardAnonymization, Boolean leaderboardStudentVisible, Long achievementId, String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

    List<LeaderboardDto> getLeaderboards(String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

    LeaderboardDto getLeaderboard(Long leaderboardId);

    HashMap<String, List<LeaderboardResultDto>> getLeaderboardResults(Long leaderboardId);

}
