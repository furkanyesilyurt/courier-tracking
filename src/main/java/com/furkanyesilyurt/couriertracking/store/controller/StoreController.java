package com.furkanyesilyurt.couriertracking.store.controller;

import com.furkanyesilyurt.couriertracking.store.dto.StoreDto;
import com.furkanyesilyurt.couriertracking.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/stores", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Store Controller", description = "Here we can list, save, delete and update stores.")
public class StoreController {

    private final StoreService storeService;

    @Operation(
            summary = "Get all stores",
            description = "Fetches a list of all stores in the system.",
            responses = {
                    @ApiResponse(responseCode = "200"
                            , description = "Successfully fetched all stores"
                            , content = @Content(mediaType = "application/json"
                            , schema = @Schema(implementation = StoreDto.class))),
                    @ApiResponse(responseCode = "404"
                            , description = "No stores found"
                            , content = @Content(mediaType = "application/json"))
            }
    )
    @GetMapping
    public Page<StoreDto> findAllStores(@ParameterObject Pageable pageable) {
        return storeService.findAllStoresForPage(pageable);
    }
}
