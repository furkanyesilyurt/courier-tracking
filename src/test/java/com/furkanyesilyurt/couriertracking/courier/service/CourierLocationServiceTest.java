package com.furkanyesilyurt.couriertracking.courier.service;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierLocationDto;
import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import com.furkanyesilyurt.couriertracking.courier.entity.CourierLocation;
import com.furkanyesilyurt.couriertracking.courier.event.TotalTravelDistancePublisher;
import com.furkanyesilyurt.couriertracking.courier.repository.CourierLocationRepository;
import com.furkanyesilyurt.couriertracking.store.converter.PointConverter;
import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import com.furkanyesilyurt.couriertracking.store.event.StoreEntrancePublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Limit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourierLocationServiceTest {

    @InjectMocks
    private CourierLocationService courierLocationService;

    @Mock
    private CourierLocationRepository courierLocationRepository;

    @Mock
    private CourierService courierService;

    @Mock
    private StoreEntrancePublisher storeEntrancePublisher;

    @Mock
    private TotalTravelDistancePublisher totalTravelDistancePublisher;

    private Courier courier;
    private CourierLocationDto courierLocationDto;
    private CourierLocation courierLocation;


    @BeforeEach
    void setUp() {
        // Initialize any necessary objects or mocks here
        courier = Courier.builder().id(1L).identityNo("11111111111").firstName("firstname").lastName("lastname").totalDistance(BigDecimal.ZERO).build();
        Point location = new GeometryFactory().createPoint(new Coordinate(10.0, 10.0));
        PointDto locDto = PointConverter.convertPointToPointDto(location);
        LocalDateTime time = LocalDateTime.of(2020, 1, 1, 0, 0);
        courierLocationDto = CourierLocationDto.builder().courierId(courier.getId()).location(locDto).time(time).build();
        courierLocation = CourierLocation.builder().id(1L).courier(courier).location(location).time(time).build();
    }

    @Test
    void shouldSaveLocation() {
        when(courierService.findCourierById(courier.getId())).thenReturn(courier);

        courierLocationService.saveLocation(courierLocationDto);

        verify(courierLocationRepository).save(any());
        verify(storeEntrancePublisher).publishEvent(courierLocationDto);
        verify(totalTravelDistancePublisher).publishEvent(courierLocationDto);
    }

    @Test
    void shouldReturnLastLocationByCourierId() {
        when(courierLocationRepository.findCourierLocationByCourierIdOrderByCreateDateDesc(courier.getId(), Limit.of(1))).thenReturn(courierLocation);

        CourierLocation result = courierLocationService.findLastLocationByCourierId(courier.getId());

        verify(courierLocationRepository).findCourierLocationByCourierIdOrderByCreateDateDesc(courier.getId(), Limit.of(1));
        assertEquals(courierLocation, result);
    }

    @Test
    void shouldReturnNullWhenThereIsNoLastLocationByCourierId() {
        when(courierLocationRepository.findCourierLocationByCourierIdOrderByCreateDateDesc(courier.getId(), Limit.of(1))).thenReturn(null);

        CourierLocation result = courierLocationService.findLastLocationByCourierId(courier.getId());

        verify(courierLocationRepository).findCourierLocationByCourierIdOrderByCreateDateDesc(courier.getId(), Limit.of(1));
        assertNull(result);
    }
}