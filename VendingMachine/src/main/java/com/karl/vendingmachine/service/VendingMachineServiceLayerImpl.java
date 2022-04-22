package com.karl.vendingmachine.service;

import com.karl.vendingmachine.dao.VendingMachineAuditDao;
import com.karl.vendingmachine.dao.VendingMachineDao;
import com.karl.vendingmachine.dto.Item;
import com.karl.vendingmachine.dao.VendingMachinePersistenceException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author karl
 */
public class VendingMachineServiceLayerImpl implements  VendingMachineServiceLayer {
    
    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    // Set our balance to be BigDecimal, intial value 0.
    private BigDecimal currentBal = new BigDecimal("0.00")
            .setScale(2, RoundingMode.HALF_UP);
    
    public VendingMachineServiceLayerImpl (
            VendingMachineDao dao,
            VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    @Override
    public Item vendItem(String idNumber) throws 
            VendingMachinePersistenceException, 
            VendingMachineInsufficientFundsException,
            VendingMachineNoItemInInventoryException {
        
        
        // Fetch the item to be vended
        Item item = dao.getItem(idNumber);

        // If the item is null or the quanity is 0 thorw no item exception.     
        if(item == null || item.getItemCount() == 0) {
            throw new VendingMachineNoItemInInventoryException(
                "ERROR: This item id number does not exist in the Inventory");
        }
        
        // If balance is less than cost throw insufficient funds exception
        if(currentBal.compareTo(item.getItemCost()) < 0) {
            throw new VendingMachineInsufficientFundsException(
                    "ERROR: Insufficient funds.");
        }
        
        currentBal = currentBal.subtract(item.getItemCost());
        Item vendedItem = dao.vendItem(item.getIdNumber());
        // Record that an item was vended in our audit file.
        auditDao.writeAuditEntry("Item Vended. Id number: " + item.getIdNumber() 
                + " ,Item Name: " + item.getItemName());
        
        return vendedItem;
    }
    
    @ Override
    // Initialise the chnage class, set / calculate the change values, 
    // and return the toString for printing.
    public String setChange() {       
        VendingMachineChange change = new VendingMachineChange(currentBal);
        change.setChange();
        setCurrentBal("0");
        return change.toString();
    }
    
    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        
        // Lambdas & Streams!!
        //
        // We only want to get and display items with itemCount more than 0.
        // We will do it in the service layer as it involves business loigc.
        // We will achieve it using a stream and lambda expression.
        return dao.getAllItems()
                .stream()
                .filter((item) -> item.getItemCount() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public Item getItem(String idNumber) throws VendingMachinePersistenceException {
        return dao.getItem(idNumber);
    }
    
    @Override
    public String getCurrentBal() {
        return currentBal.toString();
    }
    
    @Override
    public void setCurrentBal(String newBal) {
        this.currentBal = new BigDecimal(newBal).setScale(2, RoundingMode.HALF_UP);
    }
    
    @Override
    // Called when you want to add more money
    public void addToCurrentBal(String balToAdd) {
        this.currentBal = currentBal.add(
                new BigDecimal(balToAdd).setScale(2, RoundingMode.HALF_UP));
    }
}
