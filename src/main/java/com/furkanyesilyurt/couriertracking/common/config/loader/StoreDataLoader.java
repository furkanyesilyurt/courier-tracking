package com.furkanyesilyurt.couriertracking.common.config.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furkanyesilyurt.couriertracking.common.constant.UserType;
import com.furkanyesilyurt.couriertracking.common.converter.StoreInitializeConverter;
import com.furkanyesilyurt.couriertracking.store.converter.PointConverter;
import com.furkanyesilyurt.couriertracking.store.dto.PointDto;
import com.furkanyesilyurt.couriertracking.store.dto.StoreInitializeDto;
import com.furkanyesilyurt.couriertracking.store.entity.Store;
import com.furkanyesilyurt.couriertracking.store.repository.StoreRepository;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.List;

@Configuration
@Slf4j
public class StoreDataLoader {

    @Value("${store.initialize.filePath:store.json}")
    private String storeJsonPath;

    @Bean
    public ApplicationRunner loadStoreData(StoreRepository storeRepository) {
        return args -> {
            if (storeRepository.count() == 0) {
                try {
                    File file = new ClassPathResource(storeJsonPath).getFile();
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<StoreInitializeDto> storesDtos = List.of(objectMapper.readValue(file, StoreInitializeDto[].class));
                    List<Store> stores = storesDtos.stream()
                            .map(storeInitializeDto -> {
                                Point point = preparePoint(storeInitializeDto.lng(), storeInitializeDto.lat());
                                Store store = StoreInitializeConverter.INSTANCE.convertStoreInitializeDtoToStore(storeInitializeDto.name(), point);
                                store.setCreatedBy(UserType.SYSTEM.name());
                                return store;
                            })
                            .toList();

                    storeRepository.saveAll(stores);
                    log.info("Store.json file loaded successfully.");
                } catch (Exception ex) {
                    log.error("An error occurred while loading data. Please check the relevant json file or save manually: {}", storeJsonPath, ex);
                }
            } else {
                log.info("Store data already exists in the database, loading skipped.");
            }
        };
    }

    private Point preparePoint(double latitude, double longitude) {
        PointDto pointDto = PointDto.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();

        return PointConverter.convertPointDtoToPoint(pointDto);
    }
}
