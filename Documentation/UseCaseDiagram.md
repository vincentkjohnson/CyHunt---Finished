## Use Case Diagram

### Diagram

![Use Case Diagram](./UseCaseDiagram.png)

## Use Case Descriptions

### User Joins the Game

1. The User taps on the Join Game button.
2. The client application calls the REST API.
3. The server checks if the User is already in the Game, if not, the User is added.
4. The server returns the list of objectives for that Game to the client.
5. The Client application stores daily game in SQLite database on device.
6. The client application displays a message indicating the user has been added and the objectives for the game.

### User Views Objectives

1. User taps View Objectives button.
2. Client application calls REST API requesting the objectives for the current game (only 1 per day).
3. The server retrieves the current Game objectives from the database.
4. The server returns results to the client application.
5. The client application displays objectives to the User.

### User Achieves Objective

1. User taps Objective Achieved button.
2. Client application compares current GPS location against stored objectives to determine if the user is at an objective, if not a message is shown indicating such.
3. If user is at an objective, the client application checks if that objective has already been retrieved, if so, a message is shown.
4. If the objective has not yet been achieved, the objective is marked as achieved and the REST API is called to marke the user as having achieved the objective.
5. The server checks that the User has not yet met the objective, if they have, a message is returned to the client which is then shown to the User.
6. If the User has not yet achieved the objective, the objective is marked as completed for the User, their score is updated, and a predefined informative message about the building is retrieved.
7. The points earned for that objective and total user daily and weekly scores are returned to the client application along with the building info message.
8. The client application shows a confirmation to the user along with the points earned, currently daily, currently weekly scores, and the info message about the buildingx`.

### User Checks Daily Scoreboard

1. The User taps on View Daily Scoreboard.
2. The client application calls the REST API.
3. The server gets the top 10 daily scores from the database.
4. THe server checks if the requesting User is in the top 10 list, if not the User and their score are added.
5. The server returns the list of scores to the client application.
6. The client application shows the list of scores to the User, highlighting their score.

### User Checks Weekly Scoreboard

1. The User taps on View Weekly Scoreboard.
2. The client application calls the REST API.
3. The server gets the top 10 weekly scores from the database.
4. THe server checks if the requesting User is in the top 10 list, if not the User and their score are added.
5. The server returns the list of scores to the client application.
6. The client application shows the list of scores to the User, highlighting their score.