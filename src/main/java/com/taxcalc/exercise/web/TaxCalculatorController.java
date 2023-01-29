package com.taxcalc.exercise.web;

import com.taxcalc.exercise.core.AbstractTaxCalculator;
import com.taxcalc.exercise.core.impl.AustriaTaxCalculator;
import com.taxcalc.exercise.domain.TaxType;
import com.taxcalc.exercise.web.dto.TaxCalculationResponse;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tax-calculator")
@Validated
public class TaxCalculatorController {

    /* TaxCalculator is not a singleton bean. Thus, on each request it is initialized and finally garbage collected by the GC
     Alternatively, would be a good approach to create a factory method (e.g. TaxCalculatorFactory) and annotate it with @Service.
     Then the singleton bean, for each coming request instantiates an appropriate implementation of AbstractTaxCalculator and
     invokes calculate function.
     In the given exercise, I tried to implement the simplest approach.
     */
    @GetMapping
    public ResponseEntity<TaxCalculationResponse> calculateTax(
            @RequestParam(value = "tax-type") TaxType type,
            @RequestParam @Positive(message = "amount must be positive") Double amount,
            @RequestParam @Positive(message = "rate must be positive") Integer rate) {
        AbstractTaxCalculator taxCalculator = new AustriaTaxCalculator(amount, type, rate);
        return ResponseEntity.ok(taxCalculator.calculate());
    }
}