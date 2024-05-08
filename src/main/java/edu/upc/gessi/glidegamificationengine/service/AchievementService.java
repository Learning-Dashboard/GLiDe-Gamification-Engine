package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.AchievementCategoryDto;
import edu.upc.gessi.glidegamificationengine.dto.AchievementDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AchievementService {

    AchievementDto createAchievement(String achievementName, MultipartFile achievementIcon, String achievementCategory) throws IOException;

    List<AchievementDto> getAchievements();

    AchievementDto getAchievement(Long achievementId);

    List<AchievementCategoryDto> getAchievementCategories();

    AchievementCategoryDto getAchievementCategory(String achievementCategoryName);

    /*
    AchievementDto createAchievement(AchievementDto achievementDto);

    AchievementDto updateAchievement(Long achievementId, AchievementDto updatedAchievementDto);

    void deleteAchievement(Long achievementId);
    */

}
