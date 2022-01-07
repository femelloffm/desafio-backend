package org.femelloffm.dataanalysis.utils;

import org.femelloffm.dataanalysis.exceptions.ObjectParsingException;
import org.femelloffm.dataanalysis.models.Customer;
import org.femelloffm.dataanalysis.models.Item;
import org.femelloffm.dataanalysis.models.Sale;
import org.femelloffm.dataanalysis.models.Salesman;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileParser {
    private static final Logger logger = LoggerFactory.getLogger(FileParser.class);
    private static final String FIELD_SEPARATOR = "\u00E7";
    private static final String ITEM_FIELD_SEPARATOR = "-";

    public static Salesman parseSalesman(String salesmanData) {
        try {
            String[] salesmanFields = salesmanData.split(FIELD_SEPARATOR);
            String cpf = salesmanFields[1];
            String name = salesmanFields[2];
            Double salary = Double.parseDouble(salesmanFields[3]);
            if (cpf.isEmpty() || name.isEmpty()) throw new IllegalArgumentException("Salesman fields cannot be empty");
            return new Salesman(cpf, name, salary);
        } catch (RuntimeException ex) {
            throw new ObjectParsingException(salesmanData, Salesman.class, ex);
        }
    }

    public static Customer parseCustomer(String customerData) {
        try {
            String[] customerFields = customerData.split(FIELD_SEPARATOR);
            String cnpj = customerFields[1];
            String name = customerFields[2];
            String businessArea = customerFields[3];
            if (cnpj.isEmpty() || name.isEmpty() || businessArea.isEmpty())
                throw new IllegalArgumentException("Customer fields cannot be empty");
            return new Customer(cnpj, name, businessArea);
        } catch (RuntimeException ex) {
            throw new ObjectParsingException(customerData, Customer.class, ex);
        }
    }

    public static Sale parseSale(String saleData) {
        try {
            String[] saleFields = saleData.split(FIELD_SEPARATOR);
            Integer saleId = Integer.parseInt(saleFields[1]);
            String seller = saleFields[3];
            List<Item> soldItems = parseItems(saleFields[2]);
            if (seller.isEmpty()) throw new IllegalArgumentException("Sale fields cannot be empty");
            return new Sale(saleId, seller, soldItems);
        } catch (RuntimeException ex) {
            throw new ObjectParsingException(saleData, Sale.class, ex);
        }
    }

    private static List<Item> parseItems(String itemsData) {
        List<Item> itemList = new ArrayList<>();
        String[] items = itemsData.substring(1, itemsData.length() - 1).split(",");
        for (String itemStr : items) {
            try {
                itemList.add(parseItem(itemStr));
            } catch (ObjectParsingException ex) {
                logger.warn(ex.getMessage());
            }
        }
        return itemList;
    }

    private static Item parseItem(String itemData) {
        try {
            String[] itemFields = itemData.split(ITEM_FIELD_SEPARATOR);
            Integer itemId = Integer.parseInt(itemFields[0]);
            Integer quantity = Integer.parseInt(itemFields[1]);
            Double price = Double.parseDouble(itemFields[2]);
            return new Item(itemId, quantity, price);
        } catch (RuntimeException ex) {
            throw new ObjectParsingException(itemData, Item.class, ex);
        }
    }
}