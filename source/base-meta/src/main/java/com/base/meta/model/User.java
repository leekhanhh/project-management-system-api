package com.base.meta.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "user")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "avatar_path")
    private String avatarPath;
}
