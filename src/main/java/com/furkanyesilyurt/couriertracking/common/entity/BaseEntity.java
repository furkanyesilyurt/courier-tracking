package com.furkanyesilyurt.couriertracking.common.entity;

import com.furkanyesilyurt.couriertracking.common.constant.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FilterDef(
        name = "deleteFilter",
        defaultCondition = "IS_DELETED = false",
        parameters = @ParamDef(name = "isDeleted", type = Boolean.class)
)
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -8889981236474736870L;

    @CreatedDate
    @Column(name = "CREATE_DATE", nullable = false, updatable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private String createdBy = UserType.USER.name();

    @LastModifiedDate
    @Column(name = "MODIFIED_DATE", insertable = false)
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    @Column(name = "MODIFIED_BY", insertable = false)
    private String modifiedBy;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Long version = 1L;

    @Column(name = "IS_DELETED", nullable = false)
    private Boolean isDeleted = Boolean.FALSE;

    public void delete() {
        this.isDeleted = Boolean.TRUE;
        this.lastModifiedDate = LocalDateTime.now();
        this.modifiedBy = UserType.USER.name();
        this.version++;
    }

    public void updateAuditFields() {
        this.lastModifiedDate = LocalDateTime.now();
        this.modifiedBy = UserType.USER.name();
        this.version++;
    }
}
