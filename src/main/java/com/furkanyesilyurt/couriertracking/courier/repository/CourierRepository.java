package com.furkanyesilyurt.couriertracking.courier.repository;

import com.furkanyesilyurt.couriertracking.common.annotation.EnableDeleteFilter;
import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    @EnableDeleteFilter
    Courier findCourierByIdentityNo(String identityNo);
}
