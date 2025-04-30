package com.furkanyesilyurt.couriertracking.store.entity;

import com.furkanyesilyurt.couriertracking.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.locationtech.jts.geom.Point;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "deleteFilter")
@Entity
@Table(name = "STORE")
public class Store extends BaseEntity {

    @Id
    @SequenceGenerator(name = "store-generator", sequenceName = "STORE_ID_SEQ")
    @GeneratedValue(generator = "store-generator", strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LOCATION", nullable = false)
    private Point location;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Store)) {
            return false;
        }
        Store that = (Store) o;
        return id.equals(that.id)
                && name.equals(that.name)
                && location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return 17 * id.hashCode() * name.hashCode() * location.hashCode();
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location=" + location +
                '}';
    }
}
