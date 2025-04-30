package com.furkanyesilyurt.couriertracking.courier.dto;

import com.furkanyesilyurt.couriertracking.common.annotation.Numeric;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Schema(name = "Courier Request Model", description = "It is a courier dto that you can send as requested.")
public record CourierRequest(
        @Schema(description = "The courier's identity no should be 11 digits", example = "11111111111")
        @Numeric
        @NotBlank
        String identityNo,

        @Schema(description = "The courier's first name", example = "Furkan")
        @NotBlank
        String firstName,

        @Schema(description = "The courier's last name", example = "Yesilyurt")
        @NotBlank
        String lastName) implements Serializable {

    @Serial
    private static final long serialVersionUID = 6090883157746510739L;
}
