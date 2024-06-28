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

    AchievementDto updateAchievement(Long achievementId, String achievementName, MultipartFile achievementIcon, String achievementCategory) throws IOException;

    void deleteAchievement(Long achievementId);

    List<AchievementCategoryDto> getAchievementCategories();

    AchievementCategoryDto getAchievementCategory(String achievementCategoryName);

}
