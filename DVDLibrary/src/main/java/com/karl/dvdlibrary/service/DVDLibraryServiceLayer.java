/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.dvdlibrary.service;

import com.karl.dvdlibrary.dto.DVD;
import com.karl.dvdlibrary.dao.DVDLibraryPersistenceException;
import java.util.List;

/**
 *
 * @author karl
 */
public interface DVDLibraryServiceLayer {
    
    void createDvd(DVD dvd) throws 
            DVDDataValidationException,
            DVDLibraryPersistenceException;
    
    DVD removeDvd(String idNumber) throws DVDLibraryPersistenceException;
    
    List<DVD> getAllDvds() throws DVDLibraryPersistenceException;
    
    DVD getDvd(String idNumber) throws DVDLibraryPersistenceException;
    
    DVD getDvdByTitle(String title) throws DVDLibraryPersistenceException;
    
    List<DVD> getAllMpaaDvds(String mpaaRating) throws DVDLibraryPersistenceException;
}
