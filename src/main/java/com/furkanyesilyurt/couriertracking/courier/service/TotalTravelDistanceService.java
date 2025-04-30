package com.furkanyesilyurt.couriertracking.courier.service;

import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import com.furkanyesilyurt.couriertracking.courier.entity.CourierLocation;
import com.furkanyesilyurt.couriertracking.common.service.DistanceCalculatorService;
import com.furkanyesilyurt.couriertracking.store.converter.PointConverter;
import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TotalTravelDistanceService {

    private final CourierService courierService;
    private final CourierLocationService courierLocationService;
    private final DistanceCalculatorService distanceCalculatorService;

    public void updateTotalDistance(PointDto location, Long courierId) {
        Courier courier = courierService.findCourierById(courierId);
        CourierLocation lastLocationOfCourier = courierLocationService.findLastLocationByCourierId(courierId);
        if (Objects.isNull(lastLocationOfCourier)) {
            return;
        }

        PointDto lastLocationDto = PointConverter.convertPointToPointDto(lastLocationOfCourier.getLocation());
        double distance = distanceCalculatorService.calculateDistance(lastLocationDto, location);

        courier.addTotalDistance(BigDecimal.valueOf(distance));
        log.info("Total distance of courier with id {} was updated as {}.", courierId, courier.getTotalDistance());
    }
}
