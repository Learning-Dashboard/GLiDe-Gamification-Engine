package edu.upc.gessi.glidegamificationengine.controller;

import edu.upc.gessi.glidegamificationengine.dto.PlayerAchievementDto;
import edu.upc.gessi.glidegamificationengine.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{playername}/achievements")
    public ResponseEntity<List<PlayerAchievementDto>> getAchievements(@PathVariable("playername") String playerPlayername, @RequestParam("category") String achivementCategory) {
        List<PlayerAchievementDto> playerAchievementDtos = playerService.getAchievements(playerPlayername, achivementCategory);
        return ResponseEntity.ok(playerAchievementDtos);
    }

}
