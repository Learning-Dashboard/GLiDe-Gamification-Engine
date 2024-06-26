package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.service.EvaluableActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/evaluableActions")
public class EvaluableActionController {

    @Autowired
    private EvaluableActionService evaluableActionService;

    @Operation(summary = "Initiate evaluable actions", description = "Initiate the evaluable actions usable from the Learning Dashboard source data tool, which all belong to the Player Performance category.", tags = { "evaluableActions" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Success message.", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping(value="/initiate", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> initiateEvaluableActions() {
        evaluableActionService.initiateEvaluableActions();
        return ResponseEntity.ok("Evaluable actions successfully initiated.");
    }

}
