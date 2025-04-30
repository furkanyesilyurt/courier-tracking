package com.furkanyesilyurt.couriertracking.courier.event;

import com.furkanyesilyurt.couriertracking.courier.service.TotalTravelDistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TotalTravelDistanceSubscriber {

    private final TotalTravelDistanceService totalTravelDistanceService;

    @Async
    @EventListener
    public void handleTotalDistanceEvent(TotalTravelDistanceEvent totalTravelDistanceEvent) {
        totalTravelDistanceService.updateTotalDistance(totalTravelDistanceEvent.getLocation(), totalTravelDistanceEvent.getCourierId());
    }
}
