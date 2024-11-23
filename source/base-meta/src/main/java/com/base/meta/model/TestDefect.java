package com.base.meta.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "test_defect")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TestDefect extends Auditable<String>{
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_step_execution_id")
    private TestStepExecution testStepExecution;
    private String name;
    private String priority;
    private String severity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account assignedDeveloper;
    private String description;
    private Boolean sendEmailNotification = true;
    @Column(name = "display_id")
    private String displayId;
}
