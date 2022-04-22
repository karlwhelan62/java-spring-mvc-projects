package com.karl.vendingmachine.dao;

import com.karl.vendingmachine.dto.Item;
import java.util.List;

/**
 *
 * @author karl
 */
public interface VendingMachineDao {
    
    /** 
     * A method to add an item to the inventory. Used only for testing. 
     * 
     * @param idNumber, item id.
     * @param item, the item itself.
     * @return Item.
     * @throws VendingMachinePersistenceException*/
    public Item addItem(String idNumber, Item item) 
            throws VendingMachinePersistenceException;
    
    /**
     * A method to vend an item. Updates the item count after vending.
     *
     * @param idNumber, the id of the item to be vended
     * @return The item that was vended.
     * @throws VendingMachinePersistenceException
     */    
    Item vendItem(String idNumber) throws VendingMachinePersistenceException;
    
     /** 
     * Returns a list of all Items in the inventory.
     *
     * @return List of all Items.
     * @throws VendingMachinePersistenceException
     */   
    List<Item> getAllItems() throws VendingMachinePersistenceException;
    
    /** 
     * Returns the Item associated with the given idNumber.
     *
     * @param idNumber, idNumber of DVD to be fetched.
     * @return the DVD object if it exists, null if not.
     * @throws VendingMachinePersistenceException
     */    
    Item getItem(String idNumber) throws VendingMachinePersistenceException;
}
