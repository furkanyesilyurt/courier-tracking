package com.furkanyesilyurt.couriertracking.courier.converter;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierDto;
import com.furkanyesilyurt.couriertracking.courier.dto.CourierRequest;
import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourierConverter {

    CourierConverter INSTANCE = Mappers.getMapper(CourierConverter.class);

    @Mapping(target = "totalDistance", constant = "0")
    Courier convertCourierRequestToCourier(CourierRequest courierRequest);

    CourierDto convertCourierToCourierDto(Courier courier);
}
