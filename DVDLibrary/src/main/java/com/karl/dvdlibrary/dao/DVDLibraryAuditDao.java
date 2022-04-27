/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.dvdlibrary.dao;

/**
 *
 * @author karl
 */
public interface DVDLibraryAuditDao {
    
    public void writeAuditEntry(String entry) 
            throws DVDLibraryPersistenceException;
}
