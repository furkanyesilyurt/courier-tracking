package com.furkanyesilyurt.couriertracking.common.converter;

import com.furkanyesilyurt.couriertracking.store.entity.Store;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreInitializeConverter {

    StoreInitializeConverter INSTANCE = Mappers.getMapper(StoreInitializeConverter.class);

    @Mapping(target = "name", source = "storeName")
    @Mapping(target = "location", source = "point")
    Store convertStoreInitializeDtoToStore(String storeName, Point point);
}
