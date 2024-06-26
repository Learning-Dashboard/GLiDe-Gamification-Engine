package edu.upc.gessi.glidegamificationengine.mapper;

import edu.upc.gessi.glidegamificationengine.dto.IndividualPlayerDto;
import edu.upc.gessi.glidegamificationengine.dto.TeamPlayerDto;
import edu.upc.gessi.glidegamificationengine.entity.IndividualPlayerEntity;
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

}
