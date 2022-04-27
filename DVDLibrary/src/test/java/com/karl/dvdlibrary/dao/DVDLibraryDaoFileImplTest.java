/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.karl.dvdlibrary.dao;

import com.karl.dvdlibrary.dto.DVD;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author karl
 */
public class DVDLibraryDaoFileImplTest {
    
    DVDLibraryDao testDao;
    
    public DVDLibraryDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        String testFile = "testlibrary.txt";
        new FileWriter(testFile);
        testDao = new DVDLibraryDaoFileImpl(testFile);
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddGetDVD() throws Exception{
        
        // ARRANGE
        // Create A DVD
        String idNumber = "0001";
        DVD dvd = new DVD(idNumber);
        dvd.setTitle("The Thing");
        dvd.setReleaseDate(LocalDate.parse("1982-05-25"));
        dvd.setMpaaRating("R");
        dvd.setDirectorName("John Carpenter");
        dvd.setStudio("Universal");
        dvd.setUserNote("Classic Horror");
        
        // ACT
        testDao.addDvd(dvd.getIdNumber(), dvd);
        DVD retrievedDvd = testDao.getDvd(idNumber);
        
        // ASSERT
        assertEquals(dvd.getIdNumber(), retrievedDvd.getIdNumber());
        assertEquals(dvd.getTitle(), retrievedDvd.getTitle());
        assertEquals(dvd.getReleaseDate(), retrievedDvd.getReleaseDate());
        assertEquals(dvd.getMpaaRating(), retrievedDvd.getMpaaRating());
        assertEquals(dvd.getDirectorName(), retrievedDvd.getDirectorName());
        assertEquals(dvd.getStudio(), retrievedDvd.getStudio());
        assertEquals(dvd.getUSerNote(), retrievedDvd.getUSerNote());
    }

    @Test void testAddListDVDs() throws Exception {
        
        // ARRANGE
        // Create 2 DVDs
        String idNumber = "0001";
        DVD dvd1 = new DVD(idNumber);
        dvd1.setTitle("The Thing");
        dvd1.setReleaseDate(LocalDate.parse("1982-05-25"));
        dvd1.setMpaaRating("R");
        dvd1.setDirectorName("John Carpenter");
        dvd1.setStudio("Universal");
        dvd1.setUserNote("Classic Horror");
        
        idNumber = "0002";
        DVD dvd2 = new DVD(idNumber);
        dvd2.setTitle("2001: A Spcae Odyssey");
        dvd2.setReleaseDate(LocalDate.parse("1968-05-25"));
        dvd2.setMpaaRating("12");
        dvd2.setDirectorName("Stanley Kubrick");
        dvd2.setStudio("Universal");
        dvd2.setUserNote("SciFi Masterpeice");
        
        // ACT
        testDao.addDvd(dvd1.getIdNumber(), dvd1);
        testDao.addDvd(dvd2.getIdNumber(), dvd2);
        List<DVD> dvds = testDao.getAllDvds();
        
        // ASSERT
        assertNotNull(dvds);
        assertEquals(dvds.size(), 2);
    }
    
    @Test void testAddGetDVDByTitle() throws Exception {
        
        // ARRANGE
        // Create 2 DVDs
        String idNumber = "0001";
        DVD dvd1 = new DVD(idNumber);
        dvd1.setTitle("The Thing");
        dvd1.setReleaseDate(LocalDate.parse("1982-05-25"));
        dvd1.setMpaaRating("R");
        dvd1.setDirectorName("John Carpenter");
        dvd1.setStudio("Universal");
        dvd1.setUserNote("Classic Horror");
        
        idNumber = "0002";
        DVD dvd2 = new DVD(idNumber);
        dvd2.setTitle("2001: A Space Odyssey");
        dvd2.setReleaseDate(LocalDate.parse("1968-05-25"));
        dvd2.setMpaaRating("12");
        dvd2.setDirectorName("Stanley Kubrick");
        dvd2.setStudio("Universal");
        dvd2.setUserNote("SciFi Masterpeice");
        
        // ACT
        testDao.addDvd(dvd1.getIdNumber(), dvd1);
        testDao.addDvd(dvd2.getIdNumber(), dvd2);
        DVD retrievedDvd = testDao.getDvdByTitle("2001: A Space Odyssey");
        
        // ASSERT
        assertEquals(dvd2.getIdNumber(), retrievedDvd.getIdNumber());
        assertEquals(dvd2.getTitle(), retrievedDvd.getTitle());
        assertEquals(dvd2.getReleaseDate(), retrievedDvd.getReleaseDate());
        assertEquals(dvd2.getMpaaRating(), retrievedDvd.getMpaaRating());
        assertEquals(dvd2.getDirectorName(), retrievedDvd.getDirectorName());
        assertEquals(dvd2.getStudio(), retrievedDvd.getStudio());
        assertEquals(dvd2.getUSerNote(), retrievedDvd.getUSerNote());
    }
  
    @Test void testAddRemoveDVD() throws Exception {
        
        // ARRANGE
        // Create 2 DVDs
        String idNumber = "0001";
        DVD dvd1 = new DVD(idNumber);
        dvd1.setTitle("The Thing");
        dvd1.setReleaseDate(LocalDate.parse("1982-05-25"));
        dvd1.setMpaaRating("R");
        dvd1.setDirectorName("John Carpenter");
        dvd1.setStudio("Universal");
        dvd1.setUserNote("Classic Horror");
        
        idNumber = "0002";
        DVD dvd2 = new DVD(idNumber);
        dvd2.setTitle("2001: A Space Odyssey");
        dvd2.setReleaseDate(LocalDate.parse("1968-05-25"));
        dvd2.setMpaaRating("12");
        dvd2.setDirectorName("Stanley Kubrick");
        dvd2.setStudio("Universal");
        dvd2.setUserNote("SciFi Masterpeice");
        
        // ACT
        testDao.addDvd(dvd1.getIdNumber(), dvd1);
        testDao.addDvd(dvd2.getIdNumber(), dvd2);
        DVD removedDvd = testDao.removeDvd(dvd1.getIdNumber());
        List<DVD> dvds = testDao.getAllDvds();
        
        // ASSERT
        assertEquals(dvd1.getIdNumber(), removedDvd.getIdNumber());
        assertEquals(dvd1.getTitle(), removedDvd.getTitle());
        assertEquals(dvd1.getReleaseDate(), removedDvd.getReleaseDate());
        assertEquals(dvd1.getMpaaRating(), removedDvd.getMpaaRating());
        assertEquals(dvd1.getDirectorName(), removedDvd.getDirectorName());
        assertEquals(dvd1.getStudio(), removedDvd.getStudio());
        assertEquals(dvd1.getUSerNote(), removedDvd.getUSerNote());   
        assertNotNull(dvds);
        assertEquals(dvds.size(), 1);
    }
}
