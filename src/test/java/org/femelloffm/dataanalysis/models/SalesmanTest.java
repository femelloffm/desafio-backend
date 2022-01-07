package org.femelloffm.dataanalysis.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SalesmanTest {
    @Test
    public void shouldSetCpf() {
        assertDoesNotThrow(() -> new Salesman("12345678912", "Jose Carlos", 4000D));
    }

    @Test
    public void shouldNotSetCpfIfLengthSmallerThan11() {
        assertThrows(IllegalArgumentException.class, () -> new Salesman("123456789", "Jose Carlos", 4000D));
    }

    @Test
    public void shouldNotSetCpfIfLengthBiggerThan11() {
        assertThrows(IllegalArgumentException.class, () -> new Salesman("1234567891234567", "Jose Carlos", 4000D));
    }

    @Test
    public void shouldNotSetCpfIfItContainsNonDigit() {
        assertThrows(IllegalArgumentException.class, () -> new Salesman("123456789a2", "Jose Carlos", 4000D));
    }
}