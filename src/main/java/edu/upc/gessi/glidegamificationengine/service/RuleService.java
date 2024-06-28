package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.DateRuleDTO;
import edu.upc.gessi.glidegamificationengine.dto.SimpleRuleDTO;

import java.sql.Date;
import java.util.List;

public interface RuleService {

    SimpleRuleDTO createSimpleRule(String simpleRuleName, Integer simpleRuleRepetitions, String gameSubjectAcronym, Integer gameCourse, String gamePeriod, String evaluableActionId, Long achievementId, String achievementAssignmentMessage, Boolean achievementAssignmentOnlyFirstTime, String achievementAssignmentCondition, List<Float> achievementAssignmentConditionParameters, Integer achievementAssignmentUnits, String achievementAssignmentAssessmentLevel);

    List<SimpleRuleDTO> getSimpleRules(String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

    SimpleRuleDTO getSimpleRule(Long simpleRuleId);

    DateRuleDTO createDateRule(String dateRuleName, Integer dateRuleRepetitions, Date dateRuleStartDate, Date dateRuleEndDate, String gameSubjectAcronym, Integer gameCourse, String gamePeriod, String evaluableActionId, Long achievementId, String achievementAssignmentMessage, Boolean achievementAssignmentOnlyFirstTime, String achievementAssignmentCondition, List<Float> achievementAssignmentConditionParameters, Integer achievementAssignmentUnits, String achievementAssignmentAssessmentLevel);

    List<DateRuleDTO> getDateRules(String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

    DateRuleDTO getDateRule(Long dateRuleId);

}
