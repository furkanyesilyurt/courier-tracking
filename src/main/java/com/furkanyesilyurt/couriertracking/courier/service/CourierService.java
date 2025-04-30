package com.furkanyesilyurt.couriertracking.courier.service;

import com.furkanyesilyurt.couriertracking.courier.converter.CourierConverter;
import com.furkanyesilyurt.couriertracking.courier.dto.CourierDto;
import com.furkanyesilyurt.couriertracking.courier.dto.CourierRequest;
import com.furkanyesilyurt.couriertracking.courier.exception.CourierAlreadyExist;
import com.furkanyesilyurt.couriertracking.courier.exception.CourierNotFoundException;
import com.furkanyesilyurt.couriertracking.courier.repository.CourierRepository;
import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import com.furkanyesilyurt.couriertracking.common.annotation.EnableDeleteFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CourierService {

    private final CourierRepository courierRepository;

    @EnableDeleteFilter
    @Transactional(readOnly = true)
    public Page<CourierDto> getAllCouriers(Pageable pageable) {
        return courierRepository.findAll(pageable)
                .map(CourierConverter.INSTANCE::convertCourierToCourierDto);
    }

    @EnableDeleteFilter
    public Courier findCourierById(Long id) {
        return courierRepository.findById(id).orElseThrow(CourierNotFoundException::new);
    }

    @EnableDeleteFilter
    private Courier findCourierByIdentityNo(String identityNo) {
        Courier courier = courierRepository.findCourierByIdentityNo(identityNo);
        if (Objects.isNull(courier)) {
            throw new CourierNotFoundException();
        }
        return courier;
    }

    public void saveCourier(CourierRequest courierRequest) {
        Courier courier = courierRepository.findCourierByIdentityNo(courierRequest.identityNo());
        if (Objects.nonNull(courier)) {
            throw new CourierAlreadyExist();
        }

        Courier newCourier = CourierConverter.INSTANCE.convertCourierRequestToCourier(courierRequest);
        courierRepository.save(newCourier);
        log.info("Courier with identity number {} saved", courierRequest.identityNo());
    }

    public CourierDto updateCourier(CourierRequest courierRequest) {
        Courier courier = findCourierByIdentityNo(courierRequest.identityNo());
        courier.setFirstName(courierRequest.firstName());
        courier.setLastName(courierRequest.lastName());
        courier.updateAuditFields();

        Courier updatedCourier = courierRepository.save(courier);
        return CourierConverter.INSTANCE.convertCourierToCourierDto(updatedCourier);
    }

    public void deleteCourierByIdentityNo(String identityNo) {
        Courier courier = findCourierByIdentityNo(identityNo);
        courier.delete();
        courierRepository.save(courier);
        log.info("Courier with identity number {} was deleted", identityNo);
    }

    public void deleteCourierById(Long id) {
        Courier courier = findCourierById(id);
        courier.delete();
        courierRepository.save(courier);
        log.info("Courier with id {} was deleted", id);
    }

    public BigDecimal getTotalTravelDistance(Long courierId) {
        Courier courier = findCourierById(courierId);
        return courier.getTotalDistance();
    }
}
