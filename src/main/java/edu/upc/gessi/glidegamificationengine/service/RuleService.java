package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.DateRuleDto;
import edu.upc.gessi.glidegamificationengine.dto.SimpleRuleDto;

import java.sql.Date;
import java.util.List;

public interface RuleService {

    SimpleRuleDto createSimpleRule(String simpleRuleName, Integer simpleRuleRepetitions, String gameSubjectAcronym, Integer gameCourse, String gamePeriod, String evaluableActionId, Long achievementId, String achievementAssignmentMessage, Boolean achievementAssignmentOnlyFirstTime, String achievementAssignmentCondition, List<Float> achievementAssignmentConditionParameters, Integer achievementAssignmentUnits, String achievementAssignmentAssessmentLevel);

    List<SimpleRuleDto> getSimpleRules(String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

    SimpleRuleDto getSimpleRule(Long simpleRuleId);

    DateRuleDto createDateRule(String dateRuleName, Integer dateRuleRepetitions, Date dateRuleStartDate, Date dateRuleEndDate, String gameSubjectAcronym, Integer gameCourse, String gamePeriod, String evaluableActionId, Long achievementId, String achievementAssignmentMessage, Boolean achievementAssignmentOnlyFirstTime, String achievementAssignmentCondition, List<Float> achievementAssignmentConditionParameters, Integer achievementAssignmentUnits, String achievementAssignmentAssessmentLevel);

    List<DateRuleDto> getDateRules(String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

    DateRuleDto getDateRule(Long dateRuleId);

}
