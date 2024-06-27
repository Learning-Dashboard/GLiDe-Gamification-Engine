package edu.upc.gessi.glidegamificationengine.service.impl;

import edu.upc.gessi.glidegamificationengine.dto.DateRuleDto;
import edu.upc.gessi.glidegamificationengine.dto.SimpleRuleDto;
import edu.upc.gessi.glidegamificationengine.entity.DateRuleEntity;
import edu.upc.gessi.glidegamificationengine.entity.SimpleRuleEntity;
import edu.upc.gessi.glidegamificationengine.entity.key.GameKey;
import edu.upc.gessi.glidegamificationengine.exception.MissingInformationException;
import edu.upc.gessi.glidegamificationengine.exception.ResourceNotFoundException;
import edu.upc.gessi.glidegamificationengine.mapper.RuleMapper;
import edu.upc.gessi.glidegamificationengine.repository.DateRuleRepository;
import edu.upc.gessi.glidegamificationengine.repository.SimpleRuleRepository;
import edu.upc.gessi.glidegamificationengine.service.RuleService;
import edu.upc.gessi.glidegamificationengine.type.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private SimpleRuleRepository simpleRuleRepository;

    @Autowired
    private DateRuleRepository dateRuleRepository;

    @Autowired
    private GameServiceImpl gameService;

    /* Methods callable from Service Layer */


    /* Methods callable from Controller Layer */

    @Override
    public List<SimpleRuleDto> getSimpleRules(String gameSubjectAcronym, Integer gameCourse, String gamePeriod) {
        List<SimpleRuleEntity> simpleRuleEntities;

        if (gameSubjectAcronym != null || gameCourse != null || gamePeriod != null) {
            if (gameSubjectAcronym == null || gameCourse == null || gamePeriod == null) {
                List<String> missingInformation = new ArrayList<>();
                if (gameSubjectAcronym == null) missingInformation.add("subject acronym");
                if (gameCourse == null) missingInformation.add("course");
                if (gamePeriod == null) missingInformation.add("period");
                throw new MissingInformationException("Game not fully identified (Missing: " +
                        missingInformation.stream().map(value -> "'" + value + "'").collect(Collectors.joining(", ")) + ").");
            }
            PeriodType gamePeriodType = PeriodType.fromString(gamePeriod);
            GameKey gameKey = new GameKey();
            gameKey.setSubjectAcronym(gameSubjectAcronym);
            gameKey.setCourse(gameCourse);
            gameKey.setPeriod(gamePeriodType);
            simpleRuleEntities = simpleRuleRepository.findByGameEntity(gameService.getGameEntityByKey(gameKey));
        }
        else {
            simpleRuleEntities = simpleRuleRepository.findAll();
        }

        return simpleRuleEntities.stream().map((simpleRuleEntity -> RuleMapper.mapToSimpleRuleDto(simpleRuleEntity)))
                .collect(Collectors.toList());
    }

    @Override
    public SimpleRuleDto getSimpleRule(Long simpleRuleId) {
        SimpleRuleEntity simpleRuleEntity = simpleRuleRepository.findById(simpleRuleId)
                .orElseThrow(() -> new ResourceNotFoundException("Simple rule with id '" + simpleRuleId + "' not found."));
        return RuleMapper.mapToSimpleRuleDto(simpleRuleEntity);
    }

    @Override
    public List<DateRuleDto> getDateRules(String gameSubjectAcronym, Integer gameCourse, String gamePeriod) {
        List<DateRuleEntity> dateRuleEntities;

        if (gameSubjectAcronym != null || gameCourse != null || gamePeriod != null) {
            if (gameSubjectAcronym == null || gameCourse == null || gamePeriod == null) {
                List<String> missingInformation = new ArrayList<>();
                if (gameSubjectAcronym == null) missingInformation.add("subject acronym");
                if (gameCourse == null) missingInformation.add("course");
                if (gamePeriod == null) missingInformation.add("period");
                throw new MissingInformationException("Game not fully identified (Missing: " +
                        missingInformation.stream().map(value -> "'" + value + "'").collect(Collectors.joining(", ")) + ").");
            }
            PeriodType gamePeriodType = PeriodType.fromString(gamePeriod);
            GameKey gameKey = new GameKey();
            gameKey.setSubjectAcronym(gameSubjectAcronym);
            gameKey.setCourse(gameCourse);
            gameKey.setPeriod(gamePeriodType);
            dateRuleEntities = dateRuleRepository.findByGameEntity(gameService.getGameEntityByKey(gameKey));
        }
        else {
            dateRuleEntities = dateRuleRepository.findAll();
        }

        return dateRuleEntities.stream().map((dateRuleEntity -> RuleMapper.mapToDateRuleDto(dateRuleEntity)))
                .collect(Collectors.toList());
    }

    @Override
    public DateRuleDto getDateRule(Long dateRuleId) {
        DateRuleEntity dateRuleEntity = dateRuleRepository.findById(dateRuleId)
                .orElseThrow(() -> new ResourceNotFoundException("Date rule with id '" + dateRuleId + "' not found."));
        return RuleMapper.mapToDateRuleDto(dateRuleEntity);
    }

}
