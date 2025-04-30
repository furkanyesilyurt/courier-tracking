package com.furkanyesilyurt.couriertracking.store.service;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierLocationDto;
import com.furkanyesilyurt.couriertracking.courier.service.CourierService;
import com.furkanyesilyurt.couriertracking.common.annotation.EnableDeleteFilter;
import com.furkanyesilyurt.couriertracking.store.converter.StoreEntranceConverter;
import com.furkanyesilyurt.couriertracking.store.dto.StoreDto;
import com.furkanyesilyurt.couriertracking.store.dto.StoreEntranceDto;
import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import com.furkanyesilyurt.couriertracking.store.dto.StoreEntranceRequest;
import com.furkanyesilyurt.couriertracking.store.entity.Store;
import com.furkanyesilyurt.couriertracking.store.entity.StoreEntrance;
import com.furkanyesilyurt.couriertracking.store.repository.StoreEntranceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StoreEntranceService {

    private final StoreEntranceRepository storeEntranceRepository;
    private final StoreService storeService;
    private final CourierService courierService;
    private final StoreEntranceCheckerService checkerService;

    @EnableDeleteFilter
    public Page<StoreEntranceDto> getAllStoreEntrances(Pageable pageable) {
        return storeEntranceRepository.findAll(pageable)
                .map(entrance -> StoreEntranceConverter.INSTANCE
                        .convertStoreEntranceToStoreEntranceDto(entrance, entrance.getCourier(), entrance.getStore()));
    }

    @EnableDeleteFilter
    public List<StoreEntranceDto> getAllStoreEntrancesByCourierId(Long courierId) {
        return storeEntranceRepository.findAllByCourierId(courierId).stream()
                .map(entrance -> StoreEntranceConverter.INSTANCE
                        .convertStoreEntranceToStoreEntranceDto(entrance, entrance.getCourier(), entrance.getStore()))
                .toList();
    }

    public void saveStoreEntranceManually(StoreEntranceRequest storeEntranceRequest) {
        StoreEntrance storeEntrance = prepareStoreEntrance(storeEntranceRequest.courierId(), storeEntranceRequest.storeId(), storeEntranceRequest.time());
        storeEntranceRepository.save(storeEntrance);
        log.info("Store entrance information was saved manually.");
    }

    public void saveStoreEntrance(CourierLocationDto courierLocationDto) {
        StoreDto storeWithinRange = checkerService.getStoreWithinRange(courierLocationDto.location());
        if (Objects.isNull(storeWithinRange)) {
            return;
        }

        boolean hasReentered = checkerService.hasReentered(courierLocationDto.courierId(), storeWithinRange.id(), courierLocationDto.time());
        if (hasReentered) {
            return;
        }

        StoreEntrance storeEntrance = prepareStoreEntrance(courierLocationDto.courierId(), storeWithinRange.id(), courierLocationDto.time());
        storeEntranceRepository.save(storeEntrance);
        log.info("A courier named {} with ID {} entered the store named {} with ID {}.",
                storeEntrance.getCourier().getFirstName(),
                storeEntrance.getCourier().getId(),
                storeEntrance.getStore().getName(),
                storeEntrance.getStore().getId());
    }

    private StoreEntrance prepareStoreEntrance(Long courierId, Long storeId, LocalDateTime time) {
        Store store = storeService.findStoreById(storeId);
        Courier courier = courierService.findCourierById(courierId);

        return StoreEntrance.builder()
                .store(store)
                .courier(courier)
                .time(time)
                .build();
    }
}
