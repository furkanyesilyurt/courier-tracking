package com.furkanyesilyurt.couriertracking.common.service;

import com.furkanyesilyurt.couriertracking.store.dto.PointDto;

@FunctionalInterface
public interface DistanceCalculatorService {

    double calculateDistance(PointDto point1, PointDto point2);
}
