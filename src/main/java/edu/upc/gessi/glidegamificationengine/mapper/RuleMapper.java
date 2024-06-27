package edu.upc.gessi.glidegamificationengine.mapper;

import edu.upc.gessi.glidegamificationengine.dto.DateRuleDto;
import edu.upc.gessi.glidegamificationengine.dto.SimpleRuleDto;
import edu.upc.gessi.glidegamificationengine.entity.DateRuleEntity;
import edu.upc.gessi.glidegamificationengine.entity.SimpleRuleEntity;
import org.modelmapper.ModelMapper;

public class RuleMapper {

    public static SimpleRuleDto mapToSimpleRuleDto(SimpleRuleEntity simpleRuleEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(simpleRuleEntity, SimpleRuleDto.class);
    }

    public static DateRuleDto mapToDateRuleDto(DateRuleEntity dateRuleEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dateRuleEntity, DateRuleDto.class);
    }

}
