/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.dvdlibrary.dao;

/**
 *
 * @author karl
 */

// Exception handling.
public class DVDLibraryPersistenceException extends Exception{
    
    public DVDLibraryPersistenceException(String message) {
        super(message);
    }
    
    public DVDLibraryPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }   
}
