package edu.upc.gessi.glidegamificationengine.service.impl;

import edu.upc.gessi.glidegamificationengine.dto.GameDto;
import edu.upc.gessi.glidegamificationengine.entity.*;
import edu.upc.gessi.glidegamificationengine.entity.key.GameKey;
import edu.upc.gessi.glidegamificationengine.exception.ConstraintViolationException;
import edu.upc.gessi.glidegamificationengine.exception.ResourceNotFoundException;
import edu.upc.gessi.glidegamificationengine.mapper.GameMapper;
import edu.upc.gessi.glidegamificationengine.mapper.LeaderboardMapper;
import edu.upc.gessi.glidegamificationengine.repository.GameRepository;
import edu.upc.gessi.glidegamificationengine.service.GameService;
import edu.upc.gessi.glidegamificationengine.type.AchievementCategoryType;
import edu.upc.gessi.glidegamificationengine.type.PeriodType;
import edu.upc.gessi.glidegamificationengine.type.PlayerType;
import edu.upc.gessi.glidegamificationengine.type.StateType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private LoggedActionServiceImpl loggedActionService;

    @Autowired
    private LoggedAchievementServiceImpl loggedAchievementService;

    /* Private methods */

    private void evaluateRule(RuleEntity ruleEntity, PlayerEntity playerEntity, Timestamp timestamp) {
        LoggedActionEntity loggedActionEntity = loggedActionService.getOrCreateLoggedActionEntityByKeyEntities(ruleEntity.getEvaluableActionEntity(), playerEntity, timestamp);
        Boolean loggedActionEntityAdded = ruleEntity.getAchievementAssignmentEntity().evaluateLoggedActionEntity(loggedActionEntity);
        if (loggedActionEntityAdded) {
            Boolean loggedAchievementEntitiesRequired = ruleEntity.evaluateRule(loggedActionEntity.getEvaluableActionEntity().getId(), loggedActionEntity.getPlayerEntity().getPlayername());
            if (loggedAchievementEntitiesRequired) {
                if (playerEntity.getType().equals(PlayerType.Team)) {
                    if (ruleEntity.getAchievementAssignmentEntity().getAssessmentLevel().equals(PlayerType.Team)) {
                        loggedAchievementService.createLoggedAchievementEntity(Date.valueOf(timestamp.toLocalDateTime().toLocalDate()), ruleEntity.getAchievementAssignmentEntity(), playerEntity);
                    }
                    else { //PlayerType.Individual
                        TeamPlayerEntity teamPlayerEntity = (TeamPlayerEntity) playerEntity;
                        for (IndividualPlayerEntity individualPlayerEntity : teamPlayerEntity.getIndividualPlayerEntities()) {
                            loggedAchievementService.createLoggedAchievementEntity(Date.valueOf(timestamp.toLocalDateTime().toLocalDate()), ruleEntity.getAchievementAssignmentEntity(), individualPlayerEntity);
                        }
                    }
                }
                else { //PlayerType.Individual
                    if (ruleEntity.getAchievementAssignmentEntity().getAssessmentLevel().equals(PlayerType.Individual)) {
                        loggedAchievementService.createLoggedAchievementEntity(Date.valueOf(timestamp.toLocalDateTime().toLocalDate()), ruleEntity.getAchievementAssignmentEntity(), playerEntity);
                    }
                    else { //PlayerType.Team
                        IndividualPlayerEntity individualPlayerEntity = (IndividualPlayerEntity) playerEntity;
                        loggedAchievementService.createLoggedAchievementEntity(Date.valueOf(timestamp.toLocalDateTime().toLocalDate()), ruleEntity.getAchievementAssignmentEntity(), individualPlayerEntity.getTeamPlayerEntity());
                    }
                }
                if (ruleEntity.getAchievementAssignmentEntity().getAchievementEntity().getCategory().equals(AchievementCategoryType.Points)) {
                    playerEntity.setPoints(playerEntity.getPoints() + ruleEntity.getAchievementAssignmentEntity().getAchievementUnits());
                    playerEntity.setLevel(ruleEntity.getGameEntity().calculateLevel(playerEntity.getPoints()));
                }
            }
        }
    }


    /* Methods callable from Service Layer */

    protected GameEntity getGameEntityByKey(GameKey gameKey) {
        return gameRepository.findById(gameKey)
                .orElseThrow(() -> new ResourceNotFoundException("Game with subject acronym '" + gameKey.getSubjectAcronym() + "', course '" + gameKey.getCourse() + "' and period '" + gameKey.getPeriod() + "' not found."));
    }


    /* Methods callable from Controller Layer */

    @Override
    public List<GameDto> getGames(String gameSubjectAcronym, Integer gameCourse, String gamePeriod) {
        List<GameEntity> gameEntities = new ArrayList<>();

        if (gameSubjectAcronym != null) {
            if (gameCourse != null) {
                if (gamePeriod != null) {
                    GameKey gameKey = new GameKey();
                    gameKey.setSubjectAcronym(gameSubjectAcronym);
                    gameKey.setCourse(gameCourse);
                    gameKey.setPeriod(PeriodType.fromString(gamePeriod));
                    Optional<GameEntity> gameEntity = gameRepository.findById(gameKey);
                    if (gameEntity.isPresent()) gameEntities.add(gameEntity.get());
                }
                else {
                    gameEntities = gameRepository.findByIdSubjectAcronymAndIdCourse(gameSubjectAcronym, gameCourse);
                }
            }
            else {
                if (gamePeriod != null) {
                    gameEntities = gameRepository.findByIdSubjectAcronymAndIdPeriod(gameSubjectAcronym, PeriodType.fromString(gamePeriod));
                }
                else {
                    gameEntities = gameRepository.findByIdSubjectAcronym(gameSubjectAcronym);
                }
            }
        }
        else {
            if (gameCourse != null) {
                if (gamePeriod != null) {
                    gameEntities = gameRepository.findByIdCourseAndIdPeriod(gameCourse, PeriodType.fromString(gamePeriod));
                }
                else {
                    gameEntities = gameRepository.findByIdCourse(gameCourse);
                }
            }
            else {
                if (gamePeriod != null) {
                    gameEntities = gameRepository.findByIdPeriod(PeriodType.fromString(gamePeriod));
                }
                else {
                    gameEntities = gameRepository.findAll();
                }
            }
        }

        return gameEntities.stream().map((gameEntity -> GameMapper.mapToGameDto(gameEntity)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void evaluateGame(String gameSubjectAcronym, Integer gameCourse, String gamePeriod) {
        PeriodType gamePeriodType = PeriodType.fromString(gamePeriod);
        GameKey gameKey = new GameKey();
        gameKey.setSubjectAcronym(gameSubjectAcronym);
        gameKey.setCourse(gameCourse);
        gameKey.setPeriod(gamePeriodType);
        GameEntity gameEntity = getGameEntityByKey(gameKey);

        if (gameEntity.getState().equals(StateType.Preparation) || gameEntity.getState().equals(StateType.Finished)) {
            throw new ConstraintViolationException("Game cannot be evaluated because its state is '" + gameEntity.getState().toString() + "' and not 'Playing', please try again only inside date range [" + gameEntity.getStartDate() + "," + gameEntity.getEndDate() + "].");
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        List<PlayerEntity> teamPlayerEntities = new ArrayList<>();
        List<PlayerEntity> individualPlayerEntities = new ArrayList<>();
        for (GameGroupEntity gameGroupEntity : gameEntity.getGameGroupEntities()) {
            for (ProjectEntity projectEntity : gameGroupEntity.getProjectEntities()) {
                TeamPlayerEntity teamPlayerEntity = projectEntity.getTeamPlayerEntity();
                teamPlayerEntities.add(teamPlayerEntity);
                individualPlayerEntities.addAll(teamPlayerEntity.getIndividualPlayerEntities());
            }
        }

        for (RuleEntity ruleEntity : gameEntity.getRuleEntities()) {
            if (ruleEntity.checkRuleValidity(Date.valueOf(timestamp.toLocalDateTime().toLocalDate()))) {
                if (ruleEntity.getEvaluableActionEntity().getAssessmentLevel().equals(PlayerType.Team)) {
                    for (PlayerEntity playerEntity : teamPlayerEntities) {
                        evaluateRule(ruleEntity, playerEntity, timestamp);
                    }
                } else { //PlayerType.Individual
                    for (PlayerEntity playerEntity : individualPlayerEntities) {
                        evaluateRule(ruleEntity, playerEntity, timestamp);
                    }
                }
            }
        }
    }

}
