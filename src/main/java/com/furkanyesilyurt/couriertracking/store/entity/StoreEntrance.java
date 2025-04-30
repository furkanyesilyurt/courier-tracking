package com.furkanyesilyurt.couriertracking.store.entity;

import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
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

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "deleteFilter")
@Entity
@Table(name = "STORE_ENTRANCE")
public class StoreEntrance extends BaseEntity {

    @Id
    @SequenceGenerator(name = "entrance-generator", sequenceName = "ENTRANCE_ID_SEQ")
    @GeneratedValue(generator = "entrance-generator", strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "COURIER_ID", referencedColumnName = "id", nullable = false)
    private Courier courier;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "STORE_ID", referencedColumnName = "id", nullable = false)
    private Store store;

    @Column(name = "TIME", nullable = false, updatable = false)
    private LocalDateTime time;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof StoreEntrance)) {
            return false;
        }
        StoreEntrance that = (StoreEntrance) o;
        return id.equals(that.id)
                && courier.equals(that.courier)
                && store.equals(that.store)
                && time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return 17 * id.hashCode() * courier.hashCode() * store.hashCode() * time.hashCode();
    }

    @Override
    public String toString() {
        return "StoreEntrance{" +
                "id=" + id +
                ", courier=" + courier +
                ", store=" + store +
                ", time=" + time +
                '}';
    }
}
