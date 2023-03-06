package com.techelevator.view;
/**
 * Creates an Inventory object with the following property:
 * item >>     A LinkedHashMap of Strings and Products with each Product's slotID being assigned as its key
 * <p>
 * Contains a default constructor meant only to be used in testing, and an overloaded constructor that is used to
 * populate the vending machine's inventory.
 * <p>
 * Contains one method (description provided in code):
 * listProducts()
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Inventory {
    // LinkedHashMap used in place of HashMap to keep all entries in the order in which they are added to the Map
    private static Map<String, Product> items = new LinkedHashMap<>();

    // This constructor used for unit testing only
    public Inventory(List<Product> productList) {
        for (Product product : productList) {
            items.put(product.getSlotID().toLowerCase(), product);
        }
    }


    // Constructor used for VendingMachineCLI
    public Inventory(File dataFile) {
        try (Scanner dataInput = new Scanner(dataFile)) {
            while (dataInput.hasNextLine()) {
                /*
                Splits each line into an array with the following indexes:
                    0 >> slotID
                    1 >> name
                    2 >> price
                    3 >> type
                */
                String[] itemArray = dataInput.nextLine().split("\\|");
                /* Uses the current array to create a key and new Product */
                items.put(itemArray[0].toLowerCase(), new Product(itemArray[0], itemArray[1],
                        new BigDecimal(Double.valueOf(itemArray[2])), itemArray[3]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found at path: " + dataFile.getAbsolutePath());
        }
    }


    // Runs through each product in the inventory and lists its slotID, name, price, and current stock
    public void listProducts() {
        System.out.printf("%-4s%-22s%-7s%5s\n", "##", "Product Name", "Price", "Qty.");
        System.out.print("---------------------------------------\n");
        for (Map.Entry<String, Product> item : items.entrySet()) {
            if (item.getKey() != null && item.getValue() != null) {
                item.getValue().printProductInfo();
            } else {
                throw new NullPointerException("Null value found in current inventory.");
            }
        }
        System.out.print("---------------------------------------\n\n");
    }

    // Getter
    public Map<String, Product> getProducts() {
        return items;
    }

}

