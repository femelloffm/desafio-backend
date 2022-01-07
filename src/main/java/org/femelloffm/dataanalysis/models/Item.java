package org.femelloffm.dataanalysis.models;

public class Item {
    private Integer id;
    private Integer quantity;
    private Double price;

    public Item(Integer id, Integer quantity, Double price) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be a positive number");
        if (price <= 0) throw new IllegalArgumentException("Price must be a positive number");
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }
}