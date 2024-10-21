package com.base.meta.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "permission")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Permission extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    private String action;
    @Column(name = "show_menu")
    private Boolean showMenu;
    private String description;
    @Column(name = "name_group")
    private String nameGroup;
    @Column(name = "permission_code")
    private String permissionCode;
    private Integer kind;
}
