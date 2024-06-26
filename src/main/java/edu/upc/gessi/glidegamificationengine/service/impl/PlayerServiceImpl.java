package edu.upc.gessi.glidegamificationengine.service.impl;

import edu.upc.gessi.glidegamificationengine.dto.IndividualPlayerDto;
import edu.upc.gessi.glidegamificationengine.dto.PlayerAchievementDto;
import edu.upc.gessi.glidegamificationengine.dto.TeamPlayerDto;
import edu.upc.gessi.glidegamificationengine.entity.*;
import edu.upc.gessi.glidegamificationengine.exception.ResourceNotFoundException;
import edu.upc.gessi.glidegamificationengine.mapper.PlayerMapper;
import edu.upc.gessi.glidegamificationengine.repository.IndividualPlayerRepository;
import edu.upc.gessi.glidegamificationengine.repository.PlayerRepository;
import edu.upc.gessi.glidegamificationengine.repository.TeamPlayerRepository;
import edu.upc.gessi.glidegamificationengine.service.PlayerService;
import edu.upc.gessi.glidegamificationengine.type.AchievementCategoryType;
import edu.upc.gessi.glidegamificationengine.type.PlayerType;
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
    public TeamPlayerDto getTeamPlayer(String teamPlayerPlayername) {
        TeamPlayerEntity teamPlayerEntity = teamPlayerRepository.findById(teamPlayerPlayername)
                .orElseThrow(() -> new ResourceNotFoundException("Team player with playername '" + teamPlayerPlayername + "' not found."));
        return PlayerMapper.mapToTeamPlayerDto(teamPlayerEntity);
    }

    @Override
    public IndividualPlayerDto getIndividualPlayer(String individualPlayerPlayername) {
        IndividualPlayerEntity individualPlayerEntity = individualPlayerRepository.findById(individualPlayerPlayername)
                .orElseThrow(() -> new ResourceNotFoundException("Individual player with playername '" + individualPlayerPlayername + "' not found."));
        return PlayerMapper.mapToIndividualPlayerDto(individualPlayerEntity);
    }

    @Override
    @Transactional
    public List<PlayerAchievementDto> getPlayerAchievements(String playerPlayername, Boolean achievementAttained, String achievementCategory) {
        PlayerEntity playerEntity = getPlayerEntityByPlayername(playerPlayername);
        if(achievementCategory == null) {
            List<PlayerAchievementDto> playerAchievementDtos = new ArrayList<>();
            for (AchievementCategoryType value : AchievementCategoryType.values()) {
                playerAchievementDtos = Stream.concat(playerAchievementDtos.stream(), getPlayerAchievements(playerPlayername, achievementAttained, value.toString()).stream()).toList();
            }
            return playerAchievementDtos;
        }
        else {
            AchievementCategoryType achievementCategoryType = AchievementCategoryType.fromString(achievementCategory);
            List<LoggedAchievementEntity> loggedAchievementEntities = playerEntity.getLoggedAchievementEntities(achievementCategoryType);
            Map<Long, PlayerAchievementDto> attainedPlayerAchievementDtos = new HashMap<>();

            for (int i = 0; i < loggedAchievementEntities.size(); i++) {
                Long currentAchievementId = loggedAchievementEntities.get(i).getAchievementAssignmentEntity().getAchievementEntity().getId();
                if (attainedPlayerAchievementDtos.containsKey(currentAchievementId)) {
                    PlayerAchievementDto existingPlayerAchievementDto = attainedPlayerAchievementDtos.get(currentAchievementId);
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
                    attainedPlayerAchievementDtos.replace(currentAchievementId, existingPlayerAchievementDto);
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
                    attainedPlayerAchievementDtos.put(currentAchievementId, newPlayerAchievementDto);
                }
            }

            if (achievementAttained == null || !achievementAttained) {
                TeamPlayerEntity teamPlayerEntity;
                if (playerEntity.getType().equals(PlayerType.Team)) {
                    teamPlayerEntity = (TeamPlayerEntity) playerEntity;
                } else {
                    IndividualPlayerEntity individualPlayerEntity = (IndividualPlayerEntity) playerEntity;
                    teamPlayerEntity = individualPlayerEntity.getTeamPlayerEntity();
                }

                Map<Long, PlayerAchievementDto> pendingPlayerAchievementDtos = new HashMap<>();
                for(RuleEntity ruleEntity : teamPlayerEntity.getProjectEntity().getGameGroupEntity().getGameEntity().getRuleEntities()) {
                    AchievementAssignmentEntity achievementAssignmentEntity = ruleEntity.getAchievementAssignmentEntity();
                    AchievementEntity achievementEntity = ruleEntity.getAchievementAssignmentEntity().getAchievementEntity();
                    if (achievementAssignmentEntity.getAssessmentLevel().equals(playerEntity.getType()) && achievementEntity.getCategory().equals(achievementCategoryType) && !attainedPlayerAchievementDtos.containsKey(achievementEntity.getId())) {
                        PlayerAchievementDto pendingPlayerAchievementDto = new PlayerAchievementDto();
                        pendingPlayerAchievementDto.setCategory(achievementEntity.getCategory());
                        pendingPlayerAchievementDto.setName(achievementEntity.getName());
                        pendingPlayerAchievementDto.setIcon(achievementEntity.getIcon());
                        pendingPlayerAchievementDto.setUnits(0);
                        pendingPlayerAchievementDto.setDate(null);
                        pendingPlayerAchievementDtos.put(achievementEntity.getId(), pendingPlayerAchievementDto);
                    }
                }

                if (achievementAttained == null) return Stream.concat(attainedPlayerAchievementDtos.values().stream(), pendingPlayerAchievementDtos.values().stream()).collect(Collectors.toList());
                else return pendingPlayerAchievementDtos.values().stream().collect(Collectors.toList());
            }
            else {  //achievementAttained
                return attainedPlayerAchievementDtos.values().stream().collect(Collectors.toList());
            }
        }
    }

}
