package com.furkanyesilyurt.couriertracking.store.controller;

import com.furkanyesilyurt.couriertracking.store.dto.StoreEntranceDto;
import com.furkanyesilyurt.couriertracking.store.dto.StoreEntranceRequest;
import com.furkanyesilyurt.couriertracking.store.service.StoreEntranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store-entrances")
@RequiredArgsConstructor
@Tag(name = "Store Entrance Controller", description = "Here we can list and save store entrances.")
public class StoreEntranceController {

    private final StoreEntranceService storeEntranceService;

    @Operation(summary = "Get all store entrances.")
    @GetMapping
    public Page<StoreEntranceDto> getAllStoreEntrances(@ParameterObject Pageable pageable) {
        return storeEntranceService.getAllStoreEntrances(pageable);
    }

    @Operation(summary = "Get store entrances by courier id.")
    @GetMapping("/{courier-id}")
    public ResponseEntity<List<StoreEntranceDto>> getStoreEntrancesByCourierId(@PathVariable("courier-id") @NonNull Long courierId) {
        List<StoreEntranceDto> allStoreEntrancesByCourierId = storeEntranceService.getAllStoreEntrancesByCourierId(courierId);
        if (allStoreEntrancesByCourierId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allStoreEntrancesByCourierId);
    }

    @Operation(summary = "Save a store entrance manually.")
    @PostMapping
    public ResponseEntity<Void> saveEntrance(@RequestBody @Valid StoreEntranceRequest storeEntranceRequest) {
        storeEntranceService.saveStoreEntranceManually(storeEntranceRequest);
        return ResponseEntity.ok().build();
    }
}
