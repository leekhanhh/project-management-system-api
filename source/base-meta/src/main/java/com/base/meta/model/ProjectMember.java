package com.base.meta.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "project_member")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class ProjectMember extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @MapsId
    private Project project;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @MapsId
    private Account account;
    @Column(name = "on_boarded_date")
    private Date onBoardedDate;
    @Column(name = "off_boarded_date")
    private Date offBoardedDate;
}
