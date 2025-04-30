package com.furkanyesilyurt.couriertracking.common.exception;

import com.furkanyesilyurt.couriertracking.courier.exception.CourierAlreadyExist;
import com.furkanyesilyurt.couriertracking.courier.exception.CourierNotFoundException;
import com.furkanyesilyurt.couriertracking.store.exception.StoreNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
@Hidden
@RequiredArgsConstructor
@Slf4j
public class CommonExceptionHandler {

    private final MessageSource messageSource;

    @Value("${app.debug:false}")
    private boolean debug;

    @ExceptionHandler(CourierNotFoundException.class)
    public ResponseEntity<Object> handleCourierNotFound(CourierNotFoundException ex) {
        String message = messageSource.getMessage("error.courier.not.found", null, LocaleContextHolder.getLocale());
        log.error(message);
        ErrorResponseDto error = ErrorResponseDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(message)
                .time(LocalDateTime.now())
                .stackTrace(getStackTrace(ex))
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourierAlreadyExist.class)
    public ResponseEntity<Object> handleCourierAlreadyExist(CourierAlreadyExist ex) {
        String message = messageSource.getMessage("error.courier.exist", null, LocaleContextHolder.getLocale());
        log.error(message);
        ErrorResponseDto error = ErrorResponseDto.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(message)
                .time(LocalDateTime.now())
                .stackTrace(getStackTrace(ex))
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<Object> handleStoreNotFound(StoreNotFoundException ex) {
        String message = messageSource.getMessage("error.store.not.found", null, LocaleContextHolder.getLocale());
        log.error(message);
        ErrorResponseDto error = ErrorResponseDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(message)
                .time(LocalDateTime.now())
                .stackTrace(getStackTrace(ex))
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    private String getStackTrace(Exception ex) {
        return debug ? Arrays.toString(ex.getStackTrace()) : StringUtils.EMPTY;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(Exception ex) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
