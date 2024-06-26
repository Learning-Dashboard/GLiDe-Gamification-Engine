package edu.upc.gessi.glidegamificationengine.mapper;

import edu.upc.gessi.glidegamificationengine.dto.IndividualPlayerDto;
import edu.upc.gessi.glidegamificationengine.dto.PlayerLoggedAchievementDto;
import edu.upc.gessi.glidegamificationengine.dto.TeamPlayerDto;
import edu.upc.gessi.glidegamificationengine.entity.IndividualPlayerEntity;
import edu.upc.gessi.glidegamificationengine.entity.LoggedAchievementEntity;
import edu.upc.gessi.glidegamificationengine.entity.TeamPlayerEntity;
import org.modelmapper.ModelMapper;

public class PlayerMapper {

    public static TeamPlayerDto mapToTeamPlayerDto(TeamPlayerEntity teamPlayerEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(teamPlayerEntity, TeamPlayerDto.class);
    }

    public static IndividualPlayerDto mapToIndividualPlayerDto(IndividualPlayerEntity individualPlayerEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(individualPlayerEntity, IndividualPlayerDto.class);
    }

    public static PlayerLoggedAchievementDto mapToPlayerLoggedAchievementDto(LoggedAchievementEntity loggedAchievementEntity) {
        PlayerLoggedAchievementDto playerLoggedAchievementDto = new PlayerLoggedAchievementDto();
        playerLoggedAchievementDto.setId(loggedAchievementEntity.getId());
        playerLoggedAchievementDto.setDate(loggedAchievementEntity.getDate());
        playerLoggedAchievementDto.setCategory(loggedAchievementEntity.getAchievementAssignmentEntity().getAchievementEntity().getCategory());
        playerLoggedAchievementDto.setName(loggedAchievementEntity.getAchievementAssignmentEntity().getAchievementEntity().getName());
        playerLoggedAchievementDto.setIcon(loggedAchievementEntity.getAchievementAssignmentEntity().getAchievementEntity().getIcon());
        playerLoggedAchievementDto.setUnits(loggedAchievementEntity.getAchievementAssignmentEntity().getAchievementUnits());
        playerLoggedAchievementDto.setMessage(loggedAchievementEntity.getAchievementAssignmentEntity().getMessage());
        playerLoggedAchievementDto.setViewed(loggedAchievementEntity.getViewed());
        return playerLoggedAchievementDto;
    }
}
