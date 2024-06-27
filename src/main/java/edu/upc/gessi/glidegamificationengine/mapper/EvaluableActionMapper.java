package edu.upc.gessi.glidegamificationengine.mapper;

import edu.upc.gessi.glidegamificationengine.dto.EvaluableActionDto;
import edu.upc.gessi.glidegamificationengine.entity.EvaluableActionEntity;
import org.modelmapper.ModelMapper;

public class EvaluableActionMapper {

    public static EvaluableActionDto mapToEvaluableActionDto(EvaluableActionEntity evaluableActionEntity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(evaluableActionEntity, EvaluableActionDto.class);
    }

}
