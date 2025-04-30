package com.furkanyesilyurt.couriertracking.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Schema(name = "Point Dto", description = "Information about a location")
public record PointDto(
        Double latitude,
        Double longitude) implements Serializable {

    @Serial
    private static final long serialVersionUID = 8628864563922046422L;
}
