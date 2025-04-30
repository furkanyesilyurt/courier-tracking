package com.furkanyesilyurt.couriertracking.courier.service;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierDto;
import com.furkanyesilyurt.couriertracking.courier.dto.CourierRequest;
import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import com.furkanyesilyurt.couriertracking.courier.exception.CourierAlreadyExist;
import com.furkanyesilyurt.couriertracking.courier.exception.CourierNotFoundException;
import com.furkanyesilyurt.couriertracking.courier.repository.CourierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourierServiceTest {

    @InjectMocks
    private CourierService courierService;

    @Mock
    private CourierRepository courierRepository;

    private Pageable pageable;
    private Courier courier;

    @BeforeEach
    void setUp() {
        // Initialize any necessary data or mocks here
        pageable = PageRequest.of(0, 10);
        courier = Courier.builder().identityNo("11111111111").firstName("firstname").lastName("lastname").totalDistance(BigDecimal.ZERO).build();
        courier.setId(1L);
    }

    @Test
    void shouldReturnAllCouriers() {
        Page<Courier> courierPage = new PageImpl<>(List.of(courier), pageable, 1);

        when(courierRepository.findAll(pageable)).thenReturn(courierPage);

        Page<CourierDto> result = courierService.getAllCouriers(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void shouldReturnCourierById() {
        when(courierRepository.findById(courier.getId())).thenReturn(Optional.of(courier));

        Courier result = courierService.findCourierById(courier.getId());

        assertNotNull(result);
        assertEquals(courier, result);
    }

    @Test
    void shouldThrowAnExceptionWhenFindCourierById() {
        when(courierRepository.findById(courier.getId())).thenReturn(Optional.empty());

        assertThrows(CourierNotFoundException.class, () -> {
            courierService.findCourierById(courier.getId());
        });
    }

    @Test
    void shouldSaveCourier() {
        CourierRequest courierRequest = CourierRequest.builder().identityNo("11111111111").firstName("firstname").lastName("lastname").build();

        when(courierRepository.findCourierByIdentityNo(courierRequest.identityNo())).thenReturn(null);

        courierService.saveCourier(courierRequest);

        verify(courierRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowAnExceptionIfCourierAlreadyExist() {
        CourierRequest courierRequest = CourierRequest.builder().identityNo("11111111111").firstName("firstname").lastName("lastname").build();

        when(courierRepository.findCourierByIdentityNo(courierRequest.identityNo())).thenReturn(courier);

        assertThrows(CourierAlreadyExist.class, () -> {
            courierService.saveCourier(courierRequest);
        });
    }

    @Test
    void shouldUpdateCourier() {
        CourierRequest courierRequest = CourierRequest.builder().identityNo("11111111111").firstName("firstname").lastName("lastname").build();

        when(courierRepository.findCourierByIdentityNo(courierRequest.identityNo())).thenReturn(courier);
        when(courierRepository.save(courier)).thenReturn(courier);

        CourierDto result = courierService.updateCourier(courierRequest);

        assertNotNull(result);
        assertEquals(courier.getId(), result.id());
    }

    @Test
    void shouldDeleteCourierById() {
        when(courierRepository.findById(courier.getId())).thenReturn(Optional.of(courier));

        courierService.deleteCourierById(courier.getId());

        verify(courierRepository, times(1)).save(courier);
    }

    @Test
    void shouldDeleteCourierByIdentityNo() {
        when(courierRepository.findCourierByIdentityNo(courier.getIdentityNo())).thenReturn(courier);

        courierService.deleteCourierByIdentityNo(courier.getIdentityNo());

        verify(courierRepository, times(1)).save(courier);
    }

    @Test
    void shouldThrowAnExceptionWhenDeleteCourierById() {
        when(courierRepository.findById(courier.getId())).thenReturn(Optional.empty());

        assertThrows(CourierNotFoundException.class, () -> {
            courierService.deleteCourierById(courier.getId());
        });
    }

    @Test
    void shouldThrowAnExceptionWhenDeleteCourierByIdentityNo() {
        when(courierRepository.findCourierByIdentityNo(courier.getIdentityNo())).thenReturn(null);

        assertThrows(CourierNotFoundException.class, () -> {
            courierService.deleteCourierByIdentityNo(courier.getIdentityNo());
        });
    }

    @Test
    void shouldReturnTotalTravelDistance() {
        when(courierRepository.findById(courier.getId())).thenReturn(Optional.of(courier));

        BigDecimal result = courierService.getTotalTravelDistance(courier.getId());

        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result);
    }
}