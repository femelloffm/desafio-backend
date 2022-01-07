package org.femelloffm.dataanalysis.builders;

import org.femelloffm.dataanalysis.models.OutputData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OutputDataBuilderTest {
    @Test
    public void shouldBuildOutputDataWithSales() {
        OutputData outputData = OutputDataBuilder.builder()
                .withClientAmount(10)
                .withSalesmanAmount(5)
                .withMostExpensiveSale(14)
                .withWorstSalesman("Jose Carlos").build();

        assertEquals(10, outputData.getClientAmount());
        assertEquals(5, outputData.getSalesmanAmount());
        assertEquals(14, outputData.getMostExpensiveSaleId());
        assertEquals("Jose Carlos", outputData.getWorstSalesman());
    }

    @Test
    public void shouldBuildOutputDataWithNoSales() {
        OutputData outputData = OutputDataBuilder.builder()
                .withClientAmount(10)
                .withSalesmanAmount(5)
                .withoutSales().build();

        assertEquals(10, outputData.getClientAmount());
        assertEquals(5, outputData.getSalesmanAmount());
        assertNull(outputData.getMostExpensiveSaleId());
        assertNull(outputData.getWorstSalesman());
    }
}