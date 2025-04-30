package com.furkanyesilyurt.couriertracking.store.converter;

import com.furkanyesilyurt.couriertracking.store.dto.StoreDto;
import com.furkanyesilyurt.couriertracking.store.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreConverterr {

    StoreConverterr INSTANCE = Mappers.getMapper(StoreConverterr.class);

    StoreDto convertStoreToStoreDto(Store store);
}
