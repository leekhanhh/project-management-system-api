package com.base.meta.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "test_execution_turn")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TestExecutionTurn extends Auditable<String>{
    @Id
    @GeneratedValue(generator = "IdGenerator")
    @GenericGenerator(name = "IdGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    private Long id;
    @Column(name = "turn_number")
    private Integer turnNumber;
    @Column(name = "start_date")
    private Date planStartDate;
    @Column(name = "end_date")
    private Date planEndDate;
    @Column(name = "actual_start_date")
    private Date actualStartDate;
    @Column(name = "actual_end_date")
    private Date actualEndDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    private Account assignedDeveloper;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_execution_id")
    private TestExecution testExecution;
}
