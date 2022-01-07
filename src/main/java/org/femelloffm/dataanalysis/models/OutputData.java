package org.femelloffm.dataanalysis.models;

public class OutputData {
    private static final String FIELD_SEPARATOR = "\u00E7";
    private Integer clientAmount;
    private Integer salesmanAmount;
    private Integer mostExpensiveSaleId;
    private String worstSalesman;
    private boolean emptySales;

    public Integer getClientAmount() {
        return clientAmount;
    }

    public Integer getSalesmanAmount() {
        return salesmanAmount;
    }

    public Integer getMostExpensiveSaleId() {
        return mostExpensiveSaleId;
    }

    public String getWorstSalesman() {
        return worstSalesman;
    }

    public boolean hasEmptySales() {
        return emptySales;
    }

    public void setClientAmount(Integer clientAmount) {
        this.clientAmount = clientAmount;
    }

    public void setSalesmanAmount(Integer salesmanAmount) {
        this.salesmanAmount = salesmanAmount;
    }

    public void setMostExpensiveSaleId(Integer mostExpensiveSaleId) {
        this.mostExpensiveSaleId = mostExpensiveSaleId;
    }

    public void setWorstSalesman(String worstSalesman) {
        this.worstSalesman = worstSalesman;
    }

    public void setEmptySales(boolean emptySales) {
        this.emptySales = emptySales;
    }

    @Override
    public String toString() {
        if (emptySales) return clientAmount + FIELD_SEPARATOR + salesmanAmount + FIELD_SEPARATOR + FIELD_SEPARATOR;
        return clientAmount + FIELD_SEPARATOR + salesmanAmount + FIELD_SEPARATOR + mostExpensiveSaleId
                + FIELD_SEPARATOR + worstSalesman;
    }
}