package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.IndividualPlayerDto;
import edu.upc.gessi.glidegamificationengine.dto.PlayerAchievementDto;
import edu.upc.gessi.glidegamificationengine.dto.PlayerLoggedAchievementDto;
import edu.upc.gessi.glidegamificationengine.dto.TeamPlayerDto;

import java.util.List;

public interface PlayerService {

    TeamPlayerDto getTeamPlayer(String teamPlayerPlayername);

    IndividualPlayerDto getIndividualPlayer(String individualPlayerPlayername);

    List<PlayerAchievementDto> getPlayerAchievements(String playerPlayername, Boolean achievementAttained, String achievementCategory);

    List<PlayerLoggedAchievementDto> getPlayerLoggedAchievements(String playerPlayername, Boolean loggedAchievementViewed, String achievementCategory);

    PlayerLoggedAchievementDto setPlayerLoggedAchievementViewed(String playerPlayername, Long loggedAchievementId);

}
