package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.PlayerAchievementDto;

import java.util.List;

public interface PlayerService {

    List<PlayerAchievementDto> getAchievements(String playerPlayername, String achievementCategory);

}
