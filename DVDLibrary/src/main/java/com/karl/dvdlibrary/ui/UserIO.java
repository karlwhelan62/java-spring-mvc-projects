/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.dvdlibrary.ui;

/**
 *
 * @author karl
 */
public interface UserIO {
    
    // Function to print information.
    void print(String msg);
    
    // Function to reead an int from input.
    int readInt(String prompt);
    
    // Functin to read an int and check if it is in bounds.
    // Use to to ensure the menu selection is in bounds.
    int readInt(String prompt, int min, int max);
    
    // Prompt the user with a message and read their input.
    String readString(String prompt);

    // Prompt the user to say yes or no
    // Called when editing feilds.
    boolean readBool(String prompt);
}
