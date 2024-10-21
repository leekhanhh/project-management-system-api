package com.base.meta.model;

import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "test_suite")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TestSuite extends Auditable<String>{
    @Id
    @GenericGenerator(strategy = "com.base.meta.service.id.IdGenerator", name = "idGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "test_plan_id")
    private TestPlan testPlan;
    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "account_id")
    private Account account;
}
