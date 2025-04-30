package com.furkanyesilyurt.couriertracking.store.dto;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

@Builder
public record StoreInitializeDto(String name,
                                 Double lat,
                                 Double lng) implements Serializable {

    @Serial
    private static final long serialVersionUID = 8983829459366346704L;
}
