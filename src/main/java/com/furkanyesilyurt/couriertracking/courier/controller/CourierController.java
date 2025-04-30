package com.furkanyesilyurt.couriertracking.courier.controller;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierDto;
import com.furkanyesilyurt.couriertracking.courier.dto.CourierRequest;
import com.furkanyesilyurt.couriertracking.courier.service.CourierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/api/v1/couriers", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Courier Controller", description = "Here we can list, save, delete and update couriers.")
public class CourierController {

    private final CourierService courierService;

    @Operation(
            summary = "Get all couriers",
            description = "Fetches a list of all couriers in the system.",
            responses = {
                    @ApiResponse(responseCode = "200"
                            , description = "Successfully fetched all couriers"
                            , content = @Content(mediaType = "application/json"
                            , schema = @Schema(implementation = CourierDto.class))),
                    @ApiResponse(responseCode = "404"
                            , description = "No couriers found"
                            , content = @Content(mediaType = "application/json"))
            }
    )
    @GetMapping
    public Page<CourierDto> getAllCouriers(@ParameterObject Pageable pageable) {
        return courierService.getAllCouriers(pageable);
    }

    @Operation(summary = "Save a courier")
    @PostMapping
    public ResponseEntity<Void> saveCourier(@RequestBody @Valid CourierRequest courierRequest) {
        courierService.saveCourier(courierRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update a courier")
    @PutMapping
    public ResponseEntity<CourierDto> updateCourier(@RequestBody @Valid CourierRequest courierRequest) {
        CourierDto courierDto = courierService.updateCourier(courierRequest);
        return ResponseEntity.ok(courierDto);
    }

    @Operation(summary = "Delete a courier by identity no")
    @DeleteMapping("/identity/{identity-no}")
    public ResponseEntity<Void> deleteCourierByIdentityNo(@PathVariable("identity-no") String identityNo) {
        courierService.deleteCourierByIdentityNo(identityNo);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a courier by id")
    @DeleteMapping("/{courier-id}")
    public ResponseEntity<Void> deleteCourierById(@PathVariable("courier-id") @NonNull Long courierId) {
        courierService.deleteCourierById(courierId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get total distance traveled by a courier")
    @GetMapping("/total-travel-distance/{courier-id}")
    public BigDecimal getTotalTravelDistance(@PathVariable("courier-id") @NonNull Long courierId) {
        return courierService.getTotalTravelDistance(courierId);
    }
}
