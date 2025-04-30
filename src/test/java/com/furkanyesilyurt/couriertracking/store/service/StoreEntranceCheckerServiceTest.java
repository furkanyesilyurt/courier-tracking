package com.furkanyesilyurt.couriertracking.store.service;

import com.furkanyesilyurt.couriertracking.common.service.DistanceCalculatorService;
import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import com.furkanyesilyurt.couriertracking.store.converter.PointConverter;
import com.furkanyesilyurt.couriertracking.store.converter.StoreConverter;
import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import com.furkanyesilyurt.couriertracking.store.dto.StoreDto;
import com.furkanyesilyurt.couriertracking.store.entity.Store;
import com.furkanyesilyurt.couriertracking.store.entity.StoreEntrance;
import com.furkanyesilyurt.couriertracking.store.repository.StoreEntranceRepository;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreEntranceCheckerServiceTest {

    @InjectMocks
    private StoreEntranceCheckerService storeEntranceCheckerService;

    @Mock
    private StoreEntranceRepository storeEntranceRepository;

    @Mock
    private StoreService storeService;

    @Mock
    private DistanceCalculatorService distanceCalculatorService;

    private Store store;
    private Point location;
    private Courier courier;
    private StoreEntrance storeEntrance;

    @BeforeEach
    void setUp() {
        // Initialize any necessary objects or mocks here
        location = new GeometryFactory().createPoint(new Coordinate(10.0, 10.0));
        store = Store.builder().id(1L).name("Test Store").location(location).build();
        courier = Courier.builder().id(1L).identityNo("11111111111").firstName("firstname").lastName("lastname").build();
        LocalDateTime time = LocalDateTime.of(2020, 1, 1, 0, 0);
        storeEntrance = StoreEntrance.builder().id(1L).store(store).courier(courier).time(time).build();
    }

    @Test
    void shouldReturnNearestStoreIfWithinRange() {
        StoreDto storeDto = StoreConverter.convertStoreToStoreDto(store);
        PointDto courierLocation = PointConverter.convertPointToPointDto(location);
        when(storeService.findAllStores()).thenReturn(List.of(storeDto));
        when(distanceCalculatorService.calculateDistance(courierLocation, storeDto.location())).thenReturn(0.01);

        var result = storeEntranceCheckerService.getStoreWithinRange(courierLocation);

        assertThat(result).isEqualTo(storeDto);
    }

    @Test
    void shouldReturnNullIfOutsideRange() {
        StoreDto storeDto = StoreConverter.convertStoreToStoreDto(store);
        PointDto courierLocation = PointConverter.convertPointToPointDto(location);
        when(storeService.findAllStores()).thenReturn(List.of(storeDto));
        when(distanceCalculatorService.calculateDistance(courierLocation, storeDto.location())).thenReturn(0.2);

        var result = storeEntranceCheckerService.getStoreWithinRange(courierLocation);

        assertThat(result).isNull();
    }

    @Test
    void shouldReturnTrueWhenIsReentered() {
        LocalDateTime localDateTime = storeEntrance.getTime().plusSeconds(30);
        when(storeEntranceRepository.findStoreEntranceByStoreIdAndCourierIdOrderByCreateDateDesc(store.getId(), courier.getId(), Limit.of(1))).thenReturn(storeEntrance);

        boolean result = storeEntranceCheckerService.hasReentered(courier.getId(), store.getId(), localDateTime);

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenIsNotReentered() {
        LocalDateTime localDateTime = storeEntrance.getTime().plusMinutes(30);
        when(storeEntranceRepository.findStoreEntranceByStoreIdAndCourierIdOrderByCreateDateDesc(store.getId(), courier.getId(), Limit.of(1))).thenReturn(storeEntrance);

        boolean result = storeEntranceCheckerService.hasReentered(courier.getId(), store.getId(), localDateTime);

        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnFalseWhenHasNoEntered() {
        LocalDateTime localDateTime = storeEntrance.getTime().plusMinutes(30);
        when(storeEntranceRepository.findStoreEntranceByStoreIdAndCourierIdOrderByCreateDateDesc(store.getId(), courier.getId(), Limit.of(1))).thenReturn(null);

        boolean result = storeEntranceCheckerService.hasReentered(courier.getId(), store.getId(), localDateTime);

        assertThat(result).isFalse();
    }
}