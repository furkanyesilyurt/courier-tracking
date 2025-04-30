package com.furkanyesilyurt.couriertracking.store.converter;

import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import com.furkanyesilyurt.couriertracking.store.dto.StoreEntranceDto;
import com.furkanyesilyurt.couriertracking.store.entity.Store;
import com.furkanyesilyurt.couriertracking.store.entity.StoreEntrance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreEntranceConverter {

    StoreEntranceConverter INSTANCE = Mappers.getMapper(StoreEntranceConverter.class);

    @Mapping(target = "id", source = "storeEntrance.id")
    @Mapping(target = "courierId", source = "courier.id")
    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "time", source = "storeEntrance.time")
    StoreEntranceDto convertStoreEntranceToStoreEntranceDto(StoreEntrance storeEntrance, Courier courier, Store store);
}
