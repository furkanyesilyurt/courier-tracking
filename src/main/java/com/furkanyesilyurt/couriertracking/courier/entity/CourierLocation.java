package com.furkanyesilyurt.couriertracking.courier.entity;

import com.furkanyesilyurt.couriertracking.common.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "deleteFilter")
@Entity
@Table(name = "COURIER_LOCATION")
public class CourierLocation extends BaseEntity {

    @Id
    @SequenceGenerator(name = "location-generator", sequenceName = "LOCATION_ID_SEQ")
    @GeneratedValue(generator = "location-generator", strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "LOCATION", nullable = false, updatable = false)
    private Point location;

    @Column(name = "TIME", updatable = false)
    private LocalDateTime time = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "COURIER_ID", referencedColumnName = "id", nullable = false, updatable = false)
    private Courier courier;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof CourierLocation)) {
            return false;
        }
        CourierLocation that = (CourierLocation) o;
        return id.equals(that.id)
                && location.equals(that.location)
                && time.equals(that.time)
                && courier.equals(that.courier);
    }

    @Override
    public int hashCode() {
        return 17 * location.hashCode() * time.hashCode() * courier.hashCode();
    }

    @Override
    public String toString() {
        return "CourierCurrentLocation{" +
                "id=" + id +
                ", location=" + location +
                ", time=" + time +
                ", courier=" + courier +
                '}';
    }
}
