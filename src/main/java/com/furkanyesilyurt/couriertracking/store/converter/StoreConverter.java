package com.furkanyesilyurt.couriertracking.store.converter;

import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import com.furkanyesilyurt.couriertracking.store.dto.StoreDto;
import com.furkanyesilyurt.couriertracking.store.entity.Store;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class StoreConverter {

    private StoreConverter() {
    }

    public static StoreDto convertStoreToStoreDto(Store store) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(store.getLocation().getY(), store.getLocation().getX()));
        PointDto pointDto = PointConverter.convertPointToPointDto(point);

        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .location(pointDto)
                .build();
    }
}
