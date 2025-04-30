package com.furkanyesilyurt.couriertracking.courier.controller;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierLocationDto;
import com.furkanyesilyurt.couriertracking.courier.service.CourierLocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/courier-location", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Courier Location Controller", description = "Here we can send a new courier location.")
public class CourierLocationController {

    private final CourierLocationService courierLocationService;

    @Operation(summary = "Send a new courier location.")
    @PostMapping
    public ResponseEntity<Void> saveLocation(@RequestBody @Valid CourierLocationDto courierLocationDto) {
        courierLocationService.saveLocation(courierLocationDto);
        return ResponseEntity.ok().build();
    }
}
