package com.furkanyesilyurt.couriertracking.courier.event;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierLocationDto;
import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class TotalTravelDistanceEvent extends ApplicationEvent {

    private final PointDto location;
    private final Long courierId;

    public TotalTravelDistanceEvent(Object source, CourierLocationDto courierLocationDto) {
        super(source);
        this.location = courierLocationDto.location();
        this.courierId = courierLocationDto.courierId();
    }
}
