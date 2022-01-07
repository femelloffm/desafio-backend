package org.femelloffm.dataanalysis.builders;

import org.femelloffm.dataanalysis.models.OutputData;

public class OutputDataBuilder {
    private OutputData outputData;

    public OutputDataBuilder() {
        this.outputData = new OutputData();
    }

    public static OutputDataBuilder builder() {
        return new OutputDataBuilder();
    }

    public OutputDataBuilder withClientAmount(Integer clientAmount) {
        this.outputData.setClientAmount(clientAmount);
        return this;
    }

    public OutputDataBuilder withSalesmanAmount(Integer salesmanAmount) {
        this.outputData.setSalesmanAmount(salesmanAmount);
        return this;
    }

    public OutputDataBuilder withMostExpensiveSale(Integer saleId) {
        this.outputData.setMostExpensiveSaleId(saleId);
        return this;
    }

    public OutputDataBuilder withWorstSalesman(String salesmanName) {
        this.outputData.setWorstSalesman(salesmanName);
        return this;
    }

    public OutputDataBuilder withoutSales() {
        this.outputData.setEmptySales(true);
        return this;
    }

    public OutputData build() {
        return this.outputData;
    }
}
