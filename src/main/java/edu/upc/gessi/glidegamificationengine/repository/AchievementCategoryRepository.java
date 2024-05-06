package edu.upc.gessi.glidegamificationengine.repository;

import edu.upc.gessi.glidegamificationengine.entity.AchievementCategoryEntity;
import edu.upc.gessi.glidegamificationengine.type.AchievementCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementCategoryRepository extends JpaRepository<AchievementCategoryEntity, AchievementCategoryType> {
}
