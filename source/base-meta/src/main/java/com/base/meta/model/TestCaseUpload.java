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
    @Column(name = "test_case_description")
    private String testCaseMenuPath;
    @Column(name = "test_case_menu_path")
    private String testStepsAction;
    @Column(name = "test_steps_data")
    private String testStepsData;
    @Column(name = "test_steps_expected_result")
    private String testStepsExpectedResult;
    @Column(name = "test_steps_actual_result")
    private String testStepsActualResult;
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "program_id")
    private Program program;
}
