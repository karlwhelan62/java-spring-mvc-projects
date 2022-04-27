# Design Document: DVD Library
This document outlines the design process for this application.

# Requirements & Use Cases
This program will store information about a DVD collection. 

It must allow the following use cases for a User:
* Add a DVD to the collection.
* Remove a DVD from the collection.
* Edit the information for an existing DVD in the collection.
* To list all DVDs in the collection.
* Display information for a specific DVD.
* To search for a DVD by Title.

It also has the following requirements.
* Load the DVD library from a file.
* Save the DVD library back to the file when the program completes.
* Allow the user to add, edit or delete many DVDs in one session.

The DVD data transfer object should have the following fields:
* Title
* Release Date
* MPAA rating
* Directors Name
* Studio
* User rating or note

# Classes and Interfaces

The Application will have 7 classes and 2 interfaces. The 7 classes will be:
* App: This is the entry point to our application. It will handle our dependency injection.
* DVD: This is our Data Transfer Object. It will hold all our info for a DVD.
* UserIOConsoleImpl: Will handle all user input and output prompts. It’s a console implementation of a User Interface. Implements the UserIO interface. 
* DVDLibraryView: Handle all the logic of the User Interface.
* DVDLibraryController: Handles the orchestration of the application, doesn’t do any work itself but knows when to call other components.
* DVDLibraryDaoFileImpl: This is an implementation of the DVDLibaryDao interface which is specific for marshalling and unmarshalling data from a text file.
* DVDLibraryDaoException: An error class.

The 2 interfaces will be:
* DVDLibraryDao: This interface will define all the methods a Data Access Object must implement in order to work with the application. It allows us to implement a different version of a DAO in the future and inject it at the App class level.
* UserIO: Similar to DAO, it defines all the methods that must be implemented. This would allow us to create a different implementation in the future without changing the other components. For example, we could implement a GUI menu Input and Output system.

# Interface, inheritance and composition relationships
The DVDLibarayDaoFileImpl class implements the DVDLibrayDao interface, The UserIOConsoleImpl class implements the UserIO interface. For composition the Controller component will have View and IO members. The View component will also have an IO member. An example of inheritance is that the DVDLibraryDaoException class will extend Exception.

# Properties and Methods
A list of properties and methods each class will need, I have omitted the App and Exception class as they only need a main methods and method to throw exceptions respectively:

* DVD:
    * **Properties**
    * idNumber – String. I’ve added this field to be a unique identifier. The title field is not suitable as an identifier as some movies share the same name, and you could more than one copies of a DVD in a collection.
    * title – String.
    * releaseDate – String. We could potentially use a date object for this field but as the requirements are not to list DVDs by date in order, it’s easier to just use a String. Agile methodology states always produce the simplest thing that will work.
    * mpaaRating – String.
    * directorsName – String.
    * studio – String.
    * userNote – String.
    * **Methods**
    * Constructor that sets idNumber.
    * Getters and setters for each field.
* DVDLibraryDaoFileImpl:
    * **Properties**
    * A map from id to DVD.
    * A constant for text file name.
    * A constant for delimiter.
    * **Methods**
    * A method for each user story.
    * Methods for marshalling and unmarshalling data from the text file.
* UserIOConsoleImpl:
    * **Properties**
    * A Scanner for reading input from the console.
    * **Methods**
    * A method for printing messages to the console.
    * A method for reading strings from user input.
    * Methods for reading numbers of different types, and ensuring they are in bounds, we use this for menu inputs.
    * A method for allowing a user to select yes or no, used when editing DVDs.
* DVDLibraryView:
    * **Methods**
    * Method to display the user menu.
    * Method to get info for a new DVD.
    * Method to get info for editing an existing DVD.
    * Method to display the result of removing a DVD.
    * Methods to get idNumbers and names for Searching or Removing DVDs.
    * Method for display all DVDs in the collection.
    * Method for display one DVD in the collection.
    * Methods for displaying error and exit messages.
    * Methods for display Banners.
* DVDLibraryController:
    * **Methods**
    * A startAapp method that starts the app, decides when the app exits and decides what methods to call and when.
    * A method for each user story/use case.

# File format for data marshalling & unmarshalling
The file format for our Data Transfer Object (DVD) will use ‘,’ as a delimiter and look as follows

“<idNumber>,<title>,<releaseDate>,<mpaaRating>,<directorsName>, <studio>,<userNote>”

The data will be unmarshalled from, and marshalled to a data file called “library.txt”

# Project structure and packages

For the project we have 5 packages which represent the different components in the MVC model. The structure of these packages, their naming conventions and their respective classes are listed below.

* com.karl.dvdlibrary – Top level package, injects dependencies.
    * App.java
* com.karl.dvdlibrarly.controller – Controller layer
    * DVDLibraryController.java
* com.karl.dvdlobrary.dao – Data Access Object layer
    * DVDLibraryDao.java – Interface
    * DVDLibraryDaoFileImpl.java – Implementation
    * DVDLibraryDaoException.java – Error handling
* com.karl.dvdlibrary.dto – Data Transfer Object
    * DVD.java
* com.karl.dvdlibrary.ui – View layer
    * DVDLibraryView.java
    * UserIO.java – Interface
    * UserIOConsoleImpl – Implementation
