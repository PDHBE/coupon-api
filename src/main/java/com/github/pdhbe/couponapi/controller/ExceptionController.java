package com.github.pdhbe.couponapi.controller;

import com.github.pdhbe.couponapi.domain.*;
import com.github.pdhbe.couponapi.domain.dto.ResponseBodyDto;
import com.github.pdhbe.couponapi.service.CannotDownloadCouponException;
import com.github.pdhbe.couponapi.service.CannotUseCouponException;
import com.github.pdhbe.couponapi.service.DuplicatedCodeException;
import com.github.pdhbe.couponapi.service.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    /**
     * Custom Exception
     */

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseBodyDto> handleNotFoundException(NotFoundException notFoundException) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND.value(), notFoundException.getMessage());
        ResponseBodyDto responseBodyDto = createResponseBodyDto(errorInfo);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBodyDto);
    }

    @ExceptionHandler(CannotUseCouponException.class)
    public ResponseEntity<ResponseBodyDto> handleCannotUseCouponException(CannotUseCouponException cannotUseCouponException) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), cannotUseCouponException.getMessage());
        ResponseBodyDto responseBodyDto = createResponseBodyDto(errorInfo);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBodyDto);
    }

    @ExceptionHandler(CannotDownloadCouponException.class)
    public ResponseEntity<ResponseBodyDto> handleCannotDownloadCouponException(CannotDownloadCouponException cannotDownloadCouponException) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), cannotDownloadCouponException.getMessage());
        ResponseBodyDto responseBodyDto = createResponseBodyDto(errorInfo);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBodyDto);
    }

    @ExceptionHandler(CannotDisableException.class)
    public ResponseEntity<ResponseBodyDto> handleCannotDisableException(CannotDisableException cannotDisableException) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), cannotDisableException.getMessage());
        ResponseBodyDto responseBodyDto = createResponseBodyDto(errorInfo);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBodyDto);
    }

    @ExceptionHandler(CannotPublishException.class)
    public ResponseEntity<ResponseBodyDto> handleCannotPublishException(CannotPublishException cannotPublishException) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), cannotPublishException.getMessage());
        ResponseBodyDto responseBodyDto = createResponseBodyDto(errorInfo);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBodyDto);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ResponseBodyDto> handleInvalidDateException(InvalidDateException invalidDateException) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), invalidDateException.getMessage());
        ResponseBodyDto responseBodyDto = createResponseBodyDto(errorInfo);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBodyDto);
    }

    @ExceptionHandler(CannotModifyException.class)
    public ResponseEntity<ResponseBodyDto> handleCannotModifyException(CannotModifyException cannotModifyException) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), cannotModifyException.getMessage());
        ResponseBodyDto responseBodyDto = createResponseBodyDto(errorInfo);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBodyDto);
    }

    @ExceptionHandler(DuplicatedCodeException.class)
    public ResponseEntity<ResponseBodyDto> handleDuplicatedCodeException(DuplicatedCodeException duplicatedCodeException) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), duplicatedCodeException.getMessage());
        ResponseBodyDto responseBodyDto = createResponseBodyDto(errorInfo);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBodyDto);
    }

    @ExceptionHandler(NotIssuerException.class)
    public ResponseEntity<ResponseBodyDto> handleNotIssuerException(NotIssuerException notIssuerException) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.FORBIDDEN.value(), notIssuerException.getMessage());
        ResponseBodyDto responseBodyDto = createResponseBodyDto(errorInfo);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBodyDto);
    }

    /**
     * Spring Exception
     */

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ResponseBodyDto> handleMissingRequestHeaderException() {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.FORBIDDEN.value(), "Required request header 'X-USER-ID' is not present.");
        ResponseBodyDto responseBodyDto = createResponseBodyDto(errorInfo);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBodyDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseBodyDto> handleConstraintViolationException() {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), "Code is Not Valid.");
        ResponseBodyDto responseBodyDto = createResponseBodyDto(errorInfo);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBodyDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBodyDto> handleMethodArgumentNotValidException() {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), "Coupon Group is Not Valid.");
        ResponseBodyDto responseBodyDto = createResponseBodyDto(errorInfo);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBodyDto);
    }

    private ResponseBodyDto createResponseBodyDto(ErrorInfo errorInfo) {
        return ResponseBodyDto.builder()
                .success(false)
                .response(null)
                .error(errorInfo)
                .build();
    }
}
