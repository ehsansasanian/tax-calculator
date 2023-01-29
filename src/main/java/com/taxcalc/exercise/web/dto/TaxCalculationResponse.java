package com.taxcalc.exercise.web.dto;

public record TaxCalculationResponse(Double net, Double gross, Double vat) {
}
