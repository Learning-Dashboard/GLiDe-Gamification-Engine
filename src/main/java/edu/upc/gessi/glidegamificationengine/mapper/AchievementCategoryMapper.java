package edu.upc.gessi.glidegamificationengine.mapper;

import edu.upc.gessi.glidegamificationengine.dto.AchievementCategoryDto;
import edu.upc.gessi.glidegamificationengine.entity.AchievementCategoryEntity;
import edu.upc.gessi.glidegamificationengine.type.AchievementCategoryType;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class AchievementCategoryMapper {

    public static AchievementCategoryDto mapToAchievementCategoryDto(AchievementCategoryEntity achievementCategoryEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(achievementCategoryEntity, AchievementCategoryDto.class);
    }

    public static AchievementCategoryEntity mapToAchievementCategoryEntity(AchievementCategoryDto achievementCategoryDto) {
        ModelMapper modelMapper = new ModelMapper();
        Converter<String, AchievementCategoryType> stringToAchievementCategoryType = ctx -> AchievementCategoryType.valueOf(ctx.getSource());
        TypeMap<AchievementCategoryDto, AchievementCategoryEntity> propertyMapper = modelMapper.createTypeMap(AchievementCategoryDto.class, AchievementCategoryEntity.class);
        propertyMapper.addMappings(mapper -> {
            mapper.using(stringToAchievementCategoryType).map(AchievementCategoryDto::getName, AchievementCategoryEntity::setName);
        });
        return modelMapper.map(achievementCategoryDto, AchievementCategoryEntity.class);
    }

}
