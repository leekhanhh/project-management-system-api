package com.base.meta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "requirement")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Requirement extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acceptance_category_id")
    private Category acceptance;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "devision_category_id")
    private Category division;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "name_category_id")
    private Category name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_classification_category_id")
    private Category detailClassification;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id")
    private Project project;
    @Column(name = "display_id")
    private String displayId;
}
