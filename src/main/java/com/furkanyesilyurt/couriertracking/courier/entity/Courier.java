package com.furkanyesilyurt.couriertracking.courier.entity;

import com.furkanyesilyurt.couriertracking.common.annotation.Numeric;
import com.furkanyesilyurt.couriertracking.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "deleteFilter", condition = "is_deleted = :isDeleted")
@Entity
@Table(name = "COURIER")
public class Courier extends BaseEntity {

    @Id
    @SequenceGenerator(name = "courier-generator", sequenceName = "COURIER_ID_SEQ")
    @GeneratedValue(generator = "courier-generator", strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "IDENTITY_NO", nullable = false)
    @NotBlank
    @Numeric
    private String identityNo;

    @Column(name = "FIRSTNAME", length = 50, nullable = false)
    private String firstName;

    @Column(name = "LASTNAME", length = 50, nullable = false)
    private String lastName;

    @Column(name = "TOTAL_DISTANCE", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalDistance = BigDecimal.ZERO;

    public void addTotalDistance(BigDecimal distance) {
        this.totalDistance = this.totalDistance.add(distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Courier)) {
            return false;
        }
        Courier that = (Courier) o;
        return id.equals(that.id)
                && identityNo.equals(that.identityNo)
                && firstName.equals(that.firstName)
                && lastName.equals(that.lastName);
    }

    @Override
    public int hashCode() {
        return 17 * id.hashCode() * identityNo.hashCode();
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", identityNo='" + identityNo + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

