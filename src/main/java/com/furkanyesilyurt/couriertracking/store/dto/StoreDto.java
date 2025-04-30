package com.furkanyesilyurt.couriertracking.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Schema(name = "Store Dto", description = "Information about a store")
public record StoreDto(
        @Schema(description = "The store's id")
        Long id,

        @Schema(description = "The store's name")
        String name,

        @Schema(description = "The store's location")
        PointDto location) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1663314047807157355L;
}
