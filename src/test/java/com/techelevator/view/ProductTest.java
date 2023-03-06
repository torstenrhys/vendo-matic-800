package com.techelevator.view;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

public class ProductTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private Product product;

    @Before
    public void setUp() {
        // Set system output to new PrintStream to capture output
        System.setOut(new PrintStream(outputStreamCaptor));
        // Construct test product
        product = new Product("A1", "Test Snack", new BigDecimal("1.00"), "Chips");
    }

    @Test
    public void printProductInfo_shows_correct_slot_name_price_and_stock() {
        product.printProductInfo();
        Assert.assertEquals("A1  Test Snack            $1.00      5", outputStreamCaptor.toString().trim());
    }

    @Test
    public void printProductInfo_shows_if_item_is_sold_out() {
        product.setInStock(0);
        product.printProductInfo();
        Assert.assertEquals("A1  Test Snack            $1.00  SOLD OUT", outputStreamCaptor.toString().trim());
    }

    @Test
    public void updateStockInfo_changes_stock_properly() {
        product.updateStockInfo();
        product.updateStockInfo();
        Assert.assertEquals(3, product.getStock());
    }

    @Test
    public void updateStockInfo_does_not_take_stock_below_zero() {
        product.updateStockInfo();
        product.updateStockInfo();
        product.updateStockInfo();
        product.updateStockInfo();
        product.updateStockInfo();
        product.updateStockInfo();
        Assert.assertEquals(0, product.getStock());
    }

    @After
    public void tearDown() {
        // Return system output to System.out
        System.setOut(standardOut);
    }
}
