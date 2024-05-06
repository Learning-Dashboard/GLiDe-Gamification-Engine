package edu.upc.gessi.glidegamificationengine.service.impl;

import edu.upc.gessi.glidegamificationengine.dto.AchievementDto;
import edu.upc.gessi.glidegamificationengine.entity.AchievementEntity;
import edu.upc.gessi.glidegamificationengine.exception.ConstraintViolationException;
import edu.upc.gessi.glidegamificationengine.exception.ResourceNotFoundException;
import edu.upc.gessi.glidegamificationengine.mapper.AchievementMapper;
import edu.upc.gessi.glidegamificationengine.repository.AchievementRepository;
import edu.upc.gessi.glidegamificationengine.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AchievementServiceImpl implements AchievementService {

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private AchievementCategoryServiceImpl achievementCategoryService;

    /* Methods callable from Service Layer */


    /* Methods callable from Controller Layer */

    @Override
    public AchievementDto createAchievement(String achievementName, MultipartFile achievementIcon, String achievementCategoryName) throws IOException {
        AchievementEntity achievementEntity = new AchievementEntity();
        if (achievementName.isEmpty())
            throw new ConstraintViolationException("Name cannot be empty, please introduce a name");
        achievementEntity.setName(achievementName);
        achievementEntity.setIcon(Base64.getEncoder().encodeToString(achievementIcon.getBytes()));
        achievementEntity.setAchievementCategoryEntity(achievementCategoryService.getAchievementCategoryEntityByName(achievementCategoryName));

        AchievementEntity savedAchievementEntity;
        try{
             savedAchievementEntity = achievementRepository.save(achievementEntity);
        }
        catch (Exception exception){
            if (exception.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
                throw new ConstraintViolationException("Name '" + achievementEntity.getName() + "' already used, please pick a different name");
            else throw exception;
        }
        return AchievementMapper.mapToAchievementDto(savedAchievementEntity);
    }

    @Override
    public List<AchievementDto> getAllAchievements() {
        List<AchievementEntity> achievementEntities = achievementRepository.findAll();
        return achievementEntities.stream().map((achievementEntity -> AchievementMapper.mapToAchievementDto(achievementEntity)))
                .collect(Collectors.toList());
    }

    @Override
    public AchievementDto getAchievementById(Long achievementId) {
        AchievementEntity achievementEntity = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement with id '" + achievementId + "' not found"));
        return AchievementMapper.mapToAchievementDto(achievementEntity);
    }

    /*
    @Override
    public AchievementDto createAchievement(AchievementDto achievementDto) {
        AchievementEntity achievementEntity;
        try{
            achievementEntity = AchievementMapper.mapToAchievementEntity(achievementDto);
        }
        catch(Exception e){
            throw new TypeNotCorrectException("Achievement category name '" + achievementDto.getAchievementCategoryName() + "' not a valid achievement category type (Only available: " +
                    Stream.of(AchievementCategoryType.values()).map(achievementCategoryType -> "'" + achievementCategoryType.toString() + "'").collect(Collectors.joining(", ")) + ")");
        }

        achievementEntity.setId(null);
        achievementEntity.setAchievementCategoryEntity(AchievementCategoryMapper.mapToAchievementCategoryEntity(achievementCategoryService.getAchievementCategoryByName(achievementEntity.getAchievementCategoryEntity().getName().toString())));

        AchievementEntity savedAchievementEntity = new AchievementEntity();
        try{
            savedAchievementEntity = achievementRepository.save(achievementEntity);
        }
        catch (Exception e){
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
                throw new ConstraintViolationException("Name '" + achievementEntity.getName() + "' already used, please pick a different name");
            else if (e.getCause() instanceof PropertyValueException)
                throw new ConstraintViolationException("Name cannot be null, please introduce a name");
        }
        return AchievementMapper.mapToAchievementDto(savedAchievementEntity);
    }

    @Override
    public AchievementDto updateAchievement(Long achievementId, AchievementDto updatedAchievementDto) {
        AchievementEntity achievementEntity = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement with id '" + achievementId + "' not found"));
        achievementEntity.setName(updatedAchievementDto.getName());
        achievementEntity.setIcon(updatedAchievementDto.getIcon());
        AchievementEntity updatedAchievementEntity = achievementRepository.save(achievementEntity);
        return AchievementMapper.mapToAchievementDto(updatedAchievementEntity);
    }

    @Override
    public void deleteAchievement(Long achievementId) {
        achievementRepository.findById(achievementId)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement with id '" + achievementId + "' not found"));
        achievementRepository.deleteById(achievementId);
    }
     */

}
