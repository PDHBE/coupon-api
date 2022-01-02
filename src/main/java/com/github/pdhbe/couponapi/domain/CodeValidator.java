//package com.github.pdhbe.couponapi.domain;
//
//import lombok.RequiredArgsConstructor;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//import java.util.regex.Pattern;
//
//@RequiredArgsConstructor
//public class CodeValidator implements ConstraintValidator<CodeConstraint,String> {
//
//    private final CouponGroupRepository couponGroupRepository;
//
//    @Override
//    public boolean isValid(String code, ConstraintValidatorContext constraintValidatorContext) {
//        return Pattern.matches("\\w{2,50}",code) && !couponGroupRepository.existsByCode(code);
//    }
//}
