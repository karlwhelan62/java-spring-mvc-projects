package com.karl.vendingmachine.ui;

import com.karl.vendingmachine.dto.Item;
import java.util.List;

/**
 *
 * @author karl
 */
public class VendingMachineView {
    
    private UserIO io;
    
    public VendingMachineView(UserIO io) {
        this.io = io;
    }
    
    // Get inputs from users in various scenarios
    public int printMenuAndGetSelection() {
        io.print(" === Main Menu === ");
        io.print("1. Add Money");
        io.print("2. Vend Item");
        io.print("3. Exit");
        
        return io.readInt("Please select from the above choices.", 1, 3);
    }
    
    public String getMoneyFromUser() {       
        Double moneyDouble = io.readDouble(
                "Enter the amount of money you wish to insert", 0, 100);
        String moneyString = Double.toString(moneyDouble);
        return moneyString;
    }
    
    public String getItemId() {        
        return io.readString("Enter the id number of the Item you wish to buy.");
    }
    
    // display 1 item.
    public void displayItem(Item item) {
        io.print("ID: " + item.getIdNumber() + 
                " ,Item: " + item.getItemName() + 
                " ,Cost: $" + item.getItemCost().toString() +
                " ,Quantity Remaining: " + item.getItemCount());
    }
    
    // display all items in the inventory.
    public void displayAllItems(List<Item> items) {
        for (Item item : items) {
            displayItem(item);
        }
    }
    
    // display balance.
    public void displayBalance(String balance) {
        io.print("Balance: " + balance);
    }

    // display chnage in coins.
    public void displayChangeCoins(String changeString) {
        io.print(changeString);
    } 
    
    // display various messages.      
    public void displayaddMoneySuccessMessage() {
        io.print("Successfully added money");
    }

    public void displayVendItemSuccessMessage(Item item) {
        io.print("Successfully vended: " + item.getItemName());
    }    
    
    public void displayGoodbyeMessage() {
        io.print("Good Bye!");
    }
    
    public void displayUnknownCommandMessage() {
        io.print("UNKOWN COMMAND");
    }    
      
    public void displayErrorMessage(String errorMsg) {
        io.print(" === ERROR === ");
        io.print(errorMsg);
    }
    
    // display banners / headings.
    public void displayItemListBanner() {
        io.print(" === All Items === ");
    }
    
    public void displayBalanceBanner() {
        io.print(" === Current Balance === ");
    }
    
    public void displayVendItemBanner() {
        io.print(" === Vend Item === ");
    }
    
    public void displayaddMoneyBanner() {
        io.print(" === Add Money === ");
    }
    
    public void displayChangeBanner() {
        io.print(" === Your Change === ");
    }    
}
