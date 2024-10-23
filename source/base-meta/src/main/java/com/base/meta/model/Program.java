package com.base.meta.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "program")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Program extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    private String name;
    private String description;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_category_id")
    private Category programType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_category_id")
    private Category programStatus;
    @Column(name = "program_category")
    private String programCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_account_id")
    private Account programOwner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dev_account_id")
    private Account assignedDeveloper;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tester_account_id")
    private Account assignedTester;
}
