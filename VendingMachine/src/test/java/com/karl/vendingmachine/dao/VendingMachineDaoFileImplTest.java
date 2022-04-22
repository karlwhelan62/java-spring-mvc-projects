package com.karl.vendingmachine.dao;

import java.io.FileWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.karl.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author karl
 */
public class VendingMachineDaoFileImplTest {
    
    VendingMachineDao testDao;
    
    public VendingMachineDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testInventory.txt";
        // Use the FileWriter to quickly blannk the file
        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile);
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetItem() throws Exception {
        // Arrange
        String idNumber = "A1";
        Item createdItem = new Item("A1");
        
        createdItem.setItemName("Can of Coke");
        createdItem.setItemCost(new BigDecimal("1.50"));
        createdItem.setItemCount(5);
        
        // Act
        testDao.addItem(idNumber, createdItem);
        Item retrievedItem = testDao.getItem(idNumber);
        
        // Assert
        assertEquals(createdItem.getIdNumber(), retrievedItem.getIdNumber());
        assertEquals(createdItem.getItemName(), retrievedItem.getItemName());
        assertEquals(createdItem.getItemCost(), retrievedItem.getItemCost());
        assertEquals(createdItem.getItemCount(), retrievedItem.getItemCount());
    }

    @Test
    public void testGetAllItems() throws Exception {
        // Arrange
        String idNumber1 = "A1";
        Item item1 = new Item("A1");
        
        item1.setItemName("Can of Coke");
        item1.setItemCost(new BigDecimal("1.50"));
        item1.setItemCount(5);
        
        
        String idNumber2 = "A2";
        Item item2 = new Item("A2");
        
        item2.setItemName("Bag of Crips");
        item2.setItemCost(new BigDecimal("0.80"));
        item2.setItemCount(10);
        
        // Act
        testDao.addItem(idNumber1, item1);
        testDao.addItem(idNumber2, item2);
        List<Item> allItems = testDao.getAllItems();
        
        
        // Assert
        assertNotNull(allItems, "The list of Items should not be null.");
        assertEquals(2, allItems.size() , "List should have 2 items.");
        assertTrue(testDao.getAllItems().contains(item1));
        assertTrue(testDao.getAllItems().contains(item2));
    }
    
    @Test
    public void testVendItem() throws Exception {
        // Arrange
        String idNumber = "A1";
        Item createdItem = new Item("A1");
        
        createdItem.setItemName("Can of Coke");
        createdItem.setItemCost(new BigDecimal("1.50"));
        createdItem.setItemCount(2);
        
        // Act
        testDao.addItem(idNumber, createdItem);
        int originalCount = createdItem.getItemCount();
        Item retrievedItem = testDao.vendItem(idNumber);
        
        // Assert
        assertEquals(createdItem.getIdNumber(), retrievedItem.getIdNumber());
        assertEquals(createdItem.getItemName(), retrievedItem.getItemName());
        assertEquals(createdItem.getItemCost(), retrievedItem.getItemCost());
        // Check that vend has decremented count.
        assertEquals(originalCount, retrievedItem.getItemCount()+1);
    }
}
