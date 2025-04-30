package com.furkanyesilyurt.couriertracking.store.service;

import com.furkanyesilyurt.couriertracking.store.dto.StoreDto;
import com.furkanyesilyurt.couriertracking.store.entity.Store;
import com.furkanyesilyurt.couriertracking.store.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @Test
    void findAllStoresForPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Store store = Store.builder()
                .id(1L)
                .name("Test Store")
                .location(new GeometryFactory().createPoint(new Coordinate(10.0, 10.0)))
                .build();
        Page<Store> storePage = new PageImpl<>(List.of(store), pageable, 1);

        when(storeRepository.findAll(pageable)).thenReturn(storePage);

        Page<StoreDto> result = storeService.findAllStoresForPage(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(storeRepository, times(1)).findAll(pageable);
    }

    @Test
    void findAllStores() {
        Store store = Store.builder()
                .id(1L)
                .name("Test Store")
                .location(new GeometryFactory().createPoint(new Coordinate(10.0, 10.0)))
                .build();
        List<Store> stores = Arrays.asList(store);

        when(storeRepository.findAll()).thenReturn(stores);

        List<StoreDto> result = storeService.findAllStores();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(storeRepository, times(1)).findAll();
    }

    @Test
    void findStoreById() {
        Store store = Store.builder()
                .id(1L)
                .name("Test Store")
                .location(new GeometryFactory().createPoint(new Coordinate(10.0, 10.0)))
                .build();

        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));

        Store result = storeService.findStoreById(store.getId());

        assertNotNull(result);
        verify(storeRepository, times(1)).findById(store.getId());
    }
}