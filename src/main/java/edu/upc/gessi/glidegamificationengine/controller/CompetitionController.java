package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.dto.CompetitionResultDto;
import edu.upc.gessi.glidegamificationengine.dto.wrapper.CompetitionResultDtoListEntry;
import edu.upc.gessi.glidegamificationengine.service.CompetitionService;
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
@RequestMapping("/api/competitions")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @Operation(summary = "Get competition results", description = "Get the results of a competition. The competition is identified by its id. The results are returned as a list of CompetitionResultDtoListEntry objects, which contain the competition extent (subject/group) identifiers and their respective competition results as list of CompetitionResultDto objects.", tags = { "competitions" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: List of CompetitionResultDtoListEntry objects, which contain the competition extent (subject/group) identifiers and their respective competition results as list of CompetitionResultDto objects.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CompetitionResultDtoListEntry.class)))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Competition with the given id not found.", content = @Content)
    })
    @GetMapping(value ="/{id}/results", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CompetitionResultDtoListEntry>> getCompetitionResults(@PathVariable("id") Long competitionId) {
        HashMap<String, List<CompetitionResultDto>> competitionResultDtos = competitionService.getCompetitionResults(competitionId);
        List<CompetitionResultDtoListEntry> competitionResultDtoListEntries = new ArrayList<>();
        competitionResultDtos.forEach((key, value) -> {
            CompetitionResultDtoListEntry entry = new CompetitionResultDtoListEntry();
            entry.setCompetitionExtentIdentifier(key);
            entry.setCompetitionResults(value);
            competitionResultDtoListEntries.add(entry);
        });
        return ResponseEntity.ok(competitionResultDtoListEntries);
    }
}
