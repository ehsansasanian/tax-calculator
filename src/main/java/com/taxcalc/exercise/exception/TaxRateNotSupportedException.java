package com.taxcalc.exercise.exception;

public class TaxRateNotSupportedException extends RuntimeException {
    private final static String MESSAGE = "Tax rate %s percent is not applicable for %s.";

    public TaxRateNotSupportedException(String country, Integer rate) {
        super(String.format(MESSAGE, rate, country));
    }
}
