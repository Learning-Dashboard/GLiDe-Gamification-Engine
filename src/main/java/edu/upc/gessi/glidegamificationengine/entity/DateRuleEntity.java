package edu.upc.gessi.glidegamificationengine.entity;

import edu.upc.gessi.glidegamificationengine.type.RuleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "date_rule")
@Check(constraints = "repetitions >= 1 AND start_date <= end_date")
public class DateRuleEntity extends RuleEntity {

    @Column(name = "repetitions", nullable = false)
    private Integer repetitions;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    public DateRuleEntity(Long id, String name, Integer repetitions, Date startDate, Date endDate) {
        super(id, name, RuleType.DateRule, null);
        this.repetitions = repetitions;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
