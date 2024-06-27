package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.DateRuleDto;
import edu.upc.gessi.glidegamificationengine.dto.SimpleRuleDto;

import java.util.List;

public interface RuleService {

    List<SimpleRuleDto> getSimpleRules(String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

    SimpleRuleDto getSimpleRule(Long simpleRuleId);

    List<DateRuleDto> getDateRules(String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

    DateRuleDto getDateRule(Long dateRuleId);

}
