package com.taxcalc.exercise.core;

import com.taxcalc.exercise.exception.TaxRateNotSupportedException;
import com.taxcalc.exercise.domain.TaxType;
import com.taxcalc.exercise.web.dto.TaxCalculationResponse;

import static com.taxcalc.exercise.util.TaxCalculatorHelper.calculateByGross;
import static com.taxcalc.exercise.util.TaxCalculatorHelper.calculateByNet;
import static com.taxcalc.exercise.util.TaxCalculatorHelper.calculateByVat;

public abstract class AbstractTaxCalculator {
    protected final Double amount;
    protected final TaxType type;
    protected final Integer rate;

    protected AbstractTaxCalculator(Double amount, TaxType type, Integer rate) {
        this.amount = amount;
        this.type = type;
        this.rate = rate;
    }

    public abstract void validateTaxRate() throws TaxRateNotSupportedException;

    public TaxCalculationResponse calculate() {
        this.validateTaxRate();

        return switch (type) {
            case net -> calculateByNet(amount, rate);
            case gross -> calculateByGross(amount, rate);
            case vat -> calculateByVat(amount, rate);
        };
    }
}
