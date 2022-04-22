package com.karl.vendingmachine.service;

import java.math.BigDecimal;

/**
 *
 * @author karl
 */

// This enum represents the various coins and there values per instructions.
enum Coin {
    
    DOLLAR (new BigDecimal("100")),
    QUARTER (new BigDecimal("25")),
    DIME (new BigDecimal("10")),
    NICKEL (new BigDecimal("5")),
    PENNY (new BigDecimal("1"));
    
    private final BigDecimal coinValue;
    
    Coin(BigDecimal coinValue) {
        this.coinValue = coinValue;
    }
    
    public BigDecimal getCointValue(){
        return this.coinValue;
    };
    
}

// This change class will be instnstiated after an item is vended.
// It's purpose is to get the change in coins for the user.
public class VendingMachineChange {
    
    private BigDecimal balance;
    // A int feild for each denomination.
    private int numDollars;
    private int numQuarters;
    private int numDimes;
    private int numNickles;
    private int numPennies;
    
    // The cointrcutor will set the balance to the chnage in BigDecimal after
    // an item in vended.
    public VendingMachineChange(BigDecimal balance) {
        this.balance = balance;
    }
    
    public void setChange() {
        
        // for each denomination, set the value for that denomination.
        BigDecimal dollarVal = Coin.DOLLAR.getCointValue();
        
        // Muliply by 100 to get the balance in pennies, only do once.
        balance = balance.multiply(dollarVal);
        
        // For each denomination, the number is equal to the int value of
        // the current balance divided by the denominations value.
        numDollars = (balance.divide(dollarVal)).intValue();
        
        // Set the blance to be equal to the remaider.
        balance = balance.remainder(dollarVal);
        
        // Do for each denominator.
        BigDecimal quarterVal = Coin.QUARTER.getCointValue();
        numQuarters = (balance.divide(quarterVal)).intValue();
        balance = balance.remainder(quarterVal);
        
        BigDecimal dimeVal = Coin.DIME.getCointValue();
        numDimes = (balance.divide(dimeVal)).intValue();
        balance = balance.remainder(dimeVal);
        
        BigDecimal nickelVal = Coin.NICKEL.getCointValue();
        numNickles = (balance.divide(nickelVal)).intValue();
        balance = balance.remainder(nickelVal);
        
        // Herer we only have pennies left so just set value oof remainder.
        numPennies = balance.intValue();       
    }
    
    // Return the cnhage result in a printable format.
    public String toString() {
        return "Your Change: \n" 
                + numDollars + " Dollars, " 
                + numQuarters + " Quarters, " 
                + numDimes + " Dimes, " 
                + numNickles + " Nickels, " 
                + numPennies +  " Pennies";
    }
}