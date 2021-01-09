package com.josep.hris.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity(name = "job_level")
public class JobLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @NotEmpty
    @Size(max = 50)
    private String code;

    @NotEmpty
    @Size(max = 255)
    private String name;

    @OneToOne
    private JobLevel parent;

    @OneToOne(mappedBy = "parent")
    private JobLevel child;

    private Integer sort;

    private Integer status;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="created_by", nullable=false)
    @JsonIgnore
    private Users createdBy;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="updated_by", nullable=false)
    @JsonIgnore
    private Users updatedBy;
}
