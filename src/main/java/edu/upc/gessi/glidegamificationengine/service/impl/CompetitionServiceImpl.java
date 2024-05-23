package edu.upc.gessi.glidegamificationengine.service.impl;

import edu.upc.gessi.glidegamificationengine.dto.CompetitionResultDto;
import edu.upc.gessi.glidegamificationengine.entity.CompetitionEntity;
import edu.upc.gessi.glidegamificationengine.entity.GameGroupEntity;
import edu.upc.gessi.glidegamificationengine.entity.key.GameKey;
import edu.upc.gessi.glidegamificationengine.exception.ResourceNotFoundException;
import edu.upc.gessi.glidegamificationengine.repository.CompetitionRepository;
import edu.upc.gessi.glidegamificationengine.service.CompetitionService;
import edu.upc.gessi.glidegamificationengine.type.ExtentType;
import edu.upc.gessi.glidegamificationengine.type.PlayerType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    /* Methods callable from Service Layer */

    protected CompetitionEntity getCompetitionEntityById(Long competitionId) {
        return competitionRepository.findById(competitionId)
                .orElseThrow(() -> new ResourceNotFoundException("Competition with id '" + competitionId + "' not found."));
    }

    protected HashMap<String, List<CompetitionResultDto>> getCompetitionResultsBySubject(CompetitionEntity competitionEntity, HashMap<String, Integer> achievementUnitsResults, HashMap<String, Date> achievementDatesResults) {
        Map<String, String> playerImages;
        if (competitionEntity.getAssessmentLevel().equals(PlayerType.Team)) {
            playerImages = competitionEntity.getGameEntity().getTeamPlayerLogos();
        }
        else {
            playerImages = competitionEntity.getGameEntity().getIndividualPlayerAvatars();
        }

        List<String> orderedPlayersWithAchievementResults;
        if (competitionEntity.getAchievementEntity().getCategory().isNumerical()) {
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

        HashMap<String, List<CompetitionResultDto>> competitionResults = new HashMap<>();
        List<CompetitionResultDto> competitionResultDtos = new ArrayList<>();
        for (int i = 0; i < orderedPlayersWithAchievementResults.size(); i++) {
            CompetitionResultDto competitionResultDto = new CompetitionResultDto();
            competitionResultDto.setPosition(i + 1);
            competitionResultDto.setPlayername(orderedPlayersWithAchievementResults.get(i));
            competitionResultDto.setPlayerimage(playerImages.get(orderedPlayersWithAchievementResults.get(i)));
            competitionResultDto.setAchievementunits(achievementUnitsResults.get(orderedPlayersWithAchievementResults.get(i)));
            competitionResultDto.setAchievementdate(achievementDatesResults.get(orderedPlayersWithAchievementResults.get(i)));
            if (i != 0) {
                if (competitionEntity.getAchievementEntity().getCategory().isNumerical() && competitionResultDtos.get(i - 1).getAchievementunits().equals(competitionResultDto.getAchievementunits())) {
                    competitionResultDto.setPosition(competitionResultDtos.get(i - 1).getPosition());
                } else if (!competitionEntity.getAchievementEntity().getCategory().isNumerical() && competitionResultDtos.get(i - 1).getAchievementdate().equals(competitionResultDto.getAchievementdate())) {
                    competitionResultDto.setPosition(competitionResultDtos.get(i - 1).getPosition());
                }
            }
            competitionResultDtos.add(competitionResultDto);
            playerImages.remove(orderedPlayersWithAchievementResults.get(i));
        }
        List<String> orderedPlayersWithoutAchievementResults = playerImages
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(e -> e.getKey())
                .toList();
        for (int i = 0; i < orderedPlayersWithoutAchievementResults.size(); i++) {
            CompetitionResultDto competitionResultDto = new CompetitionResultDto();
            competitionResultDto.setPosition(0);
            competitionResultDto.setPlayername(orderedPlayersWithoutAchievementResults.get(i));
            competitionResultDto.setPlayerimage(playerImages.get(orderedPlayersWithoutAchievementResults.get(i)));
            competitionResultDto.setAchievementunits(0);
            competitionResultDto.setAchievementdate(null);
            competitionResultDtos.add(competitionResultDto);
        }
        competitionResults.put(competitionEntity.getGameEntity().getId().getSubjectAcronym() + " " + competitionEntity.getGameEntity().getId().getCourse().toString() + " " + competitionEntity.getGameEntity().getId().getPeriod().toString(), competitionResultDtos);
        return competitionResults;
    }

    protected HashMap<String, List<CompetitionResultDto>> getCompetitionResultsByGroup(CompetitionEntity competitionEntity, HashMap<String, Integer> achievementUnitsResults, HashMap<String, Date> achievementDatesResults) {
        HashMap<String, List<CompetitionResultDto>> competitionResults = new HashMap<>();

        for (GameGroupEntity gameGroupEntity : competitionEntity.getGameEntity().getGameGroupEntities()) {
            Map<String, String> playerImages;
            if (competitionEntity.getAssessmentLevel().equals(PlayerType.Team)) {
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
            if (competitionEntity.getAchievementEntity().getCategory().isNumerical()) {
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

            List<CompetitionResultDto> competitionResultDtos = new ArrayList<>();
            for (int i = 0; i < orderedPlayersWithAchievementResults.size(); i++) {
                CompetitionResultDto competitionResultDto = new CompetitionResultDto();
                competitionResultDto.setPosition(i + 1);
                competitionResultDto.setPlayername(orderedPlayersWithAchievementResults.get(i));
                competitionResultDto.setPlayerimage(playerImages.get(orderedPlayersWithAchievementResults.get(i)));
                competitionResultDto.setAchievementunits(newAchievementUnitsResults.get(orderedPlayersWithAchievementResults.get(i)));
                competitionResultDto.setAchievementdate(newAchievementDatesResults.get(orderedPlayersWithAchievementResults.get(i)));
                if (i != 0) {
                    if (competitionEntity.getAchievementEntity().getCategory().isNumerical() && competitionResultDtos.get(i - 1).getAchievementunits().equals(competitionResultDto.getAchievementunits())) {
                        competitionResultDto.setPosition(competitionResultDtos.get(i - 1).getPosition());
                    } else if (!competitionEntity.getAchievementEntity().getCategory().isNumerical() && competitionResultDtos.get(i - 1).getAchievementdate().equals(competitionResultDto.getAchievementdate())) {
                        competitionResultDto.setPosition(competitionResultDtos.get(i - 1).getPosition());
                    }
                }
                competitionResultDtos.add(competitionResultDto);
                playerImages.remove(orderedPlayersWithAchievementResults.get(i));
            }
            List<String> orderedPlayersWithoutAchievementResults = playerImages
                    .entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey))
                    .map(e -> e.getKey())
                    .toList();
            for (int i = 0; i < orderedPlayersWithoutAchievementResults.size(); i++) {
                CompetitionResultDto competitionResultDto = new CompetitionResultDto();
                competitionResultDto.setPosition(0);
                competitionResultDto.setPlayername(orderedPlayersWithoutAchievementResults.get(i));
                competitionResultDto.setPlayerimage(playerImages.get(orderedPlayersWithoutAchievementResults.get(i)));
                competitionResultDto.setAchievementunits(0);
                competitionResultDto.setAchievementdate(null);
                competitionResultDtos.add(competitionResultDto);
            }
            competitionResults.put(competitionEntity.getGameEntity().getId().getSubjectAcronym() + gameGroupEntity.getId().getGroup() + " " + competitionEntity.getGameEntity().getId().getCourse().toString() + " " + competitionEntity.getGameEntity().getId().getPeriod().toString(), competitionResultDtos);
        }

        return competitionResults;
    }


    /* Methods callable from Controller Layer */

    @Override
    @Transactional
    public HashMap<String, List<CompetitionResultDto>> getCompetitionResults(Long competitionId) {
        CompetitionEntity competitionEntity = getCompetitionEntityById(competitionId);

        GameKey gameId = competitionEntity.getGameEntity().getId();
        List<Map> achievementResults = competitionEntity.getAchievementEntity().getResults(gameId, competitionEntity.getAssessmentLevel(), competitionEntity.getStartDate(), competitionEntity.getEndDate());
        HashMap<String, Integer> achievementUnitsResults = (HashMap<String, Integer>) achievementResults.getFirst();
        HashMap<String, Date> achievementDatesResults = (HashMap<String, Date>) achievementResults.get(1);

        if (competitionEntity.getExtent().equals(ExtentType.Subject))
            return getCompetitionResultsBySubject(competitionEntity, achievementUnitsResults, achievementDatesResults);
        else
            return getCompetitionResultsByGroup(competitionEntity, achievementUnitsResults, achievementDatesResults);
    }

}
