package com.karl.vendingmachine.service;

import com.karl.vendingmachine.dto.Item;
import com.karl.vendingmachine.dao.VendingMachinePersistenceException;
import java.util.List;

/**
 *
 * @author karl
 */
public interface VendingMachineServiceLayer {
    
    /** Vends an item for a user. It must ensure that the item exists, the 
     * quantity of the item is greater than 0 and that the user has sufficient 
     * funds. If all of this is true, vend the item and decrement the quantity.
     *
     * @param idNumber of the item to be vended.
     * @return the Item vended.
     * @throws VendingMachinePersistenceException
     * @throws VendingMachineInsufficientFundsException 
     * @throws VendingMachineNoItemInInventoryException */    
    Item vendItem(String idNumber) throws
            VendingMachinePersistenceException,
            VendingMachineInsufficientFundsException,
            VendingMachineNoItemInInventoryException;
    
    /** Gets and returns the Items in the inventory with a quantity greater 
     * than 0.
     *
     * @return the list of Items.
     * @throws VendingMachinePersistenceException */
    List<Item>  getAllItems() throws VendingMachinePersistenceException;
    
     /** Gets and returns the Item with the given idNumber.
     *
     * @param idNumber, the id number.
     * @return the Item.
     * @throws VendingMachinePersistenceException */
    Item getItem(String idNumber) throws VendingMachinePersistenceException;
    
    /** Gets users change in the form of amounts of different coins / 
     *  denominations. Instantiates the Change class, sets the fields, and
     *  calls to toString method to return the result in a printable format.
     *
     * @return the users change is a String*/
    String setChange();
    
    /** Gets the current balance as a String.
     *
     * @return the current balance as a String*/
    String getCurrentBal();
    
    /** Converts the given string to a BigDecimal object and updates the current
    * balance.
    *
    * @param newBal, the balance to add in a String format.*/
    void setCurrentBal(String newBal);
    
    /** Converts the given string to a BigDecimal object and adds it to the 
    * current balance.
    *
    * @param balToAdd, the balance to add in a String format.*/
    void addToCurrentBal(String balToAdd);
}
