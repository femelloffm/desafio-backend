package org.femelloffm.dataanalysis.models;

import java.util.List;

public class Sale {
    private Integer id;
    private String salesmanName;
    private List<Item> items;

    public Sale(Integer id, String salesmanName, List<Item> items) {
        this.id = id;
        this.salesmanName = salesmanName;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public List<Item> getItems() {
        return items;
    }
}