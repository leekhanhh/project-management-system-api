package com.base.meta.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "test_defect_fixed_result")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TestDefectFixedResult extends Auditable<String>{
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.base.meta.service.id.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_defect_id")
    private TestDefect testDefect;
    @Column(name = "action_start_date")
    private Date actionStartDate;
    private String description;
    private String remark;
    @Column(name = "display_id")
    private String displayId;
}
