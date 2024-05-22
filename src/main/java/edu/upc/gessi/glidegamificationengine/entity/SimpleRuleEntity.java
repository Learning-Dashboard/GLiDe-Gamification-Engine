package edu.upc.gessi.glidegamificationengine.entity;

import edu.upc.gessi.glidegamificationengine.type.RuleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "simple_rule")
@Check(constraints = "repetitions >= 1")
public class SimpleRuleEntity extends RuleEntity {

    @Column(name = "repetitions", nullable = false)
    private Integer repetitions;

    public SimpleRuleEntity(Long id, String name, GameEntity gameEntity, Integer repetitions) {
        super(id, name, RuleType.Simple, null, gameEntity);
        this.repetitions = repetitions;
    }

}
