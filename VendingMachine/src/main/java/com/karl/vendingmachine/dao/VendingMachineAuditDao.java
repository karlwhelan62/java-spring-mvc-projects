package com.karl.vendingmachine.dao;

/**
 *
 * @author karl
 */
public interface VendingMachineAuditDao {
    
        /** Takes the string to add to the audit file.
        * @param entry, the entry to be written.
        * @throws VendingMachinePersistenceException */
        public void writeAuditEntry(String entry) 
            throws VendingMachinePersistenceException;
}
