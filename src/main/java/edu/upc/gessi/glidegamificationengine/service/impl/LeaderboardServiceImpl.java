package edu.upc.gessi.glidegamificationengine.service.impl;

import edu.upc.gessi.glidegamificationengine.dto.LeaderboardResultDto;
import edu.upc.gessi.glidegamificationengine.entity.LeaderboardEntity;
import edu.upc.gessi.glidegamificationengine.entity.GameGroupEntity;
import edu.upc.gessi.glidegamificationengine.entity.key.GameKey;
import edu.upc.gessi.glidegamificationengine.exception.ResourceNotFoundException;
import edu.upc.gessi.glidegamificationengine.repository.LeaderboardRepository;
import edu.upc.gessi.glidegamificationengine.service.LeaderboardService;
import edu.upc.gessi.glidegamificationengine.type.ExtentType;
import edu.upc.gessi.glidegamificationengine.type.PlayerType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    /* Methods callable from Service Layer */

    protected LeaderboardEntity getLeaderboardEntityById(Long leaderboardId) {
        return leaderboardRepository.findById(leaderboardId)
                .orElseThrow(() -> new ResourceNotFoundException("Leaderboard with id '" + leaderboardId + "' not found."));
    }

    protected HashMap<String, List<LeaderboardResultDto>> getLeaderboardResultsBySubject(LeaderboardEntity leaderboardEntity, HashMap<String, Integer> achievementUnitsResults, HashMap<String, Date> achievementDatesResults) {
        Map<String, String> playerImages;
        if (leaderboardEntity.getAssessmentLevel().equals(PlayerType.Team)) {
            playerImages = leaderboardEntity.getGameEntity().getTeamPlayerLogos();
        }
        else {
            playerImages = leaderboardEntity.getGameEntity().getIndividualPlayerAvatars();
        }

        List<String> orderedPlayersWithAchievementResults;
        if (leaderboardEntity.getAchievementEntity().getCategory().isNumerical()) {
            orderedPlayersWithAchievementResults = achievementUnitsResults
                    .entrySet()
                    .stream()
                    .sorted((Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) -> {
                        return o2.getValue().equals(o1.getValue()) ?
                                o1.getKey().compareTo(o2.getKey())
                                : o2.getValue().compareTo(o1.getValue());
                    })
                    .map(e -> e.getKey())
                    .toList();
        } else {
            orderedPlayersWithAchievementResults = achievementDatesResults
                    .entrySet()
                    .stream()
                    .sorted((Map.Entry<String, Date> o1, Map.Entry<String, Date> o2) -> {
                        return o1.getValue().equals(o2.getValue()) ?
                                o1.getKey().compareTo(o2.getKey())
                                : o1.getValue().compareTo(o2.getValue());
                    })
                    .map(e -> e.getKey())
                    .toList();
        }

        HashMap<String, List<LeaderboardResultDto>> leaderboardResults = new HashMap<>();
        List<LeaderboardResultDto> leaderboardResultDtos = new ArrayList<>();
        for (int i = 0; i < orderedPlayersWithAchievementResults.size(); i++) {
            LeaderboardResultDto leaderboardResultDto = new LeaderboardResultDto();
            leaderboardResultDto.setPosition(i + 1);
            leaderboardResultDto.setPlayername(orderedPlayersWithAchievementResults.get(i));
            leaderboardResultDto.setPlayerimage(playerImages.get(orderedPlayersWithAchievementResults.get(i)));
            leaderboardResultDto.setAchievementunits(achievementUnitsResults.get(orderedPlayersWithAchievementResults.get(i)));
            leaderboardResultDto.setAchievementdate(achievementDatesResults.get(orderedPlayersWithAchievementResults.get(i)));
            if (i != 0) {
                if (leaderboardEntity.getAchievementEntity().getCategory().isNumerical() && leaderboardResultDtos.get(i - 1).getAchievementunits().equals(leaderboardResultDto.getAchievementunits())) {
                    leaderboardResultDto.setPosition(leaderboardResultDtos.get(i - 1).getPosition());
                } else if (!leaderboardEntity.getAchievementEntity().getCategory().isNumerical() && leaderboardResultDtos.get(i - 1).getAchievementdate().equals(leaderboardResultDto.getAchievementdate())) {
                    leaderboardResultDto.setPosition(leaderboardResultDtos.get(i - 1).getPosition());
                }
            }
            leaderboardResultDtos.add(leaderboardResultDto);
            playerImages.remove(orderedPlayersWithAchievementResults.get(i));
        }
        List<String> orderedPlayersWithoutAchievementResults = playerImages
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(e -> e.getKey())
                .toList();
        for (int i = 0; i < orderedPlayersWithoutAchievementResults.size(); i++) {
            LeaderboardResultDto leaderboardResultDto = new LeaderboardResultDto();
            leaderboardResultDto.setPosition(0);
            leaderboardResultDto.setPlayername(orderedPlayersWithoutAchievementResults.get(i));
            leaderboardResultDto.setPlayerimage(playerImages.get(orderedPlayersWithoutAchievementResults.get(i)));
            leaderboardResultDto.setAchievementunits(0);
            leaderboardResultDto.setAchievementdate(null);
            leaderboardResultDtos.add(leaderboardResultDto);
        }
        leaderboardResults.put(leaderboardEntity.getGameEntity().getId().getSubjectAcronym() + " " + leaderboardEntity.getGameEntity().getId().getCourse().toString() + " " + leaderboardEntity.getGameEntity().getId().getPeriod().toString(), leaderboardResultDtos);
        return leaderboardResults;
    }

    protected HashMap<String, List<LeaderboardResultDto>> getLeaderboardResultsByGroup(LeaderboardEntity leaderboardEntity, HashMap<String, Integer> achievementUnitsResults, HashMap<String, Date> achievementDatesResults) {
        HashMap<String, List<LeaderboardResultDto>> leaderboardResults = new HashMap<>();

        for (GameGroupEntity gameGroupEntity : leaderboardEntity.getGameEntity().getGameGroupEntities()) {
            Map<String, String> playerImages;
            if (leaderboardEntity.getAssessmentLevel().equals(PlayerType.Team)) {
                playerImages = gameGroupEntity.getTeamPlayerLogos();
            }
            else {
                playerImages = gameGroupEntity.getIndividualPlayerAvatars();
            }

            HashMap<String, Integer> newAchievementUnitsResults = new HashMap<>(achievementUnitsResults);
            newAchievementUnitsResults.keySet().retainAll(playerImages.keySet());
            HashMap<String, Date> newAchievementDatesResults = new HashMap<>(achievementDatesResults);
            newAchievementDatesResults.keySet().retainAll(playerImages.keySet());

            List<String> orderedPlayersWithAchievementResults;
            if (leaderboardEntity.getAchievementEntity().getCategory().isNumerical()) {
                orderedPlayersWithAchievementResults = newAchievementUnitsResults
                        .entrySet()
                        .stream()
                        .sorted((Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) -> {
                            return o2.getValue().equals(o1.getValue()) ?
                                    o1.getKey().compareTo(o2.getKey())
                                    : o2.getValue().compareTo(o1.getValue());
                        })
                        .map(e -> e.getKey())
                        .toList();
            }
            else {
                orderedPlayersWithAchievementResults = newAchievementDatesResults
                        .entrySet()
                        .stream()
                        .sorted((Map.Entry<String, Date> o1, Map.Entry<String, Date> o2) -> {
                            return o1.getValue().equals(o2.getValue()) ?
                                    o1.getKey().compareTo(o2.getKey())
                                    : o1.getValue().compareTo(o2.getValue());
                        })
                        .map(e -> e.getKey())
                        .toList();
            }

            List<LeaderboardResultDto> leaderboardResultDtos = new ArrayList<>();
            for (int i = 0; i < orderedPlayersWithAchievementResults.size(); i++) {
                LeaderboardResultDto leaderboardResultDto = new LeaderboardResultDto();
                leaderboardResultDto.setPosition(i + 1);
                leaderboardResultDto.setPlayername(orderedPlayersWithAchievementResults.get(i));
                leaderboardResultDto.setPlayerimage(playerImages.get(orderedPlayersWithAchievementResults.get(i)));
                leaderboardResultDto.setAchievementunits(newAchievementUnitsResults.get(orderedPlayersWithAchievementResults.get(i)));
                leaderboardResultDto.setAchievementdate(newAchievementDatesResults.get(orderedPlayersWithAchievementResults.get(i)));
                if (i != 0) {
                    if (leaderboardEntity.getAchievementEntity().getCategory().isNumerical() && leaderboardResultDtos.get(i - 1).getAchievementunits().equals(leaderboardResultDto.getAchievementunits())) {
                        leaderboardResultDto.setPosition(leaderboardResultDtos.get(i - 1).getPosition());
                    } else if (!leaderboardEntity.getAchievementEntity().getCategory().isNumerical() && leaderboardResultDtos.get(i - 1).getAchievementdate().equals(leaderboardResultDto.getAchievementdate())) {
                        leaderboardResultDto.setPosition(leaderboardResultDtos.get(i - 1).getPosition());
                    }
                }
                leaderboardResultDtos.add(leaderboardResultDto);
                playerImages.remove(orderedPlayersWithAchievementResults.get(i));
            }
            List<String> orderedPlayersWithoutAchievementResults = playerImages
                    .entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey))
                    .map(e -> e.getKey())
                    .toList();
            for (int i = 0; i < orderedPlayersWithoutAchievementResults.size(); i++) {
                LeaderboardResultDto leaderboardResultDto = new LeaderboardResultDto();
                leaderboardResultDto.setPosition(0);
                leaderboardResultDto.setPlayername(orderedPlayersWithoutAchievementResults.get(i));
                leaderboardResultDto.setPlayerimage(playerImages.get(orderedPlayersWithoutAchievementResults.get(i)));
                leaderboardResultDto.setAchievementunits(0);
                leaderboardResultDto.setAchievementdate(null);
                leaderboardResultDtos.add(leaderboardResultDto);
            }
            leaderboardResults.put(leaderboardEntity.getGameEntity().getId().getSubjectAcronym() + gameGroupEntity.getId().getGroup() + " " + leaderboardEntity.getGameEntity().getId().getCourse().toString() + " " + leaderboardEntity.getGameEntity().getId().getPeriod().toString(), leaderboardResultDtos);
        }

        return leaderboardResults;
    }


    /* Methods callable from Controller Layer */

    @Override
    @Transactional
    public HashMap<String, List<LeaderboardResultDto>> getLeaderboardResults(Long leaderboardId) {
        LeaderboardEntity leaderboardEntity = getLeaderboardEntityById(leaderboardId);

        GameKey gameId = leaderboardEntity.getGameEntity().getId();
        List<Map> achievementResults = leaderboardEntity.getAchievementEntity().getResults(gameId, leaderboardEntity.getAssessmentLevel(), leaderboardEntity.getStartDate(), leaderboardEntity.getEndDate());
        HashMap<String, Integer> achievementUnitsResults = (HashMap<String, Integer>) achievementResults.getFirst();
        HashMap<String, Date> achievementDatesResults = (HashMap<String, Date>) achievementResults.get(1);

        if (leaderboardEntity.getExtent().equals(ExtentType.Subject))
            return getLeaderboardResultsBySubject(leaderboardEntity, achievementUnitsResults, achievementDatesResults);
        else
            return getLeaderboardResultsByGroup(leaderboardEntity, achievementUnitsResults, achievementDatesResults);
    }

}
