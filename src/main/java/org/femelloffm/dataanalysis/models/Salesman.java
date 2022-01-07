package org.femelloffm.dataanalysis.models;

public class Salesman {
    private static final String CPF_REGEX = "[0-9]{11}";
    private String cpf;
    private String name;
    private Double salary;

    public Salesman(String cpf, String name, Double salary) {
        if (!cpf.matches(CPF_REGEX)) throw new IllegalArgumentException("Invalid CPF format in " + cpf);
        this.cpf = cpf;
        this.name = name;
        this.salary = salary;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public Double getSalary() {
        return salary;
    }
}