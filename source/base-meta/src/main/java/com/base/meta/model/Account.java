package com.base.meta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "account")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    private int kind;
    private String username;
    private String phone;
    private String email;
    @JsonIgnore
    private String password;
    @Column(name = "full_name")
    private String fullName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    @Column(name = "last_login")
    private Date lastLogin;
    @Column(name = "avatar_path")
    private String avatarPath;
    @Column(name = "reset_pwd_code")
    private String resetPwdCode;
    @Column(name = "reset_pwd_time")
    private Date resetPwdTime;
    @Column(name = "attempt_forget_pwd")
    private Integer attemptCode;
    @Column(name = "attempt_login")
    private Integer attemptLogin;
    @Column(name = "is_super_admin")
    private Boolean isSuperAdmin = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_category_id")
    private Category status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_category_id")
    private Category position;
}
