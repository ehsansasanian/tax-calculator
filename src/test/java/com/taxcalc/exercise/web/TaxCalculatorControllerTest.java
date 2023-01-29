package com.taxcalc.exercise.web;

import com.taxcalc.exercise.web.dto.TaxCalculationResponse;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaxCalculatorControllerTest {
    private final static String URL = "/tax-calculator";
    @Resource
    private TestRestTemplate testRestTemplate;

    @Test
    public void calculateTax_MissingAllParams_WillReturnBadRequest() {
        ResponseEntity<String> response = testRestTemplate.getForEntity(URL, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void calculateTax_MissingTaxTypeParam_WillReturnBadRequest() {
        ResponseEntity<Void> response = testRestTemplate.getForEntity(URL + "?amount=100&rate=13", Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void calculateTax_MissingAmountParam_WillReturnBadRequest() {
        ResponseEntity<Void> response = testRestTemplate.getForEntity(URL + "?tax-type=net&rate=13", Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void calculateTax_MissingRateParam_WillReturnBadRequest() {
        ResponseEntity<Void> response = testRestTemplate.getForEntity(URL + "?tax-type=net&amount=100", Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    // Similarly, the rest of the params can be tested.
    @Test
    public void calculateTax_WithNet_WillReturnOK() {
        ResponseEntity<TaxCalculationResponse> response = testRestTemplate.getForEntity(URL + "?tax-type=net&amount=100&rate=13", TaxCalculationResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        assertThat(response.getBody().gross()).isEqualTo(113);
        assertThat(response.getBody().net()).isEqualTo(100);
        assertThat(response.getBody().vat()).isEqualTo(13);
    }

    @Test
    public void calculateTax_WithNegativeAmount_WillReturnBadRequest() {
        ResponseEntity<Void> response = testRestTemplate.getForEntity(URL + "?tax-type=net&amount=-100&rate=13", Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void calculateTax_WithIrrelevantRate_WillReturnBadRequest() {
        ResponseEntity<String> response = testRestTemplate.getForEntity(URL + "?tax-type=net&amount=100&rate=15", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
        assertThat(response.getBody()).isEqualTo("Tax rate 15 percent is not applicable for Austria.");
    }
}