package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.dto.AchievementDto;
import edu.upc.gessi.glidegamificationengine.service.AchievementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    private AchievementService achievementService;

    @PostMapping
    public ResponseEntity<AchievementDto> createAchievement(@RequestBody AchievementDto achievementDto) {
        AchievementDto savedAchievementDto = achievementService.createAchievement(achievementDto);
        return new ResponseEntity<>(savedAchievementDto, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<AchievementDto> getAchievementById(@PathVariable("id") Long achievementId) {
        AchievementDto achievementDto = achievementService.getAchievementById(achievementId);
        return ResponseEntity.ok(achievementDto);
    }

    @GetMapping
    public ResponseEntity<List<AchievementDto>> getAllAchievements(){
        List<AchievementDto> achievementDtos = achievementService.getAllAchievements();
        return ResponseEntity.ok(achievementDtos);
    }

    @PutMapping("{id}")
    public ResponseEntity<AchievementDto> updateAchievement(@PathVariable("id") Long achievementId, @RequestBody AchievementDto updatedAchievementDto) {
        AchievementDto achievementDto = achievementService.updateAchievement(achievementId, updatedAchievementDto);
        return ResponseEntity.ok(achievementDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAchievement(@PathVariable("id") Long achievementId) {
        achievementService.deleteAchievement(achievementId);
        return ResponseEntity.ok("Successfully deleted achievement with id " + achievementId);
    }
}
