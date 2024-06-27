package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.dto.DateRuleDto;
import edu.upc.gessi.glidegamificationengine.dto.SimpleRuleDto;
import edu.upc.gessi.glidegamificationengine.service.RuleService;
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
@RequestMapping("/api/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @Operation(summary = "Get simple rules", description = "Get all the simple rules optionally filtered by a specific game. The game is identified by the subject acronym, course and period when given, being the period name a valid period type (Quadrimester1 or Quadrimester2) (if not given, simple rules from all games are going to be retrieved). The simple rules are returned as a list of SimpleRuleDto objects.", tags = { "rules" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: List of SimpleRuleDto objects.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SimpleRuleDto.class)))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: (1) Game not fully identified (Missing: subject acronym, course or period); (2) The given period name not a valid period type (Only available: Quadrimester1, Quadrimester2).", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Game with the given subject acronym, course and period not found.", content = @Content)
    })
    @GetMapping(value="/simples", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SimpleRuleDto>> getSimpleRules(@RequestParam(value = "gameSubjectAcronym", required = false) String gameSubjectAcronym,
                                                              @RequestParam(value = "gameCourse", required = false) Integer gameCourse,
                                                              @RequestParam(value = "gamePeriod", required = false) String gamePeriod) {
        List<SimpleRuleDto> simpleRuleDtos = ruleService.getSimpleRules(gameSubjectAcronym, gameCourse, gamePeriod);
        return ResponseEntity.ok(simpleRuleDtos);
    }

    @Operation(summary = "Get simple rule", description = "Get a simple rule. The simple rule is identified by its id. The simple rule is returned as a SimpleRuleDto object.", tags = { "rules" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: SimpleRuleDto object.", content = @Content(schema = @Schema(implementation = SimpleRuleDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Simple rule with the given id not found.", content = @Content)
    })
    @GetMapping(value="/simples/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleRuleDto> getSimpleRule(@PathVariable("id") Long simpleRuleId) {
        SimpleRuleDto simpleRuleDto = ruleService.getSimpleRule(simpleRuleId);
        return ResponseEntity.ok(simpleRuleDto);
    }

    @Operation(summary = "Get date rules", description = "Get all the date rules optionally filtered by a specific game. The game is identified by the subject acronym, course and period when given, being the period name a valid period type (Quadrimester1 or Quadrimester2) (if not given, date rules from all games are going to be retrieved). The date rules are returned as a list of DateRuleDto objects.", tags = { "rules" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: List of DateRuleDto objects.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DateRuleDto.class)))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: (1) Game not fully identified (Missing: subject acronym, course or period); (2) The given period name not a valid period type (Only available: Quadrimester1, Quadrimester2).", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Game with the given subject acronym, course and period not found.", content = @Content)
    })
    @GetMapping(value="/dates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DateRuleDto>> getDateRules(@RequestParam(value = "gameSubjectAcronym", required = false) String gameSubjectAcronym,
                                                          @RequestParam(value = "gameCourse", required = false) Integer gameCourse,
                                                          @RequestParam(value = "gamePeriod", required = false) String gamePeriod) {
        List<DateRuleDto> dateRuleDtos = ruleService.getDateRules(gameSubjectAcronym, gameCourse, gamePeriod);
        return ResponseEntity.ok(dateRuleDtos);
    }

    @Operation(summary = "Get date rule", description = "Get a date rule. The date rule is identified by its id. The date rule is returned as a DateRuleDto object.", tags = { "rules" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: DateRuleDto object.", content = @Content(schema = @Schema(implementation = DateRuleDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Date rule with the given id not found.", content = @Content)
    })
    @GetMapping(value="/dates/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DateRuleDto> getDateRule(@PathVariable("id") Long dateRuleId) {
        DateRuleDto dateRuleDto = ruleService.getDateRule(dateRuleId);
        return ResponseEntity.ok(dateRuleDto);
    }

}
