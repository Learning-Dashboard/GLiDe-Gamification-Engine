package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.dto.AchievementCategoryDto;
import edu.upc.gessi.glidegamificationengine.dto.AchievementDto;
import edu.upc.gessi.glidegamificationengine.service.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Operation(summary = "Create achievement", description = "Create a new achievement. The achievement name cannot be blank and must not be already used. The achievement icon is optional. The achievement category name must be a valid achievement category type (Points, Badges or Resources). The achievement is returned as an AchievementDto object.", tags = { "achievements" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED: AchievementDto object.", content = @Content(schema = @Schema(implementation = AchievementDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: The given achievement category name not a valid achievement category type (Only available: Points, Badges, Resources).", content = @Content),
            @ApiResponse(responseCode = "409", description = "CONFLICT: (1) Achievement name cannot be blank; (2) The given achievement name already used.", content = @Content)
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AchievementDto> createAchievement(@RequestPart(value = "name") String achievementName,
                                                            @RequestPart(value = "icon", required = false) MultipartFile achievementIcon,
                                                            @RequestPart(value = "category") String achievementCategory) throws IOException {
        AchievementDto savedAchievementDto = achievementService.createAchievement(achievementName, achievementIcon, achievementCategory);
        return new ResponseEntity<>(savedAchievementDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get achievements", description = "Get all the achievements. The achievements are returned as a list of AchievementDto objects.", tags = { "achievements" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: List of AchievementDto objects.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AchievementDto.class))))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AchievementDto>> getAchievements(){
        List<AchievementDto> achievementDtos = achievementService.getAchievements();
        return ResponseEntity.ok(achievementDtos);
    }

    @Operation(summary = "Get achievement", description = "Get an achievement. The achievement is identified by its id. The achievement is returned as an AchievementDto object.", tags = { "achievements" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: AchievementDto object.", content = @Content(schema = @Schema(implementation = AchievementDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Achievement with the given id not found.", content = @Content)
    })
    @GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AchievementDto> getAchievement(@PathVariable("id") Long achievementId) {
        AchievementDto achievementDto = achievementService.getAchievement(achievementId);
        return ResponseEntity.ok(achievementDto);
    }

    @Operation(summary = "Get achievement icon", description = "Get the icon of an achievement. The achievement is identified by its id. The icon is returned as a byte array.", tags = { "achievements" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Icon byte array.", content = @Content(mediaType = "image/*")),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Achievement with the given id not found.", content = @Content)
    })
    @GetMapping(value="/{id}/icon")
    public ResponseEntity<?> getAchievementIcon(@PathVariable("id") Long achievementId) {
        AchievementDto achievementDto = achievementService.getAchievement(achievementId);
        byte[] iconBytes = java.util.Base64.getDecoder().decode(achievementDto.getIcon());
        Tika tika = new Tika();
        String iconMimeType = tika.detect(iconBytes);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(iconMimeType)).body(iconBytes);
    }

    @Operation(summary = "Update achievement", description = "Update an achievement. The achievement is identified by its id. The achievement name cannot be blank and must not be already used. The achievement icon is optional. The achievement category name must be a valid achievement category type (Points, Badges or Resources). The achievement is returned as an AchievementDto object.", tags = { "achievements" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: AchievementDto object.", content = @Content(schema = @Schema(implementation = AchievementDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: The given achievement category name not a valid achievement category type (Only available: Points, Badges, Resources).", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Achievement with the given id not found.", content = @Content),
            @ApiResponse(responseCode = "409", description = "CONFLICT: (1) Achievement name cannot be blank; (2) The given achievement name already used.", content = @Content)
    })
    @PutMapping(value="{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AchievementDto> updateAchievement(@PathVariable("id") Long achievementId,
                                                            @RequestPart(value = "name") String achievementName,
                                                            @RequestPart(value = "icon", required = false) MultipartFile achievementIcon,
                                                            @RequestPart(value = "category") String achievementCategory) throws IOException {
        AchievementDto updatedAchievementDto = achievementService.updateAchievement(achievementId, achievementName, achievementIcon, achievementCategory);
        return ResponseEntity.ok(updatedAchievementDto);
    }

    @Operation(summary = "Delete achievement", description = "Delete an achievement. The achievement is identified by its id.", tags = { "achievements" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Success message.", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Achievement with the given id not found.", content = @Content)
    })
    @DeleteMapping(value="{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteAchievement(@PathVariable("id") Long achievementId) {
        achievementService.deleteAchievement(achievementId);
        return ResponseEntity.ok("Achievement with id '" + achievementId + "' successfully deleted.");
    }

    @Operation(summary = "Get achievement categories", description = "Get all the achievement categories. The achievement categories are returned as a list of AchievementCategoryDto objects.", tags = { "achievements" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: List of AchievementCategoryDto objects.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AchievementCategoryDto.class))))
    })
    @GetMapping(value="/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AchievementCategoryDto>> getAchievementCategories(){
        List<AchievementCategoryDto> achievementCategoryDtos = achievementService.getAchievementCategories();
        return ResponseEntity.ok(achievementCategoryDtos);
    }

    @Operation(summary = "Get achievement category", description = "Get an achievement category. The achievement category is identified by its name, which must be a valid achievement category type (Points, Badges or Resources). The achievement category is returned as an AchievementCategoryDto object.", tags = { "achievements" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: AchievementCategoryDto object.", content = @Content(schema = @Schema(implementation = AchievementCategoryDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: The given achievement category name not a valid achievement category type (Only available: Points, Badges, Resources).", content = @Content)
    })
    @GetMapping(value="/categories/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AchievementCategoryDto> getAchievementCategory(@PathVariable("name") String achievementCategoryName){
        AchievementCategoryDto achievementCategoryDto = achievementService.getAchievementCategory(achievementCategoryName);
        return ResponseEntity.ok(achievementCategoryDto);
    }

    /*
    @PostMapping
    public ResponseEntity<AchievementDto> createAchievement(@RequestBody AchievementDto achievementDto) {
        AchievementDto savedAchievementDto = achievementService.createAchievement(achievementDto);
        return new ResponseEntity<>(savedAchievementDto, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<AchievementDto> updateAchievement(@PathVariable("id") Long achievementId, @RequestBody AchievementDto updatedAchievementDto) {
        AchievementDto achievementDto = achievementService.updateAchievement(achievementId, updatedAchievementDto);
        return ResponseEntity.ok(achievementDto);
    }
    */

}
