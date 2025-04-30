package com.furkanyesilyurt.couriertracking.common.service;

import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HaversineDistanceCalculatorServiceTest {

    @InjectMocks
    private HaversineDistanceCalculatorService haversineDistanceCalculatorService;

    @Test
    void shouldCalculateDistance() {
        PointDto pointDto1 = new PointDto(52.2296756, 21.0122287);
        PointDto pointDto2 = new PointDto(41.8919300, 12.5113300);

        double distance = haversineDistanceCalculatorService.calculateDistance(pointDto1, pointDto2);

        System.out.println("Distance: " + distance + " km");
    }
}