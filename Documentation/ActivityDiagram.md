# Activity Diagrams

## Activity 1: User Joins Game

### Diagram

![Use Case Diagram](./UserJoinsGame.png)

### Description

The user first hits the "Join Game" button in the application.  When the application sees the event raised, it makes a call to the RESTful API to add the user to the game.  The server will then determine if the user has been added to the game already, and if not, add the user.  Regardless of if the user has been previously added, the server then pulls together the current Objectives for the User and passes them to the application.  The application then displays the objectives to the user.

## Activity 2: User Reaches Objective

### Diagram

### Description

## Activity 3: User Views Daily Scoreboard

### Diagram

### Description