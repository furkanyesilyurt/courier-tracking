package com.furkanyesilyurt.couriertracking.courier.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Schema(name = "Courier", description = "A courier object")
public record CourierDto(
        @Schema(description = "The courier's id")
        Long id,

        @Schema(description = "The courier's identity no")
        String identityNo,

        @Schema(description = "The courier's first name")
        String firstName,

        @Schema(description = "The courier's last name")
        String lastName,

        @Schema(description = "The courier's total distance")
        BigDecimal totalDistance) implements Serializable {

    @Serial
    private static final long serialVersionUID = 3751363137727275975L;
}
