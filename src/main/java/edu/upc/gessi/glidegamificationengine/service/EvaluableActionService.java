package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.EvaluableActionDto;

import java.util.List;

public interface EvaluableActionService {

    void initiateEvaluableActions();

    List<EvaluableActionDto> getEvaluableActions();

    EvaluableActionDto getEvaluableAction(String evaluableActionId);

}
