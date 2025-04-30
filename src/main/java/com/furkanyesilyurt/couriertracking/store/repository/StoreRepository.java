package com.furkanyesilyurt.couriertracking.store.repository;

import com.furkanyesilyurt.couriertracking.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
