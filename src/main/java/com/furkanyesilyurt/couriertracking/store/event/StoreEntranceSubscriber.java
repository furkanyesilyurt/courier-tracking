package com.furkanyesilyurt.couriertracking.store.event;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierLocationDto;
import com.furkanyesilyurt.couriertracking.store.service.StoreEntranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreEntranceSubscriber {

    private final StoreEntranceService storeEntranceService;

    @Async
    @EventListener
    public void handleStoreEntranceEvent(StoreEntranceEvent storeEntranceEvent) {
        CourierLocationDto courierLocationDto = CourierLocationDto.builder()
                .location(storeEntranceEvent.getLocation())
                .courierId(storeEntranceEvent.getCourierId())
                .time(storeEntranceEvent.getTime())
                .build();

        storeEntranceService.saveStoreEntrance(courierLocationDto);
    }
}
