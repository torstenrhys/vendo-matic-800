package com.techelevator.view;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Verifier;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InventoryTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private List<Product> productList = new ArrayList<>();

    @Before
    public void setUp() {
        // Set system output to new PrintStream to capture output
        System.setOut(new PrintStream(outputStreamCaptor));

        productList.add(new Product("A1", "Test Snack", new BigDecimal("1.00"), "Chips"));
    }

    @Test
    public void listProducts_prints_correctly() {
        Inventory testInv = new Inventory(productList);
        String expected = "##  Product Name          Price   Qty.\n" +
                "---------------------------------------\n" +
                "A1  Test Snack            $1.00      5\n" +
                "---------------------------------------\n\n";
        testInv.listProducts();
        Assert.assertEquals(expected, outputStreamCaptor.toString());
    }


    @After
    public void tearDown() {
        // Return system output to System.out
        System.setOut(standardOut);
    }
}
