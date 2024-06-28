package edu.upc.gessi.glidegamificationengine.service;

import edu.upc.gessi.glidegamificationengine.dto.GameDTO;

import java.util.List;

public interface GameService {

    List<GameDTO> getGames(String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

    void evaluateGame(String gameSubjectAcronym, Integer gameCourse, String gamePeriod);

}
