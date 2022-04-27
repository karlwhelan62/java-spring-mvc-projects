/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.dvdlibrary.ui;

import com.karl.dvdlibrary.dto.DVD;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
/**
 *
 * @author karl
 */
public class DVDLibraryView {
    
    private UserIO io;
    
    // Contructor injection for our User IO dependency.
    public DVDLibraryView(UserIO io) {
        this.io = io;
    }
    
    // Output menu options and get the user selection.
    public int printMenuAndGetSelection() {
        
        io.print("Main Menu");
        io.print("1. Add a New DVD");
        io.print("2. Remove a DVD");
        io.print("3. Edit a DVD");
        io.print("4. List All DVDs");
        io.print("5. Get DVD By ID Number");
        io.print("6. Search DVD By Title");
        io.print("7. Search DVDs By MPAA Rating");
        io.print("8. Exit");
            
        return io.readInt("Please select from the above choices", 1, 8);
    }
    
    // Get Info for new DVD and return new DVD object.
    public DVD getNewDvdInfo() {
        
        // Get new DVD info.
        String idNumber = io.readString("Enter a ID Number for the DVD");
        String title = io.readString("Enter the Title of the DVD");
        String releaseDate = io.readString("Enter the Release Date");
        LocalDate ldReleaseDate = LocalDate.parse(releaseDate, 
                DateTimeFormatter.ofPattern("ddMMyyyy"));
        String mpaaRating = io.readString("Enter the MPAA Rating");
        String directorName = io.readString("Enter the Director Name");
        String studio = io.readString("Enter the Name of the Studio");
        String userNote = io.readString("Enter the User Note");
        
        // Create new DVD object.
        DVD newDVD = new DVD(idNumber);
        
        // Use setters to set feilds.
        newDVD.setTitle(title);
        newDVD.setReleaseDate(ldReleaseDate);
        newDVD.setMpaaRating(mpaaRating);
        newDVD.setDirectorName(directorName);
        newDVD.setStudio(studio);
        newDVD.setUserNote(userNote);
        
        // Return.
        return newDVD;
    }
    
    // This edit function will ask the user, one by one, if they wish to edit
    // the various feilds in a DVD object. Update any feilds that they change
    // and return a new DVD object, we will then use this to overwrite the
    // original object.
    public DVD editDvdInfo(DVD dvd) {
        
        // Use getters to get current feilds.
        String idNumber = dvd.getIdNumber();
        String title = dvd.getTitle();
        LocalDate releaseDate = dvd.getReleaseDate();
        String mpaaRating = dvd.getMpaaRating(); 
        String directorName = dvd.getDirectorName();
        String studio = dvd.getStudio();
        String userNote = dvd.getUSerNote();
       
        // Ask user if they want to change feilds one by one.
        // If so get the new value for the feild.
        io.print("Change the DVD title? Current title: " + title);
        boolean choice = io.readBool("Enter Y for yes or N for no");
        if (choice) {
           title = io.readString("Enter new title");
        }
        
        io.print("Change the DVDs release date? Current date: " + releaseDate.toString());
        choice = io.readBool("Enter Y for yes or N for no");
        if (choice) {
           releaseDate = LocalDate.parse(io.readString("Enter new date"), 
                   DateTimeFormatter.ofPattern("ddMMyyyy"));
        }
        
        io.print("Change the DVDs MPAA rating? Current rating: " + mpaaRating);
        choice = io.readBool("Enter Y for yes or N for no");
        if (choice) {
           mpaaRating = io.readString("Enter new rating");
        }
        
        io.print("Change the DVDs director? Current director: " + directorName);
        choice = io.readBool("Enter Y for yes or N for no");
        if (choice) {
           directorName = io.readString("Enter new director");
        }
        
        io.print("Change the DVDs studio? Current studio: " + studio);
        choice = io.readBool("Enter Y for yes or N for no");
        if (choice) {
           studio = io.readString("Enter new studio");
        }
        
        io.print("Change the DVDs userNote? Current note: " + userNote);
        choice = io.readBool("Enter Y for yes or N for no");
        if (choice) {
           userNote = io.readString("Enter new userNote");
        }
       
        // Create new DVD object.
        DVD newDVD = new DVD(idNumber);
        
        // Set feilds of new object.
        newDVD.setTitle(title);
        newDVD.setReleaseDate(releaseDate);
        newDVD.setMpaaRating(mpaaRating);
        newDVD.setDirectorName(directorName);
        newDVD.setStudio(studio);
        newDVD.setUserNote(userNote);
        
        // Return it.
        return newDVD;
    }
    
    // Take a list of DVD objects and, one by one, display most important feilds. 
    public void displayDvdList(List<DVD> dvds) {
        for (DVD dvd : dvds) {
            String dvdString = String.format("ID: %s, Title: %s, " + 
                    "Director: %s, Date: %s, MPAA: %s, ", 
                    dvd.getIdNumber(),
                    dvd.getTitle(),
                    dvd.getDirectorName(),
                    dvd.getReleaseDate(),
                    dvd.getMpaaRating());
            io.print(dvdString);
        }
    }
    
    public String getDvdIdChoice() {
        return io.readString("Please enter ID Number of DVD");
    }
    
    public String getDvdTitleChoice() {
        return io.readString("Please enter the title of the DVD to search for");
    }
    
    public String getDvdRatingChoice() {
        return io.readString("Please enter the MPAA rating to filer for");
    }
    
    // If the record is null,the DVD the user tried to delete did not exist.
    public void displayRemoveDvdResult(DVD dvdRecord) {
        if (dvdRecord != null) {
            io.print("DVD successfully removed.");
        } else {
            io.print("No such DVD in library.");
        }
        io.readString("Please hit enter to continue");
    }
    
    // Displays all the information of the fetched DVD object. 
    // If null, the DVD does not exist in library. 
    public void displayGetDvdResult (DVD dvdRecord) {
        if (dvdRecord != null) {
            io.print("ID Number: " + dvdRecord.getIdNumber());
            io.print("Title: " + dvdRecord.getTitle());
            io.print("Release Date: " + dvdRecord.getReleaseDate());
            io.print("MPAA Rating: " + dvdRecord.getMpaaRating());
            io.print("Director: " + dvdRecord.getDirectorName());
            io.print("Studio: " + dvdRecord.getStudio());
            io.print("User Note: " + dvdRecord.getUSerNote());
        } else {
            io.print("No such DVD in library");
        }
        io.readString("Please hit enter to continue.");
    }
    
    // Display Banners to make it clear what is happening at any time.
    public void displayAddDvdBanner() {
        io.print(" === Add a DVD === ");
    }
    
    public void displayAddSuccessBanner() {
        io.readString("DVD successfully added. Please hit enter to continue");
    }
    
    public void displayListDvdBanner() {
        io.print(" === List All DVDs === ");
    }
    
    public void displayListAllSuccessBanner() {
        io.readString("All DVDs listed. Please hit enter to continue");
    }
    
    public void displayRemoveDvdBanner() {
        io.print(" === Remove a DVD === ");
    }
    
    public void displayRemoveDvdSuccessBanner() {
        io.readString("DVD successfully removed. Please hit enter to continue");
    }
    
    public void displayGetDvdBanner() {
        io.print(" === Display DVD === ");
    }
    
    public void displayGetDvdByTitleBanner() {
        io.print(" === Search By Title === ");
    }
    
    public void displayGetDvdsByMpaaBanner() {
        io.print(" === Search By MPAA Rating === ");
    }
    
    
    public void displayEditDvdBanner() {
        io.print(" === Edit DVD === ");
    }
    
    public void displayEditDvdSuccessBanner() {
        io.readString("DVD successfully edited. Please hit enter to continue.");
    }
    
    public void displayNoDvdToEditBanner() {
        io.readString("No such DVD. Please hit enter to continue");
    }
    
    public void displayUnknownCommand() {
        io.print("Unkown Command");
    }
    
    public void displayExitBanner() {
        io.print("Good Bye!");
    }
    
    public void disaplyErrorMessage(String errorMsg) {
        io.print(" === ERROR === ");
        io.print(errorMsg);
    }
}
