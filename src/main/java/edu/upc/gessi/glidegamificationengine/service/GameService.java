package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.GameDto;

import java.util.List;

public interface GameService {

    List<GameDto> getGames(String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

    void evaluateGame(String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

}
