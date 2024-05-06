package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.dto.AchievementDto;
import edu.upc.gessi.glidegamificationengine.service.AchievementService;
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

    @PostMapping
    public ResponseEntity<AchievementDto> createAchievement(@RequestParam("name") String achievementName,
                                                            @RequestParam("icon") MultipartFile achievementIcon,
                                                            @RequestParam("achievementCategoryName") String achievementCategoryName) throws IOException {
        AchievementDto savedAchievementDto = achievementService.createAchievement(achievementName, achievementIcon, achievementCategoryName);
        return new ResponseEntity<>(savedAchievementDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AchievementDto>> getAllAchievements(){
        List<AchievementDto> achievementDtos = achievementService.getAllAchievements();
        return ResponseEntity.ok(achievementDtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<AchievementDto> getAchievementById(@PathVariable("id") Long achievementId) {
        AchievementDto achievementDto = achievementService.getAchievementById(achievementId);
        return ResponseEntity.ok(achievementDto);
    }

    @GetMapping("/{id}/icon")
    public ResponseEntity<?> getAchievementIconById(@PathVariable("id") Long achievementId) {
        AchievementDto achievementDto = achievementService.getAchievementById(achievementId);
        byte[] iconBytes = java.util.Base64.getDecoder().decode(achievementDto.getIcon());
        Tika tika = new Tika();
        String iconMimeType = tika.detect(iconBytes);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(iconMimeType)).body(iconBytes);
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

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAchievement(@PathVariable("id") Long achievementId) {
        achievementService.deleteAchievement(achievementId);
        return ResponseEntity.ok("Achievement with id '" + achievementId + "' successfully deleted");
    }
    */

}
