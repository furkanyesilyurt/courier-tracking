package com.furkanyesilyurt.couriertracking.store.dto;

import lombok.Builder;
import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record StoreEntranceRequest(
        @NonNull
        Long storeId,

        @NonNull
        Long courierId,

        @NonNull
        LocalDateTime time) implements Serializable {

    @Serial
    private static final long serialVersionUID = -2718575757162970874L;
}
