package com.karl.vendingmachine.service;

import com.karl.vendingmachine.dao.VendingMachineAuditDao;

/**
 *
 * @author karl
 */
public class VendingMachineAuditDaoStub implements VendingMachineAuditDao {
    
    @ Override
    public void writeAuditEntry(String entry) {
        // Do nothin
    }
}
