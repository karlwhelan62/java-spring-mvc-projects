/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.vendingmachine.service;

import com.karl.vendingmachine.dao.VendingMachineDao;
import com.karl.vendingmachine.dao.VendingMachinePersistenceException;
import com.karl.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author karl
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao{
    
    public Item onlyItem;
    
    public VendingMachineDaoStubImpl() {
        onlyItem = new Item("A1");
        onlyItem.setItemName("Can of Coke");
        onlyItem.setItemCost(new BigDecimal("1.00"));
        onlyItem.setItemCount(1);
    }
    
    public VendingMachineDaoStubImpl(Item item) {
        this.onlyItem = item;
    }
    
    @Override
    public Item addItem(String idNumber, Item item) 
            throws VendingMachinePersistenceException {
        if (idNumber.equals(onlyItem.getIdNumber())) {
            return onlyItem;
        } else {
            return null;
        }
    }
    
    @Override
    public Item getItem(String idNumber) 
            throws VendingMachinePersistenceException {
        if (idNumber.equals(onlyItem.getIdNumber())) {
            return onlyItem;
        } else {
            return null;
        }
    }
        
    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
             List<Item> itemList = new ArrayList<>();
             itemList.add(onlyItem);
             return itemList;
    }
    
    @Override
    public Item vendItem(String idNumber) 
            throws VendingMachinePersistenceException {
        if (idNumber.equals(onlyItem.getIdNumber())) {
            onlyItem.setItemCount(onlyItem.getItemCount()-1);
            return onlyItem;
        } else {
            return null;
        }       
    }
}
