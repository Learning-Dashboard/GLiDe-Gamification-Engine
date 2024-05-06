package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.dto.AchievementCategoryDto;
import edu.upc.gessi.glidegamificationengine.service.AchievementCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achievement-categories")
public class AchievementCategoryController {

    @Autowired
    private AchievementCategoryService achievementCategoryService;

    @GetMapping("/initiate")
    public ResponseEntity<String> initiateAchievementCategories(){
        achievementCategoryService.initiateAchievementCategories();
        return ResponseEntity.ok("Achievement categories successfully initiated");
    }

    @GetMapping
    public ResponseEntity<List<AchievementCategoryDto>> getAllAchievementCategories(){
        List<AchievementCategoryDto> achievementCategoryDtos = achievementCategoryService.getAllAchievementCategories();
        return ResponseEntity.ok(achievementCategoryDtos);
    }

    @GetMapping("{name}")
    public ResponseEntity<AchievementCategoryDto> getAchievementCategoryByName(@PathVariable("name") String achievementCategoryName){
        AchievementCategoryDto achievementCategoryDto = achievementCategoryService.getAchievementCategoryByName(achievementCategoryName);
        return ResponseEntity.ok(achievementCategoryDto);
    }

}
