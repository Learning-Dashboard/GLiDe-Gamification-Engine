package edu.upc.gessi.glidegamificationengine.mapper;

import edu.upc.gessi.glidegamificationengine.dto.AchievementDto;
import edu.upc.gessi.glidegamificationengine.entity.AchievementEntity;
import edu.upc.gessi.glidegamificationengine.type.AchievementCategoryType;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class AchievementMapper {

    public static AchievementDto mapToAchievementDto(AchievementEntity achievementEntity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(achievementEntity, AchievementDto.class);
    }

    public static AchievementEntity mapToAchievementEntity(AchievementDto achievementDto){
        ModelMapper modelMapper = new ModelMapper();
        Converter<String, AchievementCategoryType> stringToAchievementCategoryType = ctx -> AchievementCategoryType.fromString(ctx.getSource());
        TypeMap<AchievementDto, AchievementEntity> propertyMapper = modelMapper.createTypeMap(AchievementDto.class, AchievementEntity.class);
        propertyMapper.addMappings(mapper -> {
            mapper.using(stringToAchievementCategoryType).map(AchievementDto::getCategory, AchievementEntity::setCategory);
        });
        return modelMapper.map(achievementDto, AchievementEntity.class);
    }

}
