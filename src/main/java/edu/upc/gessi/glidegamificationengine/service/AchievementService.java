package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.AchievementDto;

import java.util.List;

public interface AchievementService {

    AchievementDto createAchievement(AchievementDto achievementDto);

    AchievementDto getAchievementById(Long achievementId);

    List<AchievementDto> getAllAchievements();

    AchievementDto updateAchievement(Long achievementId, AchievementDto updatedAchievementDto);

    void deleteAchievement(Long achievementId);
}
