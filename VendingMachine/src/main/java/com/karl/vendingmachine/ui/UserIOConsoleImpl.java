package com.karl.vendingmachine.ui;

import java.util.Scanner;

/**
 *
 * @author karl
 */
// A console implementation of the UserIO interface.
public class UserIOConsoleImpl implements UserIO{
    
    final private Scanner CONSOLE = new Scanner(System.in);
    
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }
    
    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return CONSOLE.nextLine();
    }
    
    @Override
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e){
                this.print("Invalid input. Please try again.");
            }
        }
        return num;
    }
    
    @Override
    public int readInt(String prompt, int min, int max) {
        int result;
        do {
            result = this.readInt(prompt);
        } while (result < min || result > max);
        return result;
    }
    
    @Override
    public double readDouble(String prompt) {
        while(true) {
            try {
                return Double.parseDouble(this.readString(prompt));
            } catch(NumberFormatException e) {
                this.print("Input error. Please try again");
            }
        }
    }
    
    @Override
    public double readDouble(String prompt, double min, double max) {
        double result;
        do {
            result = readDouble(prompt);
        } while(result < min || result > max);
        
        return result;
    }
}
