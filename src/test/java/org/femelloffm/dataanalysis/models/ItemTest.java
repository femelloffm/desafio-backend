package org.femelloffm.dataanalysis.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemTest {
    @Test
    public void shouldSetQuantity() {
        assertDoesNotThrow(() -> new Item(1, 10, 1.5));
    }

    @Test
    public void shouldNotSetQuantityIfZero() {
        assertThrows(IllegalArgumentException.class, () -> new Item(1, 0, 1.5));
    }

    @Test
    public void shouldNotSetQuantityIfSmallerThanZero() {
        assertThrows(IllegalArgumentException.class, () -> new Item(1, -2, 1.5));
    }

    @Test
    public void shouldSetPrice() {
        assertDoesNotThrow(() -> new Item(1, 2, 2.5));
    }

    @Test
    public void shouldNotSetPriceIfZero() {
        assertThrows(IllegalArgumentException.class, () -> new Item(1, 2, 0D));
    }

    @Test
    public void shouldNotSetPriceIfSmallerThanZero() {
        assertThrows(IllegalArgumentException.class, () -> new Item(1, 2, -1D));
    }
}