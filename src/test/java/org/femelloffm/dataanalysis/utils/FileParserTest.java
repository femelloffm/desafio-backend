package org.femelloffm.dataanalysis.utils;

import org.femelloffm.dataanalysis.exceptions.ObjectParsingException;
import org.femelloffm.dataanalysis.models.Customer;
import org.femelloffm.dataanalysis.models.Sale;
import org.femelloffm.dataanalysis.models.Salesman;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileParserTest {
    @Test
    public void shouldParseSalesman() {
        String salesmanData = "001\u00E712345678912\u00E7Jose Carlos\u00E74000";
        Salesman parsedSalesman = FileParser.parseSalesman(salesmanData);

        assertEquals("12345678912", parsedSalesman.getCpf());
        assertEquals("Jose Carlos", parsedSalesman.getName());
        assertEquals(4000.0, parsedSalesman.getSalary());
    }

    @Test
    public void shouldThrowExceptionIfSalesmanParsingFailed() {
        String salesmanData = "001\u00E712345678912\u00E7Jose Carlos\u00E7Salary";
        assertThrows(ObjectParsingException.class, () -> FileParser.parseSalesman(salesmanData));
    }

    @Test
    public void shouldThrowExceptionIfSalesmanFieldsAreEmpty() {
        String salesmanData = "001\u00E712345678912\u00E7\u00E7Salary";
        assertThrows(ObjectParsingException.class, () -> FileParser.parseSalesman(salesmanData));
    }

    @Test
    public void shouldParseCustomer() {
        String customerData = "002\u00E712345678912345\u00E7Katiane Silveira\u00E7Rural";
        Customer parsedCustomer = FileParser.parseCustomer(customerData);

        assertEquals("12345678912345", parsedCustomer.getCnpj());
        assertEquals("Katiane Silveira", parsedCustomer.getName());
        assertEquals("Rural", parsedCustomer.getBusinessArea());
    }

    @Test
    public void shouldThrowExceptionIfCustomerParsingFailed() {
        String customerData = "002\u00E712345678912345\u00E7Katiane Silveira";
        assertThrows(ObjectParsingException.class, () -> FileParser.parseCustomer(customerData));
    }

    @Test
    public void shouldThrowExceptionIfCustomerFieldsAreEmpty() {
        String customerData = "002\u00E7\u00E7Katiane Silveira";
        assertThrows(ObjectParsingException.class, () -> FileParser.parseCustomer(customerData));
    }

    @Test
    public void shouldParseSale() {
        String saleData = "003\u00E708\u00E7[1-34-10,2-33-1.50,3-40-0.10]\u00E7Jose Carlos";
        Sale parsedSale = FileParser.parseSale(saleData);

        assertEquals(8, parsedSale.getId());
        assertEquals("Jose Carlos", parsedSale.getSalesmanName());
        assertEquals(3, parsedSale.getItems().size());
        assertEquals(1, parsedSale.getItems().get(0).getId());
        assertEquals(34, parsedSale.getItems().get(0).getQuantity());
        assertEquals(10.0, parsedSale.getItems().get(0).getPrice());
        assertEquals(2, parsedSale.getItems().get(1).getId());
        assertEquals(33, parsedSale.getItems().get(1).getQuantity());
        assertEquals(1.5, parsedSale.getItems().get(1).getPrice());
        assertEquals(3, parsedSale.getItems().get(2).getId());
        assertEquals(40, parsedSale.getItems().get(2).getQuantity());
        assertEquals(0.1, parsedSale.getItems().get(2).getPrice());
    }

    @Test
    public void shouldThrowExceptionIfSaleParsingFailed() {
        String saleData = "003\u00E7Number\u00E7[1-34-10,2-33-1.50,3-40-0.10]\u00E7Jose Carlos";
        assertThrows(ObjectParsingException.class, () -> FileParser.parseSale(saleData));
    }

    @Test
    public void shouldThrowExceptionIfSaleFieldsAreEmpty() {
        String saleData = "003\u00E708\u00E7[1-34-10,2-33-1.50,3-40-0.10]\u00E7";
        assertThrows(ObjectParsingException.class, () -> FileParser.parseSale(saleData));
    }

    @Test
    public void shouldNotAddItemIfItemParsingFailed() {
        String saleData = "003\u00E708\u00E7[1-34-10,2-33-price,3-40-0.10]\u00E7Jose Carlos";
        Sale parsedSale = FileParser.parseSale(saleData);

        assertEquals(8, parsedSale.getId());
        assertEquals("Jose Carlos", parsedSale.getSalesmanName());
        assertEquals(2, parsedSale.getItems().size());
        assertEquals(1, parsedSale.getItems().get(0).getId());
        assertEquals(34, parsedSale.getItems().get(0).getQuantity());
        assertEquals(10.0, parsedSale.getItems().get(0).getPrice());
        assertEquals(3, parsedSale.getItems().get(1).getId());
        assertEquals(40, parsedSale.getItems().get(1).getQuantity());
        assertEquals(0.1, parsedSale.getItems().get(1).getPrice());
    }
}