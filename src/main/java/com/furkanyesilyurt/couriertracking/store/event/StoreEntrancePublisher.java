package com.furkanyesilyurt.couriertracking.store.event;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierLocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreEntrancePublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(CourierLocationDto courierLocationDto) {
        StoreEntranceEvent storeEntranceEvent = new StoreEntranceEvent(this, courierLocationDto);
        applicationEventPublisher.publishEvent(storeEntranceEvent);
    }
}
