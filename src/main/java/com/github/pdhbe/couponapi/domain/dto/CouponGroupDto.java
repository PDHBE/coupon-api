package com.github.pdhbe.couponapi.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CouponGroupDto {
    @NotNull
    @Length(min = 2, max = 50)
    private String name;
    @NotNull
    @Min(1)
    private int amount;
    @NotNull
    @Min(1)
    private int max_count;
    @NotNull
    private String valid_from_dt;
    @NotNull
    private String valid_to_dt;
}
