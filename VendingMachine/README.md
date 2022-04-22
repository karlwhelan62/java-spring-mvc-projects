# Vending Machine Java Spring Project

This program simulates a vending machine using Java, Spring Dependecny Injection, Data Marshalling and Unmarshalling and the Model-View-Controller design principle.

# Requirements & Use Cases
This program simulates a vending machine. It allows the following use cases for a User:
* Display all items to a user and their respective prices on start-up.
* Allow (and require) a user to enter an amount of money (US Dollars) before they select an item.
* Allow the user to exit the program from the start-up menu.
* If the user selects an item that costs more than the amount the user has put into the machineW it informs the user of insufficient funds and re displays current amount user has put into the machine.
* If the user selects an item and has the required funds the program should display the change returned to the user in the form quarters, nickels and dimes.

It also satisfies the following requirements.
* Vending machine items must be stored in a file. The inventory for this vending machine must be read from this file on start-up and writing to the file after the program exits.
* For each item the program should track the following properties:
    * Item names
    * Item cost
    * Number of items in inventory
* When an item is vended the program should update the inventory level. If the machine runs out of an item, it should no longer be displayed as an option to the user.

The Item data transfer object should have the following fields:
* Unique Id Number (Representing the slot in the vending machine. As with a real-world vending machine, they may be duplicate items in different slots)
* Item name
* Item cost
* Number of items in inventory

# Classes and Interfaces
The Application will have 16 classes and 3 interfaces. The 16 classes are:
* App: This is the entry point to our application. It will handle our dependency injection.
* Item: This is our Data Transfer Object. It will hold all our info for each Item that the vending machine vends.
* UserIOConsoleImpl: Will handle all user input and output prompts. It’s a console implementation of a User Interface. Implements the UserIO interface. 
* VendingMachineView: Handle all the logic of the User Interface.
* VendingMachineController: Handles the orchestration of the application, doesn’t do any work itself but knows when to call other components.
* VendingMachineServiceLayerImpl: An implementation of VendingMachineServiceLayer interface. It handles all the business logic of the application.
* VendingMachineDaoFileImpl: This is an implementation of the VendingMachineDao interface which is specific for marshalling and unmarshalling data from a text file.
* VendingMachineDataPersistenceException: An error class for throwing an error when the program is unable to persist data.
* VendingMachineAuditDaoFileImpl: An implementation of the VendingMachineAuditDao interface. It will have the specific implementation of writing out audit trail to an audit file to keep track of what has been done and when.
* VendingMachineInsufficientFundsException: An error class that is thrown when a user tries to purchase an item they have not entered enough money for.
* VendingMachineNoItemInventoryException: An error that is thrown when a user tries to purchase an item that has zero inventory or doesn’t exist in the inventory.
* VendingMachineChange: A class that takes the amount of change the user is owed in pennies and returns the number of quarters, nickels, dimes etc due back to the user.
* VendingMachineDaoFileImplTest: A test class which runs out units tests for the file implementation of out DAO.
* VendingMachineAuditDaoStub: A stubbed version of the auditDao class which allows simplified testing of the service layer.
* VendingMachineDaoFileImplStub: A stubbed version of the DAO which allows simplified testing of the service layer.
* VendingMachineServiceLayerImplTest: A test class which has the unit tests which test our service layer.
The 3 interfaces are:
* VendingMachineDao: This interface will define all the methods a Data Access Object must implement in order to work with the application. It allows us to implement a different version of a DAO in the future and inject it at the App class level.
* UserIO: Similar to DAO, it defines all the methods that must be implemented. This would allow us to create a different implementation in the future without changing the other components. For example, we could implement a GUI menu Input and Output system.
* VendingMachineServiceLayer: An interface that defines all the methods our service layer implementation must have.

# File format for data marshalling & unmarshalling
The file format for our Data Transfer Object (Item) will use ‘::’ as a delimiter and look as follows
“<idNumber>::<itemName>::<itemCost>::<itemCount>”
The data will be unmarshalled from, and marshalled to a data file called “inventory.txt”

# Project structure and packages
For the project we have 6 packages which represent the different components in the MVC model. We also have 2 test packages. The structure of these packages, their naming conventions and their respective classes are listed below.

Source packages
* com.karl.vendingmachine – Top level package, injects dependencies.
    * App.java
* com.karl.vendingmachine.controller – Controller layer
    * VendingMachineController.java
* com.karl.vendingmachine.service – Service layer
    * VendingMachineServiceLayer.java – Interface
    * VendingMachineServiceLayerImpl.java – Implementation
    * VendingMachineInsufficientFundsException.java – Insufficient funds error handling.
    * VendingMachineNoItemInventoryException.java – No item in inventory error.
    * VendingMachineChange.java – Calculates the change to be given to a user. I’m placing it in the service layer as it involves calculating business logic. 
* com.karl.vendingmachine.dao – Data Access Object layer
    * VendingMachineDao.java – Interface
    * VendingMachineDaoFileImpl.java – Implementation
    * VendingMachineAuditDao.java – Interface
    * VendingMachineAuditDaoFileImpl.java – Implementation
    * VendingMachinePersistenceException.java – Data persistence error handling
* com.karl.vendingmachine.dto – Data Transfer Object
    * Item.java
* com.karl.dvdlibrary.ui – View layer
    * VendingMachineView.java
    * UserIO.java – Interface
    * UserIOConsoleImpl – Implementation

Test packages
* com.karl.vendingmachine.dao
    * VendingMachineDaoFileImplTest.java – Unit tests for DAO.
* com.karl.vendingmachine.service
    * VendingMachineAuditDaoStub – Stub implementation of auditDao
    * VendingMachineDaoStubImpl – Stub implementation of Dao.
    * VendingMachineServcieLayerImplTest – Unit tests for service layer.