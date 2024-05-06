package edu.upc.gessi.glidegamificationengine.service.impl;

import edu.upc.gessi.glidegamificationengine.dto.AchievementCategoryDto;
import edu.upc.gessi.glidegamificationengine.entity.AchievementCategoryEntity;
import edu.upc.gessi.glidegamificationengine.exception.ContentNotInitiatedException;
import edu.upc.gessi.glidegamificationengine.exception.ResourceNotFoundException;
import edu.upc.gessi.glidegamificationengine.exception.TypeNotCorrectException;
import edu.upc.gessi.glidegamificationengine.mapper.AchievementCategoryMapper;
import edu.upc.gessi.glidegamificationengine.repository.AchievementCategoryRepository;
import edu.upc.gessi.glidegamificationengine.service.AchievementCategoryService;
import edu.upc.gessi.glidegamificationengine.type.AchievementCategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AchievementCategoryServiceImpl implements AchievementCategoryService {

    @Autowired
    private AchievementCategoryRepository achievementCategoryRepository;

    /* Methods callable from Service Layer */

    protected AchievementCategoryEntity getAchievementCategoryEntityByName(String achievementCategoryName){
        AchievementCategoryType achievementCategoryTypeName;
        try{
            achievementCategoryTypeName = AchievementCategoryType.valueOf(achievementCategoryName);
        }
        catch (IllegalArgumentException e){
            throw new TypeNotCorrectException("Name '" + achievementCategoryName + "' not a valid achievement category type (Only available: " +
                    Stream.of(AchievementCategoryType.values()).map(achievementCategoryType -> "'" + achievementCategoryType.toString() + "'").collect(Collectors.joining(", ")) + ")");
        }
        Optional<AchievementCategoryEntity> achievementCategoryEntity = achievementCategoryRepository.findById(achievementCategoryTypeName);
        if (achievementCategoryEntity.isEmpty())
            throw new ResourceNotFoundException("Achievement category with name '" + achievementCategoryName.toString() + "' not found because achievement categories not initiated or not up to date, please perform an initiate operation");
        return achievementCategoryEntity.get();
    }


    /* Methods callable from Controller Layer */

    @Override
    public void initiateAchievementCategories() {
        AchievementCategoryEntity points = new AchievementCategoryEntity(
                AchievementCategoryType.Points,
                "They represent the basic unit to measure the players' evolution and determine the players' level",
                true
        );
        achievementCategoryRepository.save(points);
        AchievementCategoryEntity badges = new AchievementCategoryEntity(
                AchievementCategoryType.Badges,
                "They represent special milestones reached by the players",
                false
        );
        achievementCategoryRepository.save(badges);
        AchievementCategoryEntity resources = new AchievementCategoryEntity(
                AchievementCategoryType.Resources,
                "They represent real-world rewards for the players",
                true
        );
        achievementCategoryRepository.save(resources);
    }

    @Override
    public List<AchievementCategoryDto> getAllAchievementCategories() {
        List<AchievementCategoryEntity> achievementCategoryEntities = achievementCategoryRepository.findAll();
        if (achievementCategoryEntities.size() != AchievementCategoryType.values().length)
            throw new ContentNotInitiatedException("Achievement categories not initiated or not up to date, please perform an initiate operation");
        return achievementCategoryEntities.stream().map(achievementCategoryEntity -> AchievementCategoryMapper.mapToAchievementCategoryDto(achievementCategoryEntity))
                .collect(Collectors.toList());
    }

    @Override
    public AchievementCategoryDto getAchievementCategoryByName(String achievementCategoryName) {
        AchievementCategoryEntity achievementCategoryEntity = getAchievementCategoryEntityByName(achievementCategoryName);
        return AchievementCategoryMapper.mapToAchievementCategoryDto(achievementCategoryEntity);
    }

}
