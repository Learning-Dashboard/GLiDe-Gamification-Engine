package edu.upc.gessi.glidegamificationengine.service.impl;

import edu.upc.gessi.glidegamificationengine.dto.AchievementDto;
import edu.upc.gessi.glidegamificationengine.entity.AchievementEntity;
import edu.upc.gessi.glidegamificationengine.exception.ResourceNotFoundException;
import edu.upc.gessi.glidegamificationengine.mapper.AchievementMapper;
import edu.upc.gessi.glidegamificationengine.repository.AchievementRepository;
import edu.upc.gessi.glidegamificationengine.service.AchievementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private AchievementRepository achievementRepository;

    @Override
    public AchievementDto createAchievement(AchievementDto achievementDto) {
        AchievementEntity achievementEntity = AchievementMapper.mapToAchievementEntity(achievementDto);
        AchievementEntity savedAchievementEntity = achievementRepository.save(achievementEntity);
        return AchievementMapper.mapToAchievementDto(savedAchievementEntity);
    }

    @Override
    public AchievementDto getAchievementById(Long achievementId) {
        AchievementEntity achievementEntity = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement with id " + achievementId + " not found"));
        return AchievementMapper.mapToAchievementDto(achievementEntity);
    }

    @Override
    public List<AchievementDto> getAllAchievements() {
        List<AchievementEntity> achievementEntities = achievementRepository.findAll();
        return achievementEntities.stream().map((achievementEntity -> AchievementMapper.mapToAchievementDto(achievementEntity)))
                .collect(Collectors.toList());
    }

    @Override
    public AchievementDto updateAchievement(Long achievementId, AchievementDto updatedAchievementDto) {
        AchievementEntity achievementEntity = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement with id " + achievementId + " not found"));
        achievementEntity.setName(updatedAchievementDto.getName());
        achievementEntity.setIcon(updatedAchievementDto.getIcon());
        AchievementEntity updatedAchievementEntity = achievementRepository.save(achievementEntity);
        return AchievementMapper.mapToAchievementDto(updatedAchievementEntity);
    }

    @Override
    public void deleteAchievement(Long achievementId) {
        achievementRepository.findById(achievementId)
                .orElseThrow(() -> new ResourceNotFoundException("Achievement with id " + achievementId + " not found"));
        achievementRepository.deleteById(achievementId);
    }
}
