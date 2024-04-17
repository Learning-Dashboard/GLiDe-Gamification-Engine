package edu.upc.gessi.glidegamificationengine.mapper;

import edu.upc.gessi.glidegamificationengine.dto.AchievementDto;
import edu.upc.gessi.glidegamificationengine.entity.AchievementEntity;

public class AchievementMapper {

    public static AchievementDto mapToAchievementDto(AchievementEntity achievementEntity){
        return new AchievementDto(
                achievementEntity.getId(),
                achievementEntity.getName(),
                achievementEntity.getIcon()
        );
    }

    public static AchievementEntity mapToAchievementEntity(AchievementDto achievementDto){
        return new AchievementEntity(
                achievementDto.getId(),
                achievementDto.getName(),
                achievementDto.getIcon()
        );
    }
}
