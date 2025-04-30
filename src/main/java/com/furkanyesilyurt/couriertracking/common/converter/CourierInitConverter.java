package com.furkanyesilyurt.couriertracking.common.converter;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierDto;
import com.furkanyesilyurt.couriertracking.courier.dto.CourierRequest;
import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourierInitConverter {
//
//    CourierInitConverter INSTANCE = Mappers.getMapper(CourierInitConverter.class);
//
//    Courier convertCourierRequestToCourier(CourierRequest courierRequest);
//
//    CourierDto convertCourierToCourierDto(Courier courier);
}

