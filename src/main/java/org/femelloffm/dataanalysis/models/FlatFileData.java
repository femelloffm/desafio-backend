package org.femelloffm.dataanalysis.models;

import java.util.ArrayList;
import java.util.List;

public class FlatFileData {
    private List<Salesman> salesmen;
    private List<Customer> customers;
    private List<Sale> sales;

    public FlatFileData() {
        this.salesmen = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.sales = new ArrayList<>();
    }

    public List<Salesman> getSalesmen() {
        return salesmen;
    }

    public void addSalesman(Salesman salesman) {
        this.salesmen.add(salesman);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void addSale(Sale sale) {
        this.sales.add(sale);
    }
}