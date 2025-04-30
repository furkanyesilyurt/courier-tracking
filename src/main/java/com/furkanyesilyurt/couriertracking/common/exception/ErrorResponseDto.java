package com.furkanyesilyurt.couriertracking.common.exception;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record ErrorResponseDto(
        int status,
        String message,
        LocalDateTime time,
        String stackTrace) implements Serializable {

    @Serial
    private static final long serialVersionUID = -5144007721307805620L;
}
