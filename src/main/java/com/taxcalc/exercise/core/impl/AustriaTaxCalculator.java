package com.taxcalc.exercise.core.impl;

import com.taxcalc.exercise.core.AbstractTaxCalculator;
import com.taxcalc.exercise.exception.TaxRateNotSupportedException;
import com.taxcalc.exercise.domain.TaxType;

public class AustriaTaxCalculator extends AbstractTaxCalculator {
    public AustriaTaxCalculator(Double amount, TaxType type, Integer rate) {
        super(amount, type, rate);
    }

    @Override
    public void validateTaxRate() throws TaxRateNotSupportedException {
        if (rate != 10 && rate != 13 && rate != 20) throw new TaxRateNotSupportedException("Austria", rate);
    }
}
