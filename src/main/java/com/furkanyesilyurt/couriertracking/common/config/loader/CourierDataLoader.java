package com.furkanyesilyurt.couriertracking.common.config.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furkanyesilyurt.couriertracking.common.constant.UserType;
import com.furkanyesilyurt.couriertracking.common.converter.CourierInitConverter;
import com.furkanyesilyurt.couriertracking.courier.converter.CourierConverter;
import com.furkanyesilyurt.couriertracking.courier.dto.CourierRequest;
import com.furkanyesilyurt.couriertracking.courier.entity.Courier;
import com.furkanyesilyurt.couriertracking.courier.repository.CourierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class CourierDataLoader {

    @Value("${courier.initialize.filePath:courier.json}")
    private String courierJsonPath;

    @Bean
    public ApplicationRunner loadCourierData(CourierRepository courierRepository) {
        return args -> {
            if (courierRepository.count() == 0) {
                try {
                    File file = new ClassPathResource(courierJsonPath).getFile();
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<CourierRequest> courierDtos = List.of(objectMapper.readValue(file, CourierRequest[].class));

                    List<Courier> couriers = courierDtos.stream()
                            .map(courierDto -> {
                                Courier courier = CourierConverter.INSTANCE.convertCourierRequestToCourier(courierDto);
                                courier.setCreatedBy(UserType.SYSTEM.name());
                                courier.setTotalDistance(BigDecimal.ZERO);
                                return courier;
                            })
                            .toList();

                    courierRepository.saveAll(couriers);
                    log.info("courier.json file loaded successfully.");
                } catch (Exception ex) {
                    log.error("An error occurred while loading data. Please check the relevant json file or save manually: {}", courierJsonPath, ex);
                }
            } else {
                log.info("Courier data already exists in the database, loading skipped.");
            }
        };
    }
}
