package com.karl.dvdlibrary;

import com.karl.dvdlibrary.controller.DVDLibraryController;
import com.karl.dvdlibrary.dao.DVDLibraryAuditDao;
import com.karl.dvdlibrary.dao.DVDLibraryAuditDaoFileImpl;
import com.karl.dvdlibrary.dao.DVDLibraryDao;
import com.karl.dvdlibrary.dao.DVDLibraryDaoFileImpl;
import com.karl.dvdlibrary.service.DVDLibraryServiceLayer;
import com.karl.dvdlibrary.service.DVDLibraryServiceLayerImpl;
import com.karl.dvdlibrary.ui.DVDLibraryView;
import com.karl.dvdlibrary.ui.UserIO;
import com.karl.dvdlibrary.ui.UserIOConsoleImpl;

/**
 *
 * @author karl
 */
public class App {
    
    // This is the entry point to our application
    public static void main(String[] args) {
        
        // Here we do our dependency injection by passing the implementations
        // of User IO and Data Access Object we want to use.
        UserIO io = new UserIOConsoleImpl();
        DVDLibraryView view = new DVDLibraryView(io);
        DVDLibraryDao dao = new DVDLibraryDaoFileImpl();
        DVDLibraryAuditDao auditDao = new DVDLibraryAuditDaoFileImpl();
        DVDLibraryServiceLayer service = new DVDLibraryServiceLayerImpl(
                dao, auditDao);
        DVDLibraryController controller = new DVDLibraryController(
                service, view);
        
        controller.startApp();
    }
}
