package com.furkanyesilyurt.couriertracking.store.repository;

import com.furkanyesilyurt.couriertracking.store.entity.StoreEntrance;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreEntranceRepository extends JpaRepository<StoreEntrance, Long> {

//    StoreEntrance findStoreEntranceByStoreIdAndCourierIdOrderByTimeDesc(Long storeId, Long courierId);

    StoreEntrance findStoreEntranceByStoreIdAndCourierIdOrderByCreateDateDesc(Long storeId, Long courierId, Limit limit);

    List<StoreEntrance> findAllByCourierId(Long courierId);
}
