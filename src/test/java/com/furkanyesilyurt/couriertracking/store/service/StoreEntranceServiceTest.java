package com.furkanyesilyurt.couriertracking.store.service;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierLocationDto;
import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import com.furkanyesilyurt.couriertracking.courier.service.CourierService;
import com.furkanyesilyurt.couriertracking.store.converter.PointConverter;
import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import com.furkanyesilyurt.couriertracking.store.dto.StoreDto;
import com.furkanyesilyurt.couriertracking.store.dto.StoreEntranceDto;
import com.furkanyesilyurt.couriertracking.store.dto.StoreEntranceRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreEntranceServiceTest {

    @InjectMocks
    private StoreEntranceService storeEntranceService;

    @Mock
    private StoreEntranceRepository storeEntranceRepository;

    @Mock
    private StoreService storeService;

    @Mock
    private CourierService courierService;

    @Mock
    private StoreEntranceCheckerService checkerService;

    private Store store;
    private Courier courier;
    private StoreEntrance storeEntrance;
    private Point location;

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
    void shouldReturnAllStoreEntrances() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<StoreEntrance> storeEntrancePage = new PageImpl<>(List.of(storeEntrance), pageable, 1);

        when(storeEntranceRepository.findAll(pageable)).thenReturn(storeEntrancePage);

        Page<StoreEntranceDto> result = storeEntranceService.getAllStoreEntrances(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(storeEntranceRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldReturnStoreEntrancesByCourierId() {
        when(storeEntranceRepository.findAllByCourierId(courier.getId())).thenReturn(List.of(storeEntrance));

        List<StoreEntranceDto> result = storeEntranceService.getAllStoreEntrancesByCourierId(courier.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(storeEntrance.getId(), result.get(0).storeId());
    }


    @Test
    void shouldSaveStoreEntranceManually() {
        StoreEntranceRequest storeEntranceRequest = StoreEntranceRequest.builder().storeId(1L).courierId(1L).time(LocalDateTime.of(2020, 1, 1, 0, 0)).build();

        when(storeService.findStoreById(storeEntranceRequest.storeId())).thenReturn(store);
        when(courierService.findCourierById(storeEntranceRequest.courierId())).thenReturn(courier);

        storeEntranceService.saveStoreEntranceManually(storeEntranceRequest);

        verify(storeEntranceRepository, times(1)).save(any());
    }

    @Test
    void shouldSaveStoreEntrance() {
        StoreDto storeDto = StoreDto.builder().id(1L).name("Test Store").build();
        PointDto pointDto = PointConverter.convertPointToPointDto(location);
        CourierLocationDto courierLocationDto = CourierLocationDto.builder().courierId(1L).location(pointDto).time(storeEntrance.getTime().plusMinutes(2)).build();

        when(checkerService.getStoreWithinRange(courierLocationDto.location())).thenReturn(storeDto);
        when(checkerService.hasReentered(courier.getId(), store.getId(), courierLocationDto.time())).thenReturn(false);
        when(storeService.findStoreById(store.getId())).thenReturn(store);
        when(courierService.findCourierById(courier.getId())).thenReturn(courier);

        storeEntranceService.saveStoreEntrance(courierLocationDto);

        verify(storeEntranceRepository, times(1)).save(any());
    }

    @Test
    void shouldNotSaveStoreEntranceWhenNotInRange() {
        PointDto pointDto = PointConverter.convertPointToPointDto(location);
        CourierLocationDto courierLocationDto = CourierLocationDto.builder().courierId(1L).location(pointDto).time(storeEntrance.getTime().plusMinutes(2)).build();

        when(checkerService.getStoreWithinRange(courierLocationDto.location())).thenReturn(null);

        storeEntranceService.saveStoreEntrance(courierLocationDto);

        verify(storeEntranceRepository, times(0)).save(any());
    }

    @Test
    void shouldNotSaveStoreEntranceWhenIsReentered() {
        StoreDto storeDto = StoreDto.builder().id(1L).name("Test Store").build();
        PointDto pointDto = PointConverter.convertPointToPointDto(location);
        CourierLocationDto courierLocationDto = CourierLocationDto.builder().courierId(1L).location(pointDto).time(storeEntrance.getTime().plusMinutes(2)).build();

        when(checkerService.getStoreWithinRange(courierLocationDto.location())).thenReturn(storeDto);
        when(checkerService.hasReentered(courier.getId(), store.getId(), courierLocationDto.time())).thenReturn(true);

        storeEntranceService.saveStoreEntrance(courierLocationDto);

        verify(storeEntranceRepository, times(0)).save(any());
    }
}