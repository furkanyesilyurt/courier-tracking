package com.furkanyesilyurt.couriertracking.store.event;

import com.furkanyesilyurt.couriertracking.courier.dto.CourierLocationDto;
import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
@Setter
public class StoreEntranceEvent extends ApplicationEvent {

    private final PointDto location;
    private final Long courierId;
    private final LocalDateTime time;

    public StoreEntranceEvent(Object source, CourierLocationDto courierLocationDto) {
        super(source);
        this.location = courierLocationDto.location();
        this.courierId = courierLocationDto.courierId();
        this.time = courierLocationDto.time();
    }
}
