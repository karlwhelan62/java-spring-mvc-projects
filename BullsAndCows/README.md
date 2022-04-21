# Design Document: Guess the Number, Bulls & Cows, RESTful Service

# Requirements & Use Cases

This application is a REST server to facilitate playing a number guessing game know as “Bulls and Cows”.  In each game a 4-digit number is created where every number is distinct. For each round the user guesses a number and is told the exact and partial digit matches.

* An exact match occurs when the user guesses the correct digit in the correct position.
* A partial match occurs when the user guesses the correct digit in the wrong position.
* Once every number is an exact match the game ends.

The application has the following requirements.

* Will be a Spring Boot REST application using JDBC template to access a database.
* A Game will have an answer and a status (in progress or finished).
* While a game is in progress the user should not be able to see the answer when fetching a game or games.
* The answer will be 4-digit number with no duplicates.
* Each round will have a guess, the time of the guess, and the result of the guess.
* The result of the guess will be in the format “e:0:p:0” where e is number of exact matches, and p is the number of partial matches.
* The server will have RESTful endpoints, the details of which are in the next section.
* The application will include a service layer that will store and apply the games rules.
* The public DAO interface methods will be tested thoroughly.

# REST Endpoints

The application will have the following RESTful endpoint to which the user will send HTTP requests in order to play the game:
* /api/begin – POST – Starts a game, generates an answer and sets the correct status. Should return a 201 Created message and the created game with the answer hidden.
* /api/guess – POST – Makes a guess by passing the guess and gameId as JSON. The application must calculate the results of the guess and mark the game finished if the guess is correct. Should return the round with the results of the guess included.
* /api/game – GET – Returns a list of all games. In progress games must have their answer hidden.
* /api/game/{gameId} – GET – Returns a specific game based on ID. In progress games must have their answer hidden.
* /api/rounds/{gameId} – GET – Returns a list of rounds for the specific game sorted by time.

# Database Design and ERD

We need to design a database to store our data objects. The design for this database is simple. We only need 2 tables, one for Game and one for Round. Game has a one-to-many relationship with Round, there are no many-to-many relationships.

Below are our entities and fields, in 3NF, with attribute types:
* Game
    * gameId INT (PK) Auto_Increment
    * answer CHAR(4) NOT NULL,
    *inProgress TINYINT NOT NULL (“is true or false so we use tiny int, 1 is true, 0 is false”)
* Round
    * roundId INT (PK) Auto_Increment
    * guess CHAR(4) NOT NULL,
    * time TIMESTAMP NOT NULL,
    * guessResult CHAR(7) NOT NULL
    * gameID INT (FK) NOT NULL

Below is our ERD.

![ERD Diagram](/BullsAndCows/src/resources/1-erd.png)

IMAGE

The project includes a SQL script for the schema and SQL script to add sample data. For our tests we don’t want to run tests against our actual data so will create a test database with the same schema.

# Project structure and packages

For the project we have 5 packages which represent the different components in the MVC model. We don’t need a view package as the user input and output is manged by our RESTful endpoints. The structure of these packages, their naming conventions and their respective classes are listed below

* karl.bullsandcows – Top level package, injects dependencies, in this case dependency injection is managed by Spring Boot using annotations.
    * App.java
    * TestApplciationConfiguration – Simple class with configuration for running tests
* karl.bullsandcows.controller – Controller layer
    * BullsAndCowsController.java – Holds our restful endpoints NOTE: interacts with the service layer, never accesses the DAO directly.
* karl.bullsandcows.service – Service layer
    * BullsAndCowsServiceLayer.java – Holds and implements all the game logic and access the DAO.
* karl.bullsandcows.models – Holds our Data Transfer Objects
    * Game.java
    * Round.java
* karl.bullsandcows.data – Holds our DAO interfaces and implementations.
    * GameDao.java – Interface
    * GameInMemoryDao.java – In memory implementation
    * GameDatabaseDao.java – Database Implementation
    * RoundDao.java – Interface
    * RoundInMemoryDao.java – In memory Implementation
    * RoundDatabaseDao.java – Database Implementation.

We also have 2 test classes in our Test package, one for each DAO database implementation
* karl.bullsandcows.data
    * GameDatabaseDaoTest.java
    * RoundDatabaseDaoTest.java


# Application working with Postman

Bellow we have several screenshots showing the Application working through Postman.

1. The result of Get game. Returns all games. Note it includes the rounds nested in the games and that the answer of InProgress game is hidden. 

![Screenshot](/BullsAndCows/src/resources/2-get-game.png)

2. Get game by id. Here we see the info for game with id 1 was retrieved.

![Screenshot](/BullsAndCows/src/resources/3-get-game-by-id.png)

3. Let’s make a guess with Duplicates. We shouldn’t be able to. And we can’t, we get an unprocessable Entity. 

![Screenshot](/BullsAndCows/src/resources/4-guess-dupes.png)

4. Let’s make a guess. I know the answer is “1234” so I’ll make an incorrect guess first. As you can see the info from the round is returned. We have 3 exact matches and 0 partial matches.

![Screenshot](/BullsAndCows/src/resources/5-guess-not-right.png)

5. Now let’s guess correctly. See we have 4 Exact matches.

![Screenshot](/BullsAndCows/src/resources/6-guess-correct.png)

6. Now let’s Fetch our games again. We see that the answer for game 1 is now revealed, and it is no longer in progress, because we guessed correctly, and the game is over.

![Screenshot](/BullsAndCows/src/resources/7-get-finished.png)

7. Let’s start a new game. See we get a game JSON object as a response and that the answer is hidden.

![Screenshot](/BullsAndCows/src/resources/8-begin.png)

8. Finally let’s get the rounds for a given game id. See below.

![Screenshot](/BullsAndCows/src/resources/9-rounds.png)
