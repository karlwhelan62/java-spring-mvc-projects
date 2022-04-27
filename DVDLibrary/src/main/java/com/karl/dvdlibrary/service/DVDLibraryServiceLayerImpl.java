/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.dvdlibrary.service;
import com.karl.dvdlibrary.dao.DVDLibraryAuditDao;
import com.karl.dvdlibrary.dao.DVDLibraryDao;
import com.karl.dvdlibrary.dto.DVD;
import com.karl.dvdlibrary.dao.DVDLibraryPersistenceException;
import java.util.List;

/**
 *
 * @author karl
 */
public class DVDLibraryServiceLayerImpl implements DVDLibraryServiceLayer {
    
    private DVDLibraryDao dao;
    private DVDLibraryAuditDao auditDao;
    
    public DVDLibraryServiceLayerImpl(
            DVDLibraryDao dao, 
            DVDLibraryAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    @Override
    public void createDvd(DVD dvd) throws 
            DVDDataValidationException,
            DVDLibraryPersistenceException {
        
        validateDvdData(dvd);
        dao.addDvd(dvd.getIdNumber(), dvd);
        auditDao.writeAuditEntry("DVD " + dvd.getIdNumber() + " CREATED");
    }
    
    @Override
    public DVD removeDvd(String idNumber) throws DVDLibraryPersistenceException {
        auditDao.writeAuditEntry("DVD " + idNumber + " REMOVED");
        return dao.removeDvd(idNumber);
    }
    
    @Override
    public List<DVD> getAllDvds() throws DVDLibraryPersistenceException {
        return dao.getAllDvds();
    }
    
    @Override
    public DVD getDvd(String idNumber) throws DVDLibraryPersistenceException {
        return dao.getDvd(idNumber);
    }
    
    @Override
    public DVD getDvdByTitle(String title) throws DVDLibraryPersistenceException {
        return dao.getDvdByTitle(title);
    }
    
    @Override
    public List<DVD> getAllMpaaDvds(String mpaaRating) throws DVDLibraryPersistenceException {
        return dao.getAllMpaaDvds(mpaaRating);
    };
    
    public void validateDvdData(DVD dvd) throws DVDDataValidationException {
        if (dvd.getTitle() == null || 
                dvd.getTitle().trim().length() == 0 || 
                dvd.getReleaseDate() == null || 
                dvd.getReleaseDate().toString().trim() .length() == 0 || 
                dvd.getMpaaRating() == null ||
                dvd.getMpaaRating().trim().length() == 0 ||
                dvd.getDirectorName() == null ||
                dvd.getDirectorName().trim().length() == 0 ||
                dvd.getStudio() == null ||
                dvd.getStudio().trim().length() == 0 ||
                dvd.getUSerNote() == null ||
                dvd.getUSerNote().trim().length() == 0) {
            throw new DVDDataValidationException(
                    "ERROR: All feilds are required");
        }
    }   
}
