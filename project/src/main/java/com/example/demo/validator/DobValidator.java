package com.example.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DobValidator implements ConstraintValidator<DateOfBirthConstraint, Date> {
    private int min;
    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null){
            return true;
        }

        long years = ChronoUnit.YEARS.between(((ChronoLocalDate) date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()), java.time.LocalDate.now());
        return years >= min;
    }

    @Override
    public void initialize(DateOfBirthConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.min = constraintAnnotation.min();
    }
}
