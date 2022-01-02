package com.github.pdhbe.couponapi.domain.dto;

import com.github.pdhbe.couponapi.domain.ErrorInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseBodyDto {
    private boolean success;
    private Object response;
    private ErrorInfo error;
}
