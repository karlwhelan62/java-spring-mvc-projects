package com.karl.vendingmachine.controller;

import com.karl.vendingmachine.dao.VendingMachinePersistenceException;
import com.karl.vendingmachine.service.VendingMachineServiceLayer;
import com.karl.vendingmachine.ui.VendingMachineView;
import com.karl.vendingmachine.dto.Item;
import com.karl.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.karl.vendingmachine.service.VendingMachineNoItemInInventoryException;
import java.util.List;

/**
 *
 * @author karl
 */

// This is the Controller layer. It interacts with the View and Data access
// layer and tells them what to do and when.
public class VendingMachineController {

    // Dependency Injection!!
    // We use dao when interacting with the Data access layer.
    // We use view when interacting with the view layer.    
    private VendingMachineView view;
    private VendingMachineServiceLayer service;
    
    // Use enums for mune options.
    private enum MenuOptions {
        ADD_MONEY, VEND_ITEM, EXIT
    }
    
    // Use the constructor to do dependency injection.
    public VendingMachineController (
            VendingMachineServiceLayer service,
            VendingMachineView view) {
        this.service = service;
        this.view = view;
    }
    
    public void startApp() {
        boolean keepGoing = true;
        MenuOptions menuSelection;
        
        try {
            
            boolean hasErrors = false;
            /* We use this do while becuase we want to go back to the menu if
            * There is a insufficient funds exception, so the user can enter 
            * more money. We initialise a boolean called hasErrors. If there is
            * an error we set it to true, this will fire the do branch again. */
            do {
                try {
                    while(keepGoing) {
                    // Display the item list and balance at startup.
                    displayItemList();
                    displayBalance();
                    
                    // Our menu.
                    menuSelection = getMenuSelection();
            
                    switch(menuSelection) {
                        case ADD_MONEY:
                            addMoney();
                            break;
                        case VEND_ITEM:
                            vendItem();
                            break;
                        case EXIT:
                            keepGoing = false;
                            hasErrors = false;
                            break;
                        default:
                            unkownCommand();
                        }
                    }
                    
                // Insufficient funds.    
                } catch(VendingMachineInsufficientFundsException e) {
                    view.displayErrorMessage(e.getMessage());
                    hasErrors = true;
                }
                    
            } while(hasErrors);
            
            exitMesaage();
            
        // Unlike Insuffient funs error, if there is a data persitence error
        // we want to exit the program.
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        } 
    }
    
    private MenuOptions getMenuSelection() {
        return MenuOptions.values()[view.printMenuAndGetSelection()-1];
    }
    
    // Get item list from service layer, and display with view layer.
    private void displayItemList() throws VendingMachinePersistenceException{
        view.displayItemListBanner();
        List<Item> items = service.getAllItems();
        view.displayAllItems(items);        
    }
    
    // Display current balance.
    private void displayBalance(){
        view.displayBalanceBanner();
        String balance = service.getCurrentBal();
        view.displayBalance(balance);        
    }
    
    // Allow user to insert money.
    private void addMoney(){
        view.displayaddMoneyBanner();
        String newBal = view.getMoneyFromUser();
        service.addToCurrentBal(newBal);
        view.displayaddMoneySuccessMessage();
    }
    
    // Vend item.
    private void vendItem() throws 
            VendingMachinePersistenceException,
            VendingMachineInsufficientFundsException {
        view.displayVendItemBanner();
        boolean hasErrors = false;
        // If there is a no item exception we want to give the user another 
        // chance to enter an ID number. We user a hasErros boolean the same 
        // way as in the startApp method above. 
        do {
            String idNumber = view.getItemId();
            
            try {
                Item item = service.vendItem(idNumber);
                view.displayVendItemSuccessMessage(item);
                view.displayChangeBanner();
                String changeString = service.setChange();
                view.displayChangeCoins(changeString);
                hasErrors = false;
            } catch (VendingMachineNoItemInInventoryException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while(hasErrors);
    }
    
    private void exitMesaage() {
        view.displayGoodbyeMessage();
    }
    
    private void unkownCommand() {
        view.displayUnknownCommandMessage();
    }
}
