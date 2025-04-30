package com.furkanyesilyurt.couriertracking.courier.service;

import com.furkanyesilyurt.couriertracking.common.service.DistanceCalculatorService;
import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import com.furkanyesilyurt.couriertracking.courier.entity.CourierLocation;
import com.furkanyesilyurt.couriertracking.store.converter.PointConverter;
import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TotalTravelDistanceServiceTest {

    @InjectMocks
    private TotalTravelDistanceService totalTravelDistanceService;

    @Mock
    private CourierService courierService;

    @Mock
    private CourierLocationService courierLocationService;

    @Mock
    private DistanceCalculatorService distanceCalculatorService;

    @Test
    void shouldUpdateTotalTravelDistance() {
        Courier courier = Courier.builder().id(1L).identityNo("11111111111").firstName("firstname").lastName("lastname").totalDistance(BigDecimal.ZERO).build();
        Point location = new GeometryFactory().createPoint(new Coordinate(10.0, 10.0));
        LocalDateTime time = LocalDateTime.of(2020, 1, 1, 0, 0);
        CourierLocation courierLocation = CourierLocation.builder().id(1L).courier(courier).location(location).time(time).build();
        PointDto loc1 = PointConverter.convertPointToPointDto(courierLocation.getLocation());
        PointDto loc2 = PointConverter.convertPointToPointDto(location);

        when(courierService.findCourierById(courier.getId())).thenReturn(courier);
        when(courierLocationService.findLastLocationByCourierId(courier.getId())).thenReturn(courierLocation);
        when(distanceCalculatorService.calculateDistance(loc2, loc1)).thenReturn(1.0);

        totalTravelDistanceService.updateTotalDistance(loc1, courier.getId());

        assertEquals(1, courier.getTotalDistance().intValue());
    }

    @Test
    void shouldNotUpdateTotalTravelDistanceWhenHasNoLastLocation() {
        Courier courier = Courier.builder().id(1L).identityNo("11111111111").firstName("firstname").lastName("lastname").totalDistance(BigDecimal.ZERO).build();
        Point location = new GeometryFactory().createPoint(new Coordinate(10.0, 10.0));
        LocalDateTime time = LocalDateTime.of(2020, 1, 1, 0, 0);
        CourierLocation courierLocation = CourierLocation.builder().id(1L).courier(courier).location(location).time(time).build();
        PointDto loc1 = PointConverter.convertPointToPointDto(courierLocation.getLocation());
        PointDto loc2 = PointConverter.convertPointToPointDto(location);

        when(courierService.findCourierById(courier.getId())).thenReturn(courier);
        when(courierLocationService.findLastLocationByCourierId(courier.getId())).thenReturn(null);

        totalTravelDistanceService.updateTotalDistance(loc1, courier.getId());

        assertEquals(0, courier.getTotalDistance().intValue());
    }
}