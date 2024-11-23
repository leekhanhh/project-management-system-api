package com.base.meta.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "test_case_upload")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TestCaseUpload extends Auditable<String>{
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @Column(name = "test_case_precondition")
    private String testCasePrecondition;
    @Column(name = "test_case_name")
    private String testCaseName;
    @Column(name = "test_case_menu_path")
    private String testCaseMenuPath;
    @Column(name = "test_step_action")
    private String testStepAction;
    @Column(name = "test_step_data")
    private String testStepData;
    @Column(name = "test_step_expected_result")
    private String testStepExpectedResult;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "program_id")
    private Program program;
}
