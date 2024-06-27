package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.LeaderboardResultDto;

import java.util.HashMap;
import java.util.List;

public interface LeaderboardService {

    HashMap<String, List<LeaderboardResultDto>> getLeaderboardResults(Long leaderboardId);

}
