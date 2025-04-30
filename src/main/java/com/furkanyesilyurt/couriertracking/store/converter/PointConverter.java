package com.furkanyesilyurt.couriertracking.store.converter;

import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.Objects;

public class PointConverter {

    private PointConverter() {
    }

    public static PointDto convertPointToPointDto(Point point) {
        if (Objects.isNull(point)) {
            return null;
        }

        return PointDto.builder()
                .latitude(point.getX())
                .longitude(point.getY())
                .build();
    }

    public static Point convertPointDtoToPoint(PointDto pointDto) {
        if (Objects.isNull(pointDto)) {
            return null;
        }

        GeometryFactory geometryFactory = new GeometryFactory();
        return geometryFactory.createPoint(new Coordinate(pointDto.latitude(), pointDto.longitude()));
    }
}
