package com.karl.dvdlibrary.controller;

import com.karl.dvdlibrary.ui.DVDLibraryView;
import com.karl.dvdlibrary.dto.DVD;
import com.karl.dvdlibrary.dao.DVDLibraryPersistenceException;
import com.karl.dvdlibrary.service.DVDDataValidationException;
import com.karl.dvdlibrary.service.DVDLibraryServiceLayer;

import java.util.List;

/**
 *
 * @author karl
 */

// This is the Controller layer. It interacts with the View and Data access
// layer and tells them what to do and when.
public class DVDLibraryController {
    
    // Dependency Injection!!
    // We use dao when interacting with the Data access layer.
    // We use view when interacting with the view layer.
    private DVDLibraryView view;
    private DVDLibraryServiceLayer service;
    private enum MenuOptions {
        ADD, REMOVE, EDIT, LIST, GET, SEARCH, SEARCH_MPAA, EXIT
    } 
    
    // Constructor Injection, pass insatnce of dependency to constructor.
    public DVDLibraryController(
            DVDLibraryServiceLayer service, 
            DVDLibraryView view) {
        this.service = service;
        this.view = view;
    }
    
    public void startApp() {
        
        boolean keepGoing = true;
        MenuOptions menuSlection;
        try {
            while (keepGoing) {
            
            // Our menu.
            menuSlection = getMenuSelection();
            
            switch(menuSlection) {
                case ADD:
                    addDvd();
                    break;
                case REMOVE:
                    removeDvd();
                    break;
                case EDIT:
                    editDvd();
                    break;
                case LIST:
                    listAllDvds();
                    break;
                case GET:
                    getDvd();
                    break;
                case SEARCH:
                    getDvdByTitle();
                    break;
                case SEARCH_MPAA:
                    listAllMpaaDvds();
                    break;    
                case EXIT:
                    keepGoing = false;
                    break;
                default:
                    unkownCommand();
            }
        }
        exitMessage();
    
        // Error handling.    
        } catch (DVDLibraryPersistenceException e) {
            view.disaplyErrorMessage(e.getMessage());
        }
    }
    
    private MenuOptions getMenuSelection() {
        return MenuOptions.values()[view.printMenuAndGetSelection() - 1];
    }
    
    // Get info for new DVD from user, create object and save to DAO.
    private void addDvd() throws DVDLibraryPersistenceException {
        view.displayAddDvdBanner();
        boolean hasErrors = false;
        do {
            DVD newDvd = view.getNewDvdInfo();
            try {
                service.createDvd(newDvd);
                view.displayAddSuccessBanner();
                hasErrors = false;
            } catch (DVDDataValidationException e) {
                hasErrors = true;
                view.disaplyErrorMessage(e.getMessage());
            }
            
        } while (hasErrors);
    }
    
    // Get list of all DVDs in DAO and display to user.
    private void listAllDvds() throws DVDLibraryPersistenceException {
        view.displayListDvdBanner();
        List<DVD> dvds = service.getAllDvds();
        view.displayDvdList(dvds);
        view.displayListAllSuccessBanner();
    }
    
    // Search DAO for DVD and, if it exists, remove it.
    private void removeDvd() throws DVDLibraryPersistenceException {
        view.displayRemoveDvdBanner();
        String idNumber = view.getDvdIdChoice();
        DVD dvdRecord = service.removeDvd(idNumber);
        view.displayRemoveDvdResult(dvdRecord);
    }
    
    // Get a DVD by idNumber and dispaly to user.
    private void getDvd() throws DVDLibraryPersistenceException {
        view.displayGetDvdBanner();
        String idNumber = view.getDvdIdChoice();
        DVD dvdRecord = service.getDvd(idNumber);
        view.displayGetDvdResult(dvdRecord);
    }
    
    // Get a DVD by title and display to user.
    private void getDvdByTitle() throws DVDLibraryPersistenceException {
        view.displayGetDvdByTitleBanner();
        String title = view.getDvdTitleChoice();
        DVD dvdRecord = service.getDvdByTitle(title);
        view.displayGetDvdResult(dvdRecord);
    }
    
    // Get DVD by idNumber and, if exists, allow a user to edit feids
    // one by one. Save edited feilds to new object and add to DAO.
    private void editDvd() throws DVDLibraryPersistenceException {
        view.displayEditDvdBanner();
        String idNumber = view.getDvdIdChoice();
        DVD dvdRecord = service.getDvd(idNumber);
        if (dvdRecord != null) {
            boolean hasErrors = false;
            do {
                DVD editedDvd = view.editDvdInfo(dvdRecord);
                try {
                    service.createDvd(editedDvd);
                    view.displayEditDvdSuccessBanner();
                    hasErrors = false;
                } catch (DVDDataValidationException e) {
                    view.disaplyErrorMessage(e.getMessage());
                }
            } while(hasErrors);
        } else {
            view.displayNoDvdToEditBanner();
        }        
    }
    
    // Get list of all DVDs in DAO and display to user.
    private void listAllMpaaDvds() throws DVDLibraryPersistenceException {
        view.displayGetDvdsByMpaaBanner();
        String mpaaRating = view.getDvdRatingChoice();
        List<DVD> dvds = service.getAllMpaaDvds(mpaaRating);
        view.displayDvdList(dvds);
        view.displayListAllSuccessBanner();
    }
    
    private void exitMessage() {
        view.displayExitBanner();
    }
    
    private void unkownCommand() {
        view.displayUnknownCommand();
    }
}
