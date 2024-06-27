package edu.upc.gessi.glidegamificationengine.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/leaderboards")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

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
