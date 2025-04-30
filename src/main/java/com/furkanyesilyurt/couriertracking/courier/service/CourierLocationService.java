package com.furkanyesilyurt.couriertracking.courier.service;

import com.furkanyesilyurt.couriertracking.common.annotation.EnableDeleteFilter;
import com.furkanyesilyurt.couriertracking.courier.dto.CourierLocationDto;
import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import com.furkanyesilyurt.couriertracking.courier.entity.CourierLocation;
import com.furkanyesilyurt.couriertracking.courier.repository.CourierLocationRepository;
import com.furkanyesilyurt.couriertracking.store.converter.PointConverter;
import com.furkanyesilyurt.couriertracking.store.event.StoreEntrancePublisher;
import com.furkanyesilyurt.couriertracking.courier.event.TotalTravelDistancePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.locationtech.jts.geom.Point;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CourierLocationService {

    private final CourierLocationRepository courierLocationRepository;
    private final CourierService courierService;
    private final StoreEntrancePublisher storeEntrancePublisher;
    private final TotalTravelDistancePublisher totalTravelDistancePublisher;

    public void saveLocation(CourierLocationDto courierLocationDto) {
        totalTravelDistancePublisher.publishEvent(courierLocationDto);
        storeEntrancePublisher.publishEvent(courierLocationDto);
        save(courierLocationDto);
    }

    private void save(CourierLocationDto courierLocationDto) {
        Courier courierById = courierService.findCourierById(courierLocationDto.courierId());
        Point point = PointConverter.convertPointDtoToPoint(courierLocationDto.location());

        CourierLocation courierLocation = CourierLocation.builder()
                .location(point)
                .courier(courierById)
                .build();
        courierLocationRepository.save(courierLocation);
        log.info("Current location information sent by the courier was saved.");
    }

    @EnableDeleteFilter
    public CourierLocation findLastLocationByCourierId(Long courierId) {
        CourierLocation courierLocation = courierLocationRepository.findCourierLocationByCourierIdOrderByCreateDateDesc(courierId, Limit.of(1));
        if (Objects.isNull(courierLocation)) {
            return null;
        }
        return courierLocation;
    }
}
