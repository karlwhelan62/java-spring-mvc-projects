/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.dvdlibrary.ui;

import java.util.Scanner;
/**
 *
 * @author karl
 */

// Console specifc implementation of USer IO.
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
    public boolean readBool(String prompt) {
        String stringValue;
        do {
            stringValue = this.readString(prompt);
        } while (!stringValue.equals("Y") && !stringValue.equals("N"));
        if(stringValue.equals("Y")) {
            return true;
        }
        return false;
    }
}
