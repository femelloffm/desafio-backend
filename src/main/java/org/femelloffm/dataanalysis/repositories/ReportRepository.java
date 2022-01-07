package org.femelloffm.dataanalysis.repositories;

import org.femelloffm.dataanalysis.exceptions.SalesNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

@Repository
public class ReportRepository {
    private Map<String, Double> salesBySalesman;
    private Map<Integer, Double> salesCostById;
    private int customerCount;
    private int salesmanCount;

    public ReportRepository() {
        this.salesBySalesman = new HashMap<>();
        this.salesCostById = new HashMap<>();
        this.customerCount = 0;
        this.salesmanCount = 0;
    }

    public void addCustomerAmount(int amount) {
        customerCount += amount;
    }

    public void addSalesmanAmount(int amount) {
        salesmanCount += amount;
    }

    public void addSale(Integer saleId, String salesmanName, Double totalCost) {
        salesCostById.put(saleId, salesCostById.getOrDefault(saleId, 0.0) + totalCost);
        salesBySalesman.put(salesmanName, salesBySalesman.getOrDefault(salesmanName, 0.0) + totalCost);
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public int getSalesmanCount() {
        return salesmanCount;
    }

    public Integer findMostExpensiveSale() {
        try {
            return Collections.max(salesCostById.entrySet(), Entry.comparingByValue()).getKey();
        } catch (NoSuchElementException ex) {
            throw new SalesNotFoundException();
        }
    }

    public String findWorstSalesman() {
        try {
            return Collections.min(salesBySalesman.entrySet(), Entry.comparingByValue()).getKey();
        } catch (NoSuchElementException ex) {
            throw new SalesNotFoundException();
        }
    }

    public void clearReport() {
        salesBySalesman.clear();
        salesCostById.clear();
        customerCount = 0;
        salesmanCount = 0;
    }
}
