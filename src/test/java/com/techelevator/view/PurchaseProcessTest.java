package com.techelevator.view;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PurchaseProcessTest {
    private ByteArrayOutputStream output;
    private ByteArrayInputStream  input;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private Product product;

    private Inventory inv;
    private VendingMachineBalance vendingMachineBalance = new VendingMachineBalance();


    @Before
    public void initialize() {

        // Set system output to new PrintStream to capture output
        System.setOut(new PrintStream(outputStreamCaptor));

        //new vendingMachineBalance to hold balance for purchases
        VendingMachineBalance vendingMachineBalance = new VendingMachineBalance();

        File dataFile = new File("vendingmachine.csv"); // CSV File
        inv = new Inventory(dataFile); // Inventory made with CSV File


        //New constructor of product
        product = new Product("C1", "Test Snack", new BigDecimal("1.00"), "Chip");

    }

    @Test
    public void PurchaseProcess_TEST_finishTransaction(){

        //Set the input as A1 as a value is needed in order to construct purchaseProcess
        input   =   new ByteArrayInputStream("A1\n".getBytes());
        output  =    new ByteArrayOutputStream();

        //set the desired change to test the change function returns the lowest number of coins possible
        vendingMachineBalance.setCurrentBalance(BigDecimal.valueOf(1.10));

        //set new PrintStream to construct purchaseProcess
        PrintStream printStream = new PrintStream(output,true);
        PurchaseProcess purchaseProcess = new PurchaseProcess(input , printStream);

        //call upon the finishTransaction method to return the desired output, being 4 Quarter and 1 Dime as well as emptying the balance
        purchaseProcess.finishTransaction(vendingMachineBalance);
        String expected = "Dispensing Change: 4 Quarter(s) | 1 Dime(s) | 0 Nickel(s) | 0 Penny(s)\r\n";
        String expectedBalance = "Your balance is now: $0.00\r\n";

        //Tests to make sure that the output is exactly correct
        Assert.assertEquals(expected + expectedBalance, outputStreamCaptor.toString());
    }


    @Test
    public void purchaseProcess_TEST_getFedMoney(){

        //sets the desired amount to be fed into the machine to ($)10
        input= new ByteArrayInputStream("10\n".getBytes());

        //Constructs a new instance of purchaseProcess to be able to feedMoney.
        //The System.out is necessary for the constructor but does not affect getFedMoney
        PurchaseProcess purchaseProcess = new PurchaseProcess(input,System.out);
        int money  =   purchaseProcess.getFedMoney();

        //Sets the expected return of getFedMoney to 10 and asserts whether getFedMoney is returning the correct value
        String expected="10";
        String actual = String.valueOf(money);
        Assert.assertEquals(expected,actual);
    }



    @Test
    public void PurchaseProcess_TEST_printMessage_According_To_Product_Type(){
        //Creates a new instance of purchaseProcess in order to test the output text depending on the type of product purchased
        PurchaseProcess purchaseProcess = new PurchaseProcess(System.in, System.out);

        //Prints out the desired text after an item has been purchased, in this case a chip
        purchaseProcess.printMessage(product.getType());
        String expected="Crunch Crunch, Yum!";

        //Compares expected to the printed message being captured by outputStreamCaptor
        Assert.assertEquals(expected,outputStreamCaptor.toString().trim());

    }


    @After
    public void tearDown() {
        // Return system output to System.out
        System.setOut(standardOut);
    }
}

