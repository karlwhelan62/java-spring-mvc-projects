/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.dvdlibrary.service;

/**
 *
 * @author karl
 */
public class DVDDataValidationException extends Exception {
    
    public DVDDataValidationException (String message) {
        super(message);
    }
    
    public DVDDataValidationException (String message, Throwable cause) {
        super(message, cause);
    }
    
}
