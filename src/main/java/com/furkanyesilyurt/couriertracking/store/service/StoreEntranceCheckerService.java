package com.furkanyesilyurt.couriertracking.store.service;

import com.furkanyesilyurt.couriertracking.common.service.DistanceCalculatorService;
import com.furkanyesilyurt.couriertracking.store.constant.StoreEntranceConstants;
import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import com.furkanyesilyurt.couriertracking.store.dto.StoreDto;
import com.furkanyesilyurt.couriertracking.store.dto.StoreEntranceDto;
import com.furkanyesilyurt.couriertracking.store.entity.StoreEntrance;
import com.furkanyesilyurt.couriertracking.store.repository.StoreEntranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StoreEntranceCheckerService {

    private final StoreEntranceRepository storeEntranceRepository;
    private final StoreService storeService;
    private final DistanceCalculatorService distanceCalculatorService;

    public StoreDto getStoreWithinRange(PointDto courierLocation) {
        return storeService.findAllStores().stream()
                .filter(storeDto -> {
                    double distance = distanceCalculatorService.calculateDistance(courierLocation, storeDto.location());
                    return distance < StoreEntranceConstants.STORE_RANGE;
                })
                .findFirst()
                .orElse(null);
    }

    public boolean hasReentered(Long courierId, Long storeId, LocalDateTime time) {
        StoreEntranceDto lastEntrance = findLastEntrance(courierId, storeId);
        if (Objects.isNull(lastEntrance)) {
            return false;
        }

        return lastEntrance.time().plusSeconds(StoreEntranceConstants.ONE_MINUTE_THRESHOLD).isAfter(time);
    }

    private StoreEntranceDto findLastEntrance(Long courierId, Long storeId) {
        StoreEntrance lastEntrance = storeEntranceRepository.findStoreEntranceByStoreIdAndCourierIdOrderByCreateDateDesc(storeId, courierId, Limit.of(1));
        if (Objects.isNull(lastEntrance)) {
            return null;
        }

        return StoreEntranceDto.builder()
                .id(lastEntrance.getId())
                .storeId(lastEntrance.getStore().getId())
                .courierId(lastEntrance.getCourier().getId())
                .time(lastEntrance.getTime())
                .build();
    }
}
