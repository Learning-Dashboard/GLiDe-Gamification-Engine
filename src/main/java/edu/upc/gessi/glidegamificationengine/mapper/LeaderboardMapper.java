package edu.upc.gessi.glidegamificationengine.mapper;

import edu.upc.gessi.glidegamificationengine.dto.LeaderboardDto;
import edu.upc.gessi.glidegamificationengine.entity.LeaderboardEntity;
import org.modelmapper.ModelMapper;

public class LeaderboardMapper {

    public static LeaderboardDto mapToLeaderboardDto(LeaderboardEntity leaderboardEntity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(leaderboardEntity, LeaderboardDto.class);
    }

}
