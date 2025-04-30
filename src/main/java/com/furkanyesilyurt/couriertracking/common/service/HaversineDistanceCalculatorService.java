package com.furkanyesilyurt.couriertracking.common.service;

import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class HaversineDistanceCalculatorService implements DistanceCalculatorService {

    private static final double EARTH_RADIUS_KM = 6371;

    @Override
    public double calculateDistance(PointDto point1, PointDto point2) {
        double lat1Rad = Math.toRadians(point1.latitude());//tersle
        double lon1Rad = Math.toRadians(point1.longitude());
        double lat2Rad = Math.toRadians(point2.latitude());
        double lon2Rad = Math.toRadians(point2.longitude());

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.pow(Math.sin(deltaLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}
