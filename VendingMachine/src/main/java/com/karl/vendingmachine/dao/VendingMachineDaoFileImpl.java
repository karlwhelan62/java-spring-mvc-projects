package com.karl.vendingmachine.dao;

import com.karl.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.*;

/**
 *
 * @author karl
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao{
    
    private final String DATA_FILE;
    private final String DELIMITER = "::";
    private Map<String, Item> itemMap = new HashMap();
    
    public VendingMachineDaoFileImpl() {
        DATA_FILE = "inventory.txt";
    }
    
    public VendingMachineDaoFileImpl(String inventoryTextFile) {
        DATA_FILE = inventoryTextFile;
    }
    
    @Override
    public Item addItem(String idNumber, Item item) 
        throws VendingMachinePersistenceException {
        loadInventory();
        Item prevItem = itemMap.put(item.getIdNumber(), item);
        writeInventory();
        return prevItem;
    }
    
    @Override 
    public List<Item> getAllItems() throws VendingMachinePersistenceException{
        loadInventory();
        return new ArrayList<>(itemMap.values());
    }
    
    @Override
    public Item getItem(String idNumber) 
            throws VendingMachinePersistenceException {
        loadInventory();
        return itemMap.get(idNumber);
    }
    
    @Override
    public Item vendItem(String idNumber) 
            throws VendingMachinePersistenceException {
        
        Item item = itemMap.get(idNumber);  
        // Decrement the item count after vending
        item.setItemCount(item.getItemCount() - 1); 
        writeInventory();
        return item;
    }
    
    // Data Marsahlling * Data Persistence
    
    /* Takes a single string which represents an Item in the inventory.
    * The format: <idNumber>::<itemName>::<itemCost>::<itemCount>
    * Split the String on the Deliiter into an array of tokens.
    * Create a new item object and save the tokens as the relevant feilds.
    * Return the object */
    private Item unmashallItem(String itemString) {
        String[] itemTokens = itemString.split(DELIMITER);
        
        String idNumber = itemTokens[0];
        Item newItem = new Item(idNumber);
        
        newItem.setItemName(itemTokens[1]);
        newItem.setItemCost(new BigDecimal(itemTokens[2])
                .setScale(2, RoundingMode.HALF_UP));
        newItem.setItemCount(Integer.parseInt(itemTokens[3]));
        
        return newItem;
    }
    
    // Open the DATA_FILE, read and for each line unmarshall
    private void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(DATA_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "Could not load data into memory", e);
        }
        
        String currentLine;
        Item currentItem;
        
        while(scanner.hasNextLine()) {
            // Get the current line in file.
            currentLine = scanner.nextLine();
            // Unmrshall to Item object.
            currentItem = unmashallItem(currentLine);
            // Save object to collection.
            itemMap.put(currentItem.getIdNumber(), currentItem);
        }
        scanner.close();
    }
    
    // Marshalling an Item object to the correct format and return that String.
    private String marshallItem(Item item) {
        String itemString = item.getIdNumber() + DELIMITER;
        itemString += item.getItemName() + DELIMITER;
        itemString += item.getItemCost().toString() + DELIMITER;
        itemString += item.getItemCount();
        return itemString;
    }
    
    // For every Item in our collection, call our marshalling function and
    // save to out data file.
    private void writeInventory() throws VendingMachinePersistenceException {
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(DATA_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not write data to file" , e);
        }
        
        String itemString;
        List<Item> itemList = getAllItems();
        
        for (Item item : itemList) {
            // Marshall Item object to String format.
            itemString = marshallItem(item);
            // Print to out File.
            out.println(itemString);
            // Ensure line in not buffered.
            out.flush();
        }
        
        out.close();       
    }
}
