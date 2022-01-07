package org.femelloffm.dataanalysis.models;

public class Customer {
    private static final String CNPJ_REGEX = "[0-9]{14}";
    private String cnpj;
    private String name;
    private String businessArea;

    public Customer(String cnpj, String name, String businessArea) {
        if (!cnpj.matches(CNPJ_REGEX)) throw new IllegalArgumentException("Invalid CNPJ format in " + cnpj);
        this.cnpj = cnpj;
        this.name = name;
        this.businessArea = businessArea;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getName() {
        return name;
    }

    public String getBusinessArea() {
        return businessArea;
    }
}