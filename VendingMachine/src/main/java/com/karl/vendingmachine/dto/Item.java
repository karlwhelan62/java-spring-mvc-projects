package com.karl.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author karl
 */
public class Item {
    
    private String idNumber;
    private String itemName;
    private BigDecimal itemCost;
    private int itemCount;
    
    public Item(String idNumber) {
        this.idNumber = idNumber;
    }
    
    // Getters
    public String getIdNumber() {
        return idNumber;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public BigDecimal getItemCost() {
        return itemCost;
    }
    
    public int getItemCount() {
        return itemCount;
    }
    
    // Setters
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }
    
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    // Generated code allowing us to compare items.
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idNumber);
        hash = 97 * hash + Objects.hashCode(this.itemName);
        hash = 97 * hash + Objects.hashCode(this.itemCost);
        hash = 97 * hash + this.itemCount;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.itemCount != other.itemCount) {
            return false;
        }
        if (!Objects.equals(this.idNumber, other.idNumber)) {
            return false;
        }
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        return Objects.equals(this.itemCost, other.itemCost);
    }
    
    
}
