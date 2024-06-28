package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.dto.IndividualPlayerDto;
import edu.upc.gessi.glidegamificationengine.dto.PlayerAchievementDto;
import edu.upc.gessi.glidegamificationengine.dto.PlayerLoggedAchievementDto;
import edu.upc.gessi.glidegamificationengine.dto.TeamPlayerDto;
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

    @Operation(summary = "Get team player", description = "Get a team player. The team player is identified by its playername. The team player is returned as a TeamPlayerDto object.", tags = { "players" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: TeamPlayerDto object.", content = @Content(schema = @Schema(implementation = TeamPlayerDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Team player with the given playername not found.", content = @Content)
    })
    @GetMapping(value="/teams/{playername}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamPlayerDto> getTeamPlayer(@PathVariable("playername") String teamPlayerPlayername) {
        TeamPlayerDto teamPlayerDto = playerService.getTeamPlayer(teamPlayerPlayername);
        return ResponseEntity.ok(teamPlayerDto);
    }

    @Operation(summary = "Get individual player", description = "Get an individual player. The individual player is identified by its playername. The individual player is returned as an IndividualPlayerDto object.", tags = { "players" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: IndividualPlayerDto object.", content = @Content(schema = @Schema(implementation = IndividualPlayerDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Individual player with the given playername not found.", content = @Content)
    })
    @GetMapping(value="/individuals/{playername}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndividualPlayerDto> getIndividualPlayer(@PathVariable("playername") String individualPlayerPlayername) {
        IndividualPlayerDto individualPlayerDto = playerService.getIndividualPlayer(individualPlayerPlayername);
        return ResponseEntity.ok(individualPlayerDto);
    }

    @Operation(summary = "Get player achievements", description = "Get the attained and/or pending achievements of a player optionally filtered by a specific achievement category. The player is identified by its playername. When only attained achievements must be retrieved, achievement attained must be true and, when only pending achievements must retrieved, achievement attained must be false (if not given, attained and pending achievements are going to be retrieved). The achievement category name must be a valid achievement category type (Points, Badges or Resources) when given (if not given, achievements from all achievement category types are going to be retrieved). The player achievements are returned as a list of PlayerAchievementDto objects in which, if the achievement category type is numerical (i.e. Points and Resources), the date corresponds to the last time units were assigned, while if the achievement category is not numerical (i.e. Badges), the date corresponds to first time a unit was assigned).", tags = { "players" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: List of PlayerAchievementDto objects.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlayerAchievementDto.class)))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: The given achievement category name not a valid achievement category type (Only available: Points, Badges, Resources).", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Player with the given playername not found.", content = @Content)
    })
    @GetMapping(value ="/{playername}/achievements", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerAchievementDto>> getPlayerAchievements(@PathVariable("playername") String playerPlayername,
                                                                            @RequestParam(value = "attained", required = false) Boolean achievementAttained,
                                                                            @RequestParam(value = "category", required = false) String achivementCategory) {
        List<PlayerAchievementDto> playerAchievementDtos = playerService.getPlayerAchievements(playerPlayername, achievementAttained, achivementCategory);
        return ResponseEntity.ok(playerAchievementDtos);
    }

    @Operation(summary = "Get player logged achievements", description = "Get the viewed and/or unviewed logged achievements of a player optionally filtered by a specific achievement category. The player is identified by its playername. When only viewed logged achievements must be retrieved, logged achievement viewed must be true and, when only unviewed logged achievements must retrieved, logged achievement viewed must be false (if not given, viewed and unviewed logged achievements are going to be retrieved). The achievement category name must be a valid achievement category type (Points, Badges or Resources) when given (if not given, logged achievements from all achievement category types are going to be retrieved). The player logged achievements are returned as a list of PlayerLoggedAchievementDto objects.", tags = { "players" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: List of PlayerLoggedAchievementDto objects.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlayerLoggedAchievementDto.class)))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: The given achievement category name not a valid achievement category type (Only available: Points, Badges, Resources).", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Player with the given playername not found.", content = @Content)
    })
    @GetMapping(value ="/{playername}/loggedAchievements", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerLoggedAchievementDto>> getPlayerLoggedAchievements(@PathVariable("playername") String playerPlayername,
                                                                                        @RequestParam(value = "viewed", required = false) Boolean loggedAchievementViewed,
                                                                                        @RequestParam(value = "category", required = false) String achivementCategory) {
        List<PlayerLoggedAchievementDto> playerLoggedAchievementDtos = playerService.getPlayerLoggedAchievements(playerPlayername, loggedAchievementViewed, achivementCategory);
        return ResponseEntity.ok(playerLoggedAchievementDtos);
    }

    @Operation(summary = "Set player logged achievement viewed", description = "Set a player logged achievement as viewed or unviewed depending on the boolean value passed. The player is identified by its playername and the logged achievement is identified by its id. The player logged achievement is returned as a PlayerLoggedAchievementDto object.", tags = { "players" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: PlayerLoggedAchievementDto object.", content = @Content(schema = @Schema(implementation = PlayerLoggedAchievementDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Player with the given playername and/or logged achievement with the given id not found.", content = @Content)
    })
    @PatchMapping(value = "/{playername}/loggedAchievements/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerLoggedAchievementDto> setPlayerLoggedAchievementViewed(@PathVariable("playername") String playerPlayername, @PathVariable("id") Long loggedAchievementId, @RequestPart(value = "viewed") Boolean viewed) {
        PlayerLoggedAchievementDto playerLoggedAchievementDto = playerService.setPlayerLoggedAchievementViewed(playerPlayername, loggedAchievementId, viewed);
        return ResponseEntity.ok(playerLoggedAchievementDto);
    }
}
