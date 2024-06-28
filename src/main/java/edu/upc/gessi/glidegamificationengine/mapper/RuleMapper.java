package edu.upc.gessi.glidegamificationengine.mapper;

import edu.upc.gessi.glidegamificationengine.dto.DateRuleDto;
import edu.upc.gessi.glidegamificationengine.dto.SimpleRuleDto;
import edu.upc.gessi.glidegamificationengine.entity.DateRuleEntity;
import edu.upc.gessi.glidegamificationengine.entity.SimpleRuleEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class RuleMapper {

    public static SimpleRuleDto mapToSimpleRuleDto(SimpleRuleEntity simpleRuleEntity) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<SimpleRuleEntity, SimpleRuleDto>() {
            @Override
            protected void configure() {
                using(ctx -> ((SimpleRuleEntity) ctx.getSource()).getGameEntity().getId().getSubjectAcronym())
                        .map(source, destination.getGameSubjectAcronym());
                using(ctx -> ((SimpleRuleEntity) ctx.getSource()).getGameEntity().getId().getCourse())
                        .map(source, destination.getGameCourse());
                using(ctx -> ((SimpleRuleEntity) ctx.getSource()).getGameEntity().getId().getPeriod())
                        .map(source, destination.getGamePeriod());
                using(ctx -> ((SimpleRuleEntity) ctx.getSource()).getEvaluableActionEntity().getId())
                        .map(source, destination.getEvaluableActionId());
                using(ctx -> ((SimpleRuleEntity) ctx.getSource()).getAchievementAssignmentEntity().getAchievementEntity().getId())
                        .map(source, destination.getAchievementId());
            }
        });
        return modelMapper.map(simpleRuleEntity, SimpleRuleDto.class);
    }

    public static DateRuleDto mapToDateRuleDto(DateRuleEntity dateRuleEntity) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<DateRuleEntity, DateRuleDto>() {
            @Override
            protected void configure() {
                using(ctx -> ((DateRuleEntity) ctx.getSource()).getGameEntity().getId().getSubjectAcronym())
                        .map(source, destination.getGameSubjectAcronym());
                using(ctx -> ((DateRuleEntity) ctx.getSource()).getGameEntity().getId().getCourse())
                        .map(source, destination.getGameCourse());
                using(ctx -> ((DateRuleEntity) ctx.getSource()).getGameEntity().getId().getPeriod())
                        .map(source, destination.getGamePeriod());
                using(ctx -> ((DateRuleEntity) ctx.getSource()).getEvaluableActionEntity().getId())
                        .map(source, destination.getEvaluableActionId());
                using(ctx -> ((DateRuleEntity) ctx.getSource()).getAchievementAssignmentEntity().getAchievementEntity().getId())
                        .map(source, destination.getAchievementId());
            }
        });
        return modelMapper.map(dateRuleEntity, DateRuleDto.class);
    }

}
