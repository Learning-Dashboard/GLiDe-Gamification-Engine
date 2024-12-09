package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.dto.SubjectDTO;
import edu.upc.gessi.glidegamificationengine.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "http://localhost:4200")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @Operation(summary = "Get subjects", description = "Get all the subjects", tags = { "subjects" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: List of SubjectDTO objects", content= @Content(array = @ArraySchema(schema = @Schema(implementation = SubjectDTO.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SubjectDTO>> getSubjects() {
        List<SubjectDTO> subjectDtos = subjectService.getSubjects();
        return ResponseEntity.ok(subjectDtos);
    }
}
