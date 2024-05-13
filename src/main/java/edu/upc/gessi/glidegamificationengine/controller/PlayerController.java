package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.dto.PlayerAchievementDto;
import edu.upc.gessi.glidegamificationengine.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Operation(summary = "Get player achievements", description = "Get the achievements of a player optionally filtered by a specific achievement category. The player is identified by its playername. The achievement category name must be a valid achievement category type (Points, Badges or Resources) when given. The player achievements are returned as a list of PlayerAchievementDto objects in which, if the achievement category type is numerical (i.e. Points and Resources), the date corresponds to the last time units were assigned, while if the achievement category is not numerical (i.e. Badges), the date corresponds to first time a unit was assigned).", tags = { "players" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: List of PlayerAchievementDto objects.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlayerAchievementDto.class)))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: The given achievement category name not a valid achievement category type (Only available: Points, Badges, Resources).", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Player with the given playername not found.", content = @Content)
    })
    @GetMapping(value ="/{playername}/achievements", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerAchievementDto>> getPlayerAchievements(@PathVariable("playername") String playerPlayername,
                                                                            @RequestParam(value = "category", required = false) String achivementCategory) {
        List<PlayerAchievementDto> playerAchievementDtos = playerService.getPlayerAchievements(playerPlayername, achivementCategory);
        return ResponseEntity.ok(playerAchievementDtos);
    }

}
