package com.base.meta.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "category")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Category extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    private Integer kind;
    private String name;
    private String code;
    private String description;
    private Integer ordering;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parentCategory;
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Category> categoryList;
}
