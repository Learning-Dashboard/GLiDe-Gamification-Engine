package edu.upc.gessi.glidegamificationengine.service.impl;

import edu.upc.gessi.glidegamificationengine.dto.PlayerAchievementDto;
import edu.upc.gessi.glidegamificationengine.entity.LoggedAchievementEntity;
import edu.upc.gessi.glidegamificationengine.entity.PlayerEntity;
import edu.upc.gessi.glidegamificationengine.exception.ResourceNotFoundException;
import edu.upc.gessi.glidegamificationengine.repository.IndividualPlayerRepository;
import edu.upc.gessi.glidegamificationengine.repository.PlayerRepository;
import edu.upc.gessi.glidegamificationengine.repository.TeamPlayerRepository;
import edu.upc.gessi.glidegamificationengine.service.PlayerService;
import edu.upc.gessi.glidegamificationengine.type.AchievementCategoryType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private IndividualPlayerRepository individualPlayerRepository;

    /* Methods callable from Service Layer */

    protected PlayerEntity getPlayerEntityByPlayername(String playerPlayername) {
        return playerRepository.findById(playerPlayername)
                .orElseThrow(() -> new ResourceNotFoundException("Player with playername '" + playerPlayername + "' not found."));
    }


    /* Methods callable from Controller Layer */

    @Override
    @Transactional
    public List<PlayerAchievementDto> getPlayerAchievements(String playerPlayername, String achievementCategory) {
        PlayerEntity playerEntity = getPlayerEntityByPlayername(playerPlayername);
        if(achievementCategory == null) {
            List<PlayerAchievementDto> playerAchievementDtos = new ArrayList<>();
            for (AchievementCategoryType value : AchievementCategoryType.values()) {
                playerAchievementDtos = Stream.concat(playerAchievementDtos.stream(), getPlayerAchievements(playerPlayername, value.toString()).stream()).toList();
            }
            return playerAchievementDtos;
        }
        else {
            AchievementCategoryType achievementCategoryType = AchievementCategoryType.fromString(achievementCategory);
            List<LoggedAchievementEntity> loggedAchievementEntities = playerEntity.getLoggedAchievementEntities(achievementCategoryType);
            Map<Long, PlayerAchievementDto> playerAchievementDtos = new HashMap<>();

            for (int i = 0; i < loggedAchievementEntities.size(); i++) {
                Long currentAchievementId = loggedAchievementEntities.get(i).getAchievementAssignmentEntity().getAchievementEntity().getId();
                if (playerAchievementDtos.containsKey(currentAchievementId)) {
                    PlayerAchievementDto existingPlayerAchievementDto = playerAchievementDtos.get(currentAchievementId);
                    if (achievementCategoryType.isNumerical()) {
                        existingPlayerAchievementDto.setUnits(existingPlayerAchievementDto.getUnits() + loggedAchievementEntities.get(i).getAchievementAssignmentEntity().getAchievementUnits());
                        if (existingPlayerAchievementDto.getDate().before(loggedAchievementEntities.get(i).getDate())) {
                            existingPlayerAchievementDto.setDate(loggedAchievementEntities.get(i).getDate());
                        }
                    } else {
                        existingPlayerAchievementDto.setUnits(existingPlayerAchievementDto.getUnits() + 1);
                        if (existingPlayerAchievementDto.getDate().after(loggedAchievementEntities.get(i).getDate())) {
                            existingPlayerAchievementDto.setDate(loggedAchievementEntities.get(i).getDate());
                        }
                    }
                    playerAchievementDtos.replace(currentAchievementId, existingPlayerAchievementDto);
                } else {
                    PlayerAchievementDto newPlayerAchievementDto = new PlayerAchievementDto();
                    newPlayerAchievementDto.setCategory(loggedAchievementEntities.get(i).getAchievementAssignmentEntity().getAchievementEntity().getCategory());
                    newPlayerAchievementDto.setName(loggedAchievementEntities.get(i).getAchievementAssignmentEntity().getAchievementEntity().getName());
                    newPlayerAchievementDto.setIcon(loggedAchievementEntities.get(i).getAchievementAssignmentEntity().getAchievementEntity().getIcon());
                    if (loggedAchievementEntities.get(i).getAchievementAssignmentEntity().getAchievementEntity().getCategory().isNumerical()) {
                        newPlayerAchievementDto.setUnits(loggedAchievementEntities.get(i).getAchievementAssignmentEntity().getAchievementUnits());
                    } else {
                        newPlayerAchievementDto.setUnits(1);
                    }
                    newPlayerAchievementDto.setDate(loggedAchievementEntities.get(i).getDate());
                    playerAchievementDtos.put(currentAchievementId, newPlayerAchievementDto);
                }
            }

            return playerAchievementDtos.values().stream().collect(Collectors.toList());
        }
    }

}
