package com.taxcalc.exercise.util;

import com.taxcalc.exercise.web.dto.TaxCalculationResponse;

public final class TaxCalculatorHelper {
    public static TaxCalculationResponse calculateByGross(final double gross, final double rate) {
        if (rate == 0) return new TaxCalculationResponse(gross, gross, 0d);
        final double net = gross / (1 + (rate / 100));
        return new TaxCalculationResponse(net, gross, gross - net);
    }

    public static TaxCalculationResponse calculateByNet(final double net, final double rate) {
        if (rate == 0) return new TaxCalculationResponse(net, net, 0d);
        final double vat = net * (rate / 100);
        return new TaxCalculationResponse(net, net + vat, vat);
    }

    public static TaxCalculationResponse calculateByVat(final double vat, final double rate) {
        final double net = vat / (rate / 100);
        return new TaxCalculationResponse(net, net + vat, vat);
    }
}
