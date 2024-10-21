package com.base.meta.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "test_step_execution")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TestStepExecution extends Auditable<String>{
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_step_id")
    private TestStep testStep;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_execution_id")
    private TestCaseExecution testCaseExecution;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category status;
    private Boolean isDefected = false;
}
