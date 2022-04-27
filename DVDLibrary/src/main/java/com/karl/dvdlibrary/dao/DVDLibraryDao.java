/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.dvdlibrary.dao;

import com.karl.dvdlibrary.dto.DVD;

import java.util.List;

/**
 *
 * @author karl
 */
public interface DVDLibraryDao {
    
    /** 
     * Adds the given DVD to the library and links it to the given idNumber 
     * If there already is a DVD associated with that idNumber it will return
     * that DVD, otherwise it will return null.
     *
     * @param idNumber, a unique identifier for each DVD.
     * @param dvd, the DVD to be added
     * @return the DVD object previously associated to that idNumber it it
     * exits, otherwise null.
     * @throws DVDLibraryPersistenceException
     */
    DVD addDvd(String idNumber, DVD dvd) throws DVDLibraryPersistenceException;
        
    /** 
     * Adds the given DVD to the library and links it to the given idNumber 
     * If there already is a DVD associated with that idNumber it will return
     * that DVD, otherwise it will return null.
     *
     * @param idNumber, idNumber of DVD to be removed.
     * @return the DVD object removed or null if it does not exist.
     * @throws DVDLibraryPersistenceException
     */
    DVD removeDvd(String idNumber) throws DVDLibraryPersistenceException;
    
    /** 
     * Returns a list of all DVDs in the library.
     *
     * @return List of all DVDs.
     * @throws DVDLibraryPersistenceException
     */
    List<DVD> getAllDvds() throws DVDLibraryPersistenceException;
    
    /** 
     * Returns the DVD associated with the given idNumber.
     *
     * @param idNumber, idNumber of DVD to be fetched.
     * @return the DVD object if it exists, null if not.
     * @throws DVDLibraryPersistenceException
     */
    DVD getDvd(String idNumber) throws DVDLibraryPersistenceException;
    
    /** 
     * Returns the first DVD in the collection with a title that matches the 
     * given String.
     *
     * @param title, the title of the DVD to be searched for.
     * @return the DVD object if it exists, if there are more than one DVD with
     * the same title it will return the first one. If no DVDs have that title 
     * it will return null.
     * @throws DVDLibraryPersistenceException
     */
    DVD getDvdByTitle(String title) throws DVDLibraryPersistenceException;
    
    /** 
     * Returns a list of all DVDs in the library with the given MPAA rating.
     *
     * @param mpaaRating, the MPAA rating to be searched for.
     * @return List of all DVDs.
     * @throws DVDLibraryPersistenceException
     */
    List<DVD> getAllMpaaDvds(String mpaaRating) throws DVDLibraryPersistenceException;
}
