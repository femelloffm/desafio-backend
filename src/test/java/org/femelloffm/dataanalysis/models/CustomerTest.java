package org.femelloffm.dataanalysis.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerTest {
    @Test
    public void shouldSetCnpj() {
        assertDoesNotThrow(() -> new Customer("12345678912345", "Katiane Silveira", "Rural"));
    }

    @Test
    public void shouldNotSetCnpjIfLengthSmallerThan14() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("123456789", "Katiane Silveira", "Rural"));
    }

    @Test
    public void shouldNotSetCnpjIfLengthBiggerThan14() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("1234567891234567", "Katiane Silveira", "Rural"));
    }

    @Test
    public void shouldNotSetCnpjIfItContainsNonDigit() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("123456789a2345", "Katiane Silveira", "Rural"));
    }
}