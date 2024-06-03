package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Operation(summary = "Evaluate game", description = "Evaluate the rules of a game to assign achievements to players when their actions meet some conditions. The game is identified by the subject acronym, course and period.", tags = { "games" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Success message.", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: The given period name not a valid period type (Only available: Quadrimester1, Quadrimester2).", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Game with the given subject acronym, course and period not found.", content = @Content),
            @ApiResponse(responseCode = "409", description = "CONFLICT: Game cannot be evaluated because its state is either 'Preparation' or 'Finished', and not 'Playing'.", content = @Content)
    })
    @GetMapping(value ="/evaluate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> evaluateGame(@RequestParam(value = "subjectAcronym") String gameSubjectAcronym,
                                               @RequestParam(value = "course") Integer gameCourse,
                                               @RequestParam(value = "period") String gamePeriod) {
        gameService.evaluateGame(gameSubjectAcronym, gameCourse, gamePeriod);
        return ResponseEntity.ok("Game corresponding to subject with acronym '" + gameSubjectAcronym + "', course '" + gameCourse + "' and period '" + gamePeriod + "' successfully evaluated");
    }

}
