package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.dto.LeaderboardDto;
import edu.upc.gessi.glidegamificationengine.dto.LeaderboardResultDto;
import edu.upc.gessi.glidegamificationengine.dto.wrapper.LeaderboardResultDtoListEntry;
import edu.upc.gessi.glidegamificationengine.service.LeaderboardService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/leaderboards")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @Operation(summary = "Get leaderboards", description = "Get all the leaderboards optionally filtered by a specific game. The game is identified by the subject acronym, course and period when given, being the period name a valid period type (Quadrimester1 or Quadrimester2) (if not given, leaderboards from all games are going to be retrieved). The leaderboards are returned as a list of LeaderboardDto objects.", tags = { "leaderboards" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: List of LeaderboardDto objects.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LeaderboardDto.class)))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: (1) Game not fully identified (Missing: subject acronym, course or period); (2) The given period name not a valid period type (Only available: Quadrimester1, Quadrimester2).", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Game with the given subject acronym, course and period not found.", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LeaderboardDto>> getLeaderboards(@RequestParam(value = "gameSubjectAcronym", required = false) String gameSubjectAcronym,
                                                                @RequestParam(value = "gameCourse", required = false) Integer gameCourse,
                                                                @RequestParam(value = "gamePeriod", required = false) String gamePeriod) {
        List<LeaderboardDto> leaderboardDtos = leaderboardService.getLeaderboards(gameSubjectAcronym, gameCourse, gamePeriod);
        return ResponseEntity.ok(leaderboardDtos);
    }

    @Operation(summary = "Get leaderboard", description = "Get a leaderboard. The leaderboard is identified by its id. The leaderboard is returned as a LeaderboardDto object.", tags = { "leaderboards" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: LeaderboardDto object.", content = @Content(schema = @Schema(implementation = LeaderboardDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Leaderboard with the given id not found.", content = @Content)
    })
    @GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LeaderboardDto> getLeaderboard(@PathVariable("id") Long leaderboardId) {
        LeaderboardDto leaderboardDto = leaderboardService.getLeaderboard(leaderboardId);
        return ResponseEntity.ok(leaderboardDto);
    }

    @Operation(summary = "Get leaderboard results", description = "Get the results of a leaderboard. The leaderboard is identified by its id. The results are returned as a list of LeaderboardResultDtoListEntry objects, which contain the leaderboard extent (subject/group) identifiers and their respective leaderboard results as list of LeaderboardResultDto objects.", tags = { "leaderboards" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: List of LeaderboardResultDtoListEntry objects, which contain the leaderboard extent (subject/group) identifiers and their respective leaderboard results as list of LeaderboardResultDto objects.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LeaderboardResultDtoListEntry.class)))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Leaderboard with the given id not found.", content = @Content)
    })
    @GetMapping(value ="/{id}/results", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LeaderboardResultDtoListEntry>> getLeaderboardResults(@PathVariable("id") Long leaderboardId) {
        HashMap<String, List<LeaderboardResultDto>> leaderboardResultDtos = leaderboardService.getLeaderboardResults(leaderboardId);
        List<LeaderboardResultDtoListEntry> leaderboardResultDtoListEntries = new ArrayList<>();
        leaderboardResultDtos.forEach((key, value) -> {
            LeaderboardResultDtoListEntry entry = new LeaderboardResultDtoListEntry();
            entry.setLeaderboardExtentIdentifier(key);
            entry.setLeaderboardResults(value);
            leaderboardResultDtoListEntries.add(entry);
        });
        return ResponseEntity.ok(leaderboardResultDtoListEntries);
    }
}
