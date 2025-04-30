package com.furkanyesilyurt.couriertracking.courier.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Schema(name = "Courier Location Dto", description = "Information about the courier's current location")
public record CourierLocationDto(
        @NonNull PointDto location,
        @NonNull Long courierId,
        @JsonIgnore LocalDateTime time) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1663314047807157355L;

    public CourierLocationDto {
        time = LocalDateTime.now();
    }
}
