package com.furkanyesilyurt.couriertracking.store.dto;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record StoreEntranceDto(
        Long id,
        Long storeId,
        Long courierId,
        LocalDateTime time) implements Serializable {

    @Serial
    private static final long serialVersionUID = -2718575757162970874L;
}
