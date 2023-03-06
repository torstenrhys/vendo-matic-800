package com.techelevator.view;
/**
 * Creates a Product object that has the following variables:
 * slotID >>    The product's location in the vending machine
 * name >>      The product's name
 * price >>     How much the product costs
 * type >>      Whether the product is a candy, a chip, a drink, or a gum
 * inStock >>   The product's current quantity within the machine
 * <p>
 * Contains one constructor.
 * <p>
 * Contains two methods (descriptions provided in code):
 * printProductInfo()
 * updateStockInfo()
 */

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final static int STARTING_STOCK = 5; /* Used to set each Product's starting quantity to 5 */

    private String slotID;
    private String name;
    private BigDecimal price;
    private String type;
    private int inStock;

    public Product(String slotID, String name, BigDecimal price, String type) {
        /* Objects.requireNonNull throws a NullPointerException if the Object contains a null value */
        this.slotID = Objects.requireNonNull(slotID, "Slot ID can't be null or empty.");
        this.name = Objects.requireNonNull(name, "Name can't be null or empty.");
        this.price = Objects.requireNonNull(price, "Price can't be null or empty.");
        this.type = Objects.requireNonNull(type, "Type can't be null or empty.");
        this.inStock = STARTING_STOCK;
    }


    // Prints a line of text containing the Product's slotID, name, price, and current stock
    public void printProductInfo() {
        if (inStock >= 1) {
            System.out.printf("%-4s%-22s$%-6.2f%5d\n", slotID, name, price, inStock);
        } else {
            System.out.printf("%-4s%-22s$%-6.2f%s\n", slotID, name, price, "SOLD OUT");
        }
    }

    // Updates stock details when dispensing Product from vending machine
    public void updateStockInfo() {
        if (inStock > 0) {
            inStock--;
        }
    }

    // Getters
    public String getSlotID() {
        return slotID;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public int getStock() {
        return inStock;
    }

    // Setters
    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

}

