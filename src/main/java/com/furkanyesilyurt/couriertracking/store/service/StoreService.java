package com.furkanyesilyurt.couriertracking.store.service;

import com.furkanyesilyurt.couriertracking.common.annotation.EnableDeleteFilter;
import com.furkanyesilyurt.couriertracking.store.converter.StoreConverter;
import com.furkanyesilyurt.couriertracking.store.dto.StoreDto;
import com.furkanyesilyurt.couriertracking.store.entity.Store;
import com.furkanyesilyurt.couriertracking.store.exception.StoreNotFoundException;
import com.furkanyesilyurt.couriertracking.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    @EnableDeleteFilter
    public Page<StoreDto> findAllStoresForPage(Pageable pageable) {
        return storeRepository.findAll(pageable)
                .map(StoreConverter::convertStoreToStoreDto);
    }

    @EnableDeleteFilter
    public List<StoreDto> findAllStores() {
        return storeRepository.findAll().stream()
                .map(StoreConverter::convertStoreToStoreDto)
                .toList();
    }

    @EnableDeleteFilter
    public Store findStoreById(Long id) {
        return storeRepository.findById(id).orElseThrow(StoreNotFoundException::new);
    }
}
