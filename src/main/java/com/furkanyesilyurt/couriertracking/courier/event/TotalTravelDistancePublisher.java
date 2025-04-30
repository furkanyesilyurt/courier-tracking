package com.furkanyesilyurt.couriertracking.courier.event;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierLocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TotalTravelDistancePublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(CourierLocationDto courierLocationDto) {
        TotalTravelDistanceEvent totalTravelDistanceEvent = new TotalTravelDistanceEvent(this, courierLocationDto);
        applicationEventPublisher.publishEvent(totalTravelDistanceEvent);
    }
}
