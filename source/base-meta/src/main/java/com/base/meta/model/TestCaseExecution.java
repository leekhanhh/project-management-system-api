package com.base.meta.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "test_case_execution")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TestCaseExecution extends Auditable<String>{
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_execution_turn_id")
    private TestExecutionTurn testExecutionTurn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_id")
    private TestCase testCase;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_suite_id")
    private TestSuite testSuite;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_category_id")
    private Category status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_code_category_id")
    private Category testExecutionTypeCode;
    @Column(name = "display_id")
    private String displayId;
}
