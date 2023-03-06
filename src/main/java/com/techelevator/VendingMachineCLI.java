package com.techelevator;

/**
 * VendingMachineCLI is the Main class calling main method:
 * Contains three methods (descriptions provided in code):
 * run
 * displayPurchaseMenu
 * main
 *
 */

import com.techelevator.view.*;
import com.techelevator.view.Exceptions.InsufficientFundsException;
import com.techelevator.view.Exceptions.InvalidSlotIdException;
import com.techelevator.view.Exceptions.OutOfStockException;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class VendingMachineCLI {

    /* Main Menu Display options */
    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";

    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};

    //Sub menu display options
    private static final String PURCHASE_MENU_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_SELECT = "Select Product";
    private static final String PURCHASE_MENU_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_FEED_MONEY, PURCHASE_MENU_SELECT, PURCHASE_MENU_FINISH_TRANSACTION};


    private Menu menu;
    private File dataFile = new File("vendingmachine.csv"); // CSV File where product info is stored
    private Inventory inv = new Inventory(dataFile); // Inventory made with CSV File

    //Purchase process Implementation
    private PurchaseProcess purchaseProcess = new PurchaseProcess(System.in, System.out);

    //new vendingMachineBalance to hold balance for purchases
    private VendingMachineBalance vendingMachineBalance = new VendingMachineBalance();


    public VendingMachineCLI(Menu menu) {
        this.menu = menu;
    }

    //Display main menu and call the appropriate method according to user input
    public void run() {
        while (true) {
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

            if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                // display vending machine items
                inv.listProducts();
            } else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
                //Display the submenu
                displayPurchaseMenu();
            } else {
                System.exit(0);
            }
        }
    }


    //Display the purchase menu and return to Main menu after finish transaction
    //Call methods to implement purchase process
    public void displayPurchaseMenu() {

        while (true) {

            System.out.println("\nCurrent money provided: " + vendingMachineBalance.balanceString());

            // Do purchase
            String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

            // User feeding money into vending machine,this can be done repeatedly by user
            // Vending machine balance also needs to be updated accordingly
            if (purchaseChoice.equals(PURCHASE_MENU_FEED_MONEY)) {
                BigDecimal money = new BigDecimal(purchaseProcess.getFedMoney());
                //Updating vending machine balance
                vendingMachineBalance.feedMoney(money);

                //log all transactions to Log.txt,format currency before logging
                NumberFormat nf = NumberFormat.getCurrencyInstance();
                double balance = money.doubleValue();
                String currency = nf.format(balance);
                PurchaseProcess.logTransaction(" FEED MONEY : " + currency + " " + vendingMachineBalance.balanceString());

            } else if (purchaseChoice.equals(PURCHASE_MENU_SELECT)) {

                //display vending machine items
                inv.listProducts();

					/*Implement purchase process
					Allow vendingMachineBalance to select product,dispense product,update stock and balance
					 */
                try {
                    purchaseProcess.purchaseProduct(inv, vendingMachineBalance);
                } catch (InsufficientFundsException e) {
                    // printing the message from InsufficientFundsException object
                    System.out.println("INSUFFICIENT FUNDS : " + e.getMessage());

                } catch (OutOfStockException exc) {
                    System.out.println("ITEM OUT OF STOCK : " + exc.getMessage());
                } catch (InvalidSlotIdException exception) {
                    System.out.println("INVALID SLOT-ID ENTERED : " + exception.getMessage());

                }

            } else if (purchaseChoice.equals(PURCHASE_MENU_FINISH_TRANSACTION)) {
                //Finish transaction
                purchaseProcess.finishTransaction(vendingMachineBalance);
                //Return to Main menu
                return;
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        VendingMachineCLI cli = new VendingMachineCLI(menu);
        cli.run();
    }
}
