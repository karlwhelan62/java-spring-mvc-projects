package com.karl.vendingmachine.service;

import com.karl.vendingmachine.dao.VendingMachinePersistenceException;
import com.karl.vendingmachine.dto.Item;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author karl
 */
public class VendingMachineServiceLayerImplTest {
    
    private VendingMachineServiceLayer service;
    
    public VendingMachineServiceLayerImplTest() {
        
        ApplicationContext appContext 
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        service = appContext.getBean(
                "serviceLayer", VendingMachineServiceLayer.class);
    }

    @Test
    public void vendNonExistantItem() throws Exception {
        try {
            // Id does not exist.
            service.vendItem("A2");
            fail("Expected NoItem Exception was not thrown");
        } catch (VendingMachinePersistenceException |
                VendingMachineInsufficientFundsException e) {
            fail("Incorrect exception was thrown");
        } catch (VendingMachineNoItemInInventoryException e) {
            return;
        }
    }
    
    @Test
    public void vendInsufficientFunds() throws Exception {
        // Balance initalises as 0.
        try {
            service.vendItem("A1");
            fail("Expected NoItem Exception was not thrown");
        } catch (VendingMachinePersistenceException |
                VendingMachineNoItemInInventoryException e) {
            fail("Incorrect exception was thrown");
        } catch (VendingMachineInsufficientFundsException e) {
            return;
        }
    }
    
    @Test
    public void vendSuccesfully() throws Exception {
        try {
            // Set the balance to be sufficient.
            service.setCurrentBal("2");
            // Get the count before vend.
            int originalCount = service.getItem("A1").getItemCount();
            // vend the item.
            Item vendedItem = service.vendItem("A1");
            // Ensure the item count has decremented.
            assertEquals(vendedItem.getItemCount() + 1, originalCount);
        } catch (VendingMachinePersistenceException |
                VendingMachineNoItemInInventoryException |
                VendingMachineInsufficientFundsException e) {
            System.out.println(e.getMessage());
            fail("Item was valid and balance was enough, no exception should have been thrown");
        }
    }
    
    @Test
    public void vendItemNoQuanity() throws Exception {
        try {
            // Set the balance to be sufficient.
            service.setCurrentBal("2");
            // Get the item and set the itemCOunt to 0.
            Item item = service.getItem("A1");
            item.setItemCount(0);
            // Attempt to vend the item.
            service.vendItem("A1");
            fail("Expected NoItem Exception was not thrown");
        } catch (VendingMachinePersistenceException |
                VendingMachineInsufficientFundsException e) {
            fail("Incorrect exception was thrown");
        } catch (VendingMachineNoItemInInventoryException e) {
            return;
        }
    }
}
