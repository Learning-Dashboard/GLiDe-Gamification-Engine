package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.AchievementCategoryDto;

import java.util.List;

public interface AchievementCategoryService {

    void initiateAchievementCategories();

    List<AchievementCategoryDto> getAllAchievementCategories();

    AchievementCategoryDto getAchievementCategoryByName(String achievementCategoryName);

}
