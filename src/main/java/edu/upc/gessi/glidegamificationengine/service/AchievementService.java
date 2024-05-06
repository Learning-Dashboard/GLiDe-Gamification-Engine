package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.AchievementDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AchievementService {

    AchievementDto createAchievement(String achievementName, MultipartFile achievementIcon, String achievementCategoryName) throws IOException;

    List<AchievementDto> getAllAchievements();

    AchievementDto getAchievementById(Long achievementId);

    /*
    AchievementDto createAchievement(AchievementDto achievementDto);

    AchievementDto updateAchievement(Long achievementId, AchievementDto updatedAchievementDto);

    void deleteAchievement(Long achievementId);
    */

}
