package com.karl.dvdlibrary.dao;

import com.karl.dvdlibrary.dto.DVD;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
/**
 *
 * @author karl
 */

// File specfifc implementation of the Data Access Object.
public class DVDLibraryDaoFileImpl implements DVDLibraryDao{
    
    private final String DATA_FILE;
    private static final String DELIMITER = ",";
    // For our collection we will use a HashMap. It will map a unique identifier
    // to a DVD object. We use HasMap becuase the lookup time is O(1), this is
    // signifcanlty better than an Array where the lookup time is O(n).
    private Map<String, DVD> dvdsMap = new HashMap();
    
    public DVDLibraryDaoFileImpl() {
        DATA_FILE = "library.txt";
    }
    
    public DVDLibraryDaoFileImpl(String libraryTextFile) {
        DATA_FILE = libraryTextFile;
    }
    
    // Implement and Override all the methods from the interface.
    @Override
    public DVD addDvd(String idNumber, DVD dvd) throws DVDLibraryPersistenceException {
        loadLibrary();
        DVD previousDvd = dvdsMap.put(idNumber, dvd);
        writeLibrary();
        return previousDvd;
    }
    
    @Override
    public DVD removeDvd(String idNumber) throws DVDLibraryPersistenceException {
        loadLibrary();
        DVD removedDvd = dvdsMap.remove(idNumber);
        writeLibrary();
        return removedDvd;
    }

    @Override
    public List<DVD> getAllDvds() throws DVDLibraryPersistenceException {
        loadLibrary();
        return new ArrayList<>(dvdsMap.values());
    }
    
    @Override
    public DVD getDvd(String idNumber) throws DVDLibraryPersistenceException {
        loadLibrary();
        return dvdsMap.get(idNumber);
    }
    
    @Override
    public DVD getDvdByTitle(String title) throws DVDLibraryPersistenceException {
        List<DVD> dvds = getAllDvds();
        for (DVD dvd : dvds) {
            if (dvd.getTitle().equals(title)) {
                return dvd;
            }
        }
        return null;
    }
    
    @Override
    public List<DVD> getAllMpaaDvds(String mpaaRating) 
            throws DVDLibraryPersistenceException {
        List<DVD> dvds = getAllDvds()
                .stream()
                .filter((dvd) -> dvd.getMpaaRating().equals(mpaaRating))
                .collect(Collectors.toList());
        
        return dvds;
    };
    
    // Data Marsahlling & Data Persistence!
    
    // Takes a single string qhich represents a DVD, the format:
    // "<idNumber>,<title>,<releaseDate>,<mpaaRating>" etc.
    // Split string on the delimiter to give us an array of tokens.
    // Each token is a feild.
    private DVD unmarshallDVD(String dvdText) {
        String[] dvdTokens = dvdText.split(DELIMITER);
        
        String idNumber = dvdTokens[0];
        DVD newDvd = new DVD(idNumber);
        
        newDvd.setTitle(dvdTokens[1]);
        LocalDate ld = LocalDate.parse(dvdTokens[2], 
                DateTimeFormatter.ofPattern("ddMMyyyy"));
        newDvd.setReleaseDate(ld);
        newDvd.setMpaaRating(dvdTokens[3]);
        newDvd.setDirectorName(dvdTokens[4]);
        newDvd.setStudio(dvdTokens[5]);
        newDvd.setUserNote(dvdTokens[6]);
        
        return newDvd;
    }
    
    // Opens the DATA_FILE, read from the file and for each line call
    // the unmarshalling method.
    private void loadLibrary() throws DVDLibraryPersistenceException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(DATA_FILE)));
        } catch (FileNotFoundException e) {
            throw new DVDLibraryPersistenceException("Could not load data into memory",
                                            e);
        }
        
        String currentLine;
        DVD currentDVD;
        
        while(scanner.hasNextLine()) {
            // Get the current line in file.
            currentLine = scanner.nextLine();
            // Unmarshall to DVD object.
            currentDVD = unmarshallDVD(currentLine);
            // Save object to collection.
            dvdsMap.put(currentDVD.getIdNumber(), currentDVD);
        }
        scanner.close();
    }
    
    // Marshalling a DVD object to the correct format and return that String.
    private String marshallDVD(DVD dvd) {
        String dvdString = dvd.getIdNumber() + DELIMITER;
        dvdString += dvd.getTitle() + DELIMITER;
        dvdString += dvd.getReleaseDate().format(
                DateTimeFormatter.ofPattern("ddMMyyyy")) + DELIMITER;
        dvdString += dvd.getMpaaRating() + DELIMITER;
        dvdString += dvd.getDirectorName() + DELIMITER;
        dvdString += dvd.getStudio() + DELIMITER;
        dvdString += dvd.getUSerNote();
        return dvdString;
    }
    
    // For every DVD in our collection, call our marsahlling function and 
    // save to out data file.
    private void writeLibrary() throws DVDLibraryPersistenceException {
        PrintWriter out;
        
        // Open file for writing.
        try {
            out = new PrintWriter(new FileWriter(DATA_FILE));
        } catch (IOException e) {
            throw new DVDLibraryPersistenceException("Could not wite data to file", e);
        }
        
        String dvdString;
        List<DVD> dvdList = getAllDvds();
        
        for (DVD dvd : dvdList) {
            // Marshall DVD object to String format.
            dvdString = marshallDVD(dvd);
            // Print to our File.
            out.println(dvdString);
            // Ensure line is not buffered. 
            out.flush();
        }
        
        out.close();
    }
}
