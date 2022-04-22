/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.vendingmachine.service;

/**
 *
 * @author karl
 */
public class VendingMachineNoItemInInventoryException extends Exception {
    
    public VendingMachineNoItemInInventoryException (String message) {
        super(message);
    }
    
    public VendingMachineNoItemInInventoryException (
            String message, Throwable cause) {
        super(message, cause);
    }     
}
