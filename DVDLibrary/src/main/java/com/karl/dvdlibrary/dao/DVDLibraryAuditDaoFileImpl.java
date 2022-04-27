/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.dvdlibrary.dao;

import java.io.*;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author karl
 */
public class DVDLibraryAuditDaoFileImpl implements DVDLibraryAuditDao {
    
    public static final String AUDIT_FILE = "audit.txt";
    
    @Override
    public void writeAuditEntry(String entry)
            throws DVDLibraryPersistenceException {
        
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new DVDLibraryPersistenceException(
                    "ERROR: Could not persist audit normation.", e);
        }
        
        String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyy h:mm:ss"));
        out.println(timestamp + " : " + entry);
        out.flush();
    }
}
