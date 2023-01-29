package com.taxcalc.exercise.core.util;

import com.taxcalc.exercise.web.dto.TaxCalculationResponse;
import org.junit.jupiter.api.Test;

import static com.taxcalc.exercise.util.TaxCalculatorHelper.calculateByGross;
import static com.taxcalc.exercise.util.TaxCalculatorHelper.calculateByNet;
import static com.taxcalc.exercise.util.TaxCalculatorHelper.calculateByVat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaxCalculatorHelperTest {
    // Since invalid values (zero or negative, are already handled in the validation function, for this exercise it is not necessary to test it)

    @Test
    public void calculateByGross_validInput_returnsCorrectResponse() {
        double gross = 100.0;
        int rate = 20;
        TaxCalculationResponse response = calculateByGross(gross, rate);

        double expectedNet = gross / 1.2;
        double expectedTax = gross - expectedNet;
        assertEquals(expectedNet, response.net());
        assertEquals(gross, response.gross());
        assertEquals(expectedTax, response.vat());
    }

    @Test
    public void calculateByGross_zeroGross_returnsCorrectResponse() {
        double gross = 0;
        int rate = 20;
        TaxCalculationResponse response = calculateByGross(gross, rate);

        assertEquals(0, response.net());
        assertEquals(gross, response.gross());
        assertEquals(0, response.vat());
    }

    @Test
    public void testCalculateByNet_validInput_returnsCorrectResponse() {
        TaxCalculationResponse response = calculateByNet(100, 20);
        assertEquals(100, response.net());
        assertEquals(120, response.gross());
        assertEquals(20, response.vat());
    }

    @Test
    public void testCalculateByVat_validInput_returnsCorrectResponse() {
        TaxCalculationResponse response = calculateByVat(10, 10);
        assertEquals(100, response.net());
        assertEquals(110, response.gross());
        assertEquals(10, response.vat());
    }
}