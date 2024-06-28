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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/leaderboards")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @Operation(summary = "Create leaderboard", description = "Create a new leaderboard. The leaderboard name cannot be blank. The leaderboard start date must be equal to or previous to the leaderboard end date. The leaderboard assessment level name must be a valid player type (Team or Individual). The leaderboard extent name must be a valid extent type (Subject or Group). The leaderboard anonymization name must be a valid anonymization type (Full, Partial or None). The achievement is identified by its id. The game is identified by the subject acronym, course and period, being the period name a valid period type (Quadrimester1 or Quadrimester2). The leaderboard is returned as a LeaderboardDto object.", tags = { "leaderboards" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED: LeaderboardDto object.", content = @Content(schema = @Schema(implementation = LeaderboardDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: (1) The given leaderboard assessment level name not a valid player type (Only available: Team, Individual); (2) The given leaderboard extent name not a valid extent type (Only available: Subject, Group); (3) The given leaderboard anonymization name not a valid anonymization type (Only available: Full, Partial, None); (4) The given game period name not a valid period type (Only available: Quadrimester1, Quadrimester2).", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: (1) Achievement with the given id not found; (2) Game with the given subject acronym, course and period not found.", content = @Content),
            @ApiResponse(responseCode = "409", description = "CONFLICT: (1) Leaderboard name cannot be blank; (2) Leaderboard start date cannot be posterior to leaderboard end date. (3) The game assigned to the leaderboard must have at least one rule with the same achievement assigned to the leaderboard.", content = @Content)
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LeaderboardDto> createLeaderboard(@RequestPart(value = "name") String leaderboardName,
                                                            @RequestPart(value = "startDate") @Schema(type = "string", format = "date", pattern = "yyyy-MM-dd") String leaderboardStartDate,
                                                            @RequestPart(value = "endDate") @Schema(type = "string", format = "date", pattern = "yyyy-MM-dd") String leaderboardEndDate,
                                                            @RequestPart(value = "assessmentLevel") String leaderboardAssessmentLevel,
                                                            @RequestPart(value = "extent") String leaderboardExtent,
                                                            @RequestPart(value = "anonymization") String leaderboardAnonymization,
                                                            @RequestPart(value = "studentVisible") Boolean leaderboardStudentVisible,
                                                            @RequestPart(value = "achievementId") Long achievementId,
                                                            @RequestPart(value = "gameSubjectAcronym") String gameSubjectAcronym,
                                                            @RequestPart(value = "gameCourse") Integer gameCourse,
                                                            @RequestPart(value = "gamePeriod") String gamePeriod) {
        LeaderboardDto savedLeaderboardDto = leaderboardService.createLeaderboard(leaderboardName, Date.valueOf(leaderboardStartDate), Date.valueOf(leaderboardEndDate), leaderboardAssessmentLevel, leaderboardExtent, leaderboardAnonymization, leaderboardStudentVisible, achievementId, gameSubjectAcronym, gameCourse, gamePeriod);
        return new ResponseEntity<>(savedLeaderboardDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get leaderboards", description = "Get all the leaderboards optionally filtered by a specific game. The game is identified by the subject acronym, course and period when given, being the period name a valid period type (Quadrimester1 or Quadrimester2) (if not given, leaderboards from all games are going to be retrieved). The leaderboards are returned as a list of LeaderboardDto objects.", tags = { "leaderboards" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: List of LeaderboardDto objects.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = LeaderboardDto.class)))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: (1) Game not fully identified (Missing: subject acronym, course or period); (2) The given game period name not a valid period type (Only available: Quadrimester1, Quadrimester2).", content = @Content),
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
