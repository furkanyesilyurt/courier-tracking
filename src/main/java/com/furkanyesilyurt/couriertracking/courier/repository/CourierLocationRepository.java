package com.furkanyesilyurt.couriertracking.courier.repository;

import com.furkanyesilyurt.couriertracking.courier.entity.CourierLocation;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierLocationRepository extends JpaRepository<CourierLocation, Long> {

    CourierLocation findCourierLocationByCourierIdOrderByCreateDateDesc(Long courierId, Limit limit);
}
