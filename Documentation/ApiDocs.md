# API Reference

__Base Url__: [http://cyhunt-env.m3djxb9pkp.us-east-2.elasticbeanstalk.com:8080](http://cyhunt-env.m3djxb9pkp.us-east-2.elasticbeanstalk.com:8080), all relative URLs below are relative to this base URL.
__Swagger Url__: [http://cyhunt-env.m3djxb9pkp.us-east-2.elasticbeanstalk.com:8080/swagger-ui.html](http://cyhunt-env.m3djxb9pkp.us-east-2.elasticbeanstalk.com:8080/swagger-ui.html)

## UserController

Used for user centric calls.

Relative URL    | HTTP Method | Message Body                                           | Result
----------------|:-----------:|--------------------------------------------------------|---
/user/adduser   | POST        | { "username": "_username_", "password": "_password_" } | { "success:" _boolean_, "message": "_string_" }
/user/loginuser | GET         | { "username": "_username_", "password": "_password_" } | { "success:" _boolean_, "message": "_string_" }

### UserController Test Results

#### Add User

If username = "bob" Then:

```json
{
    "success": false,
    "message": "User bob already exists."
}
```

Otherwise:

```json
{
    "success": true,
    "message": "User username added succesfully."
}
```

#### Login User

If username = "bob" Then:

```json
{
    "success": false,
    "message": "User bob does not exist."
}
```

Otherwise:

```json
{
    "success": true,
    "message": "User <username> logged in succesfully."
}
```


## GameController

Used for score centric calls.

Relative URL                   | HTTP Method | Message Body                                        | Result
-------------------------------|:-----------:|-----------------------------------------------------|---
/game/dailyleaders             | GET         | N/A                                                 | array of { position: _integer_, username: _username_, score:_score_ }
/game/weeklyleaders            | GET         | N/A                                                 | array of { position: _integer_, username: _username_, score:_score_ }
/game/dailyuserscore/{userId}  | GET         | N/A                                                 | int
/game/weeklyuserscore/{userId} | GET         | N/A                                                 | int
/game/updateuserscore/         | PUT         | `{ "userId": _integer_, "locationId": "_integer_" } | { "result": _boolean_, "message": "_string_", "points-earned": _integer_, "daily-score": _integer_, "weekly-score": _integer_ }
/game/objectives               | GET         | N/A                                                 | array of {"locationId": _int_,"latitude": _double__,"longitude": _double_,"radius": _double_,"name":"_string_","shortName_":"_string_","infoNote":"_string_","currentPoints": _int_}

### ScoreController Test Results

#### Update User

```json
If userId = 2 Then:  
{
    "result": false,
    "message": "User Already Achieved Objective",
    "pointesEarned": 0,
    "dailyScore": 10,
    "weeklyScore": 65
}
```

Otherwise:  

```json
{
    "result": true,
    "message": "Score Updated Succesfully",
    "pointesEarned": 10,
    "dailyScore": 20,
    "weeklyScore": 75
}
```

#### Daily Leaders and Weekly Leaders

Always Returns:

```json
[
    { "position": 1, "username": "bobby", "score": 25 },
    { "position": 2, "username": "jane", "score": 30 },
    { "position": 6, "username": "tom", "score": 50 },
    { "position": 9, "username": "josh", "score": 65 },
    { "position": 3, "username": "john", "score": 35 },
    { "position": 4, "username": "janet", "score": 40 },
    { "position": 7, "username": "sarah", "score": 55 },
    { "position": 8, "username": "jessie", "score": 60 },
    { "position": 5, "username": "user2", "score": 45 },
    { "position": 10, "username": "tom2", "score": 70 }
]
```

#### Objectives
Alwyas returns:

```JSON
[
    {"locationId":10,"latitude":42.02965,"longitude":-93.65095,"radius":5.0,"name":"Armory","shortName":"ARMORY","infoNote":"Built in 1924","currentPoints":35},
    {"locationId":14,"latitude":42.02856,"longitude":-93.64467,"radius":5.0,"name":"Bessey Hall","shortName":"BESSEY","infoNote":"Built in 1967","currentPoints":55},
    {"locationId":1,"latitude":42.03469,"longitude":-93.64558,"radius":5.0,"name":"Administrative Services Building","shortName":"ASB","infoNote":"Built in 1998","currentPoints":10},
    {"locationId":7,"latitude":42.02523,"longitude":-93.64931,"radius":5.0,"name":"Enrollment Services Center","shortName":"ENRL_SC","infoNote":"Built in 1907","currentPoints":30},
    {"locationId":20,"latitude":42.02531,"longitude":-93.64839,"radius":5.0,"name":"Carver Hall","shortName":"CARVER","infoNote":"Built in 1969","currentPoints":85},
    {"locationId":4,"latitude":42.02992,"longitude":-93.64016,"radius":5.0,"name":"Agronomy Greenhouse","shortName":"AGRO+GH","infoNote":"Built in 1985","currentPoints":15},
    {"locationId":16,"latitude":42.02363,"longitude":-93.64155,"radius":5.0,"name":"Birch Residence Hall","shortName":"BIRCH","infoNote":"Built in 1923","currentPoints":65},
    {"locationId":6,"latitude":42.02992,"longitude":-93.64203,"radius":5.0,"name":"Crop Genome Informatics Laboratory","shortName":"CGIL","infoNote":"Built in 1961","currentPoints":25},
    {"locationId":12,"latitude":42.02439,"longitude":-93.64154,"radius":5.0,"name":"Barton Residence Hall","shortName":"BARTON","infoNote":"Built in 1918","currentPoints":45},
    {"locationId":24,"latitude":42.03109,"longitude":-93.65119,"radius":5.0,"name":"Communications Building","shortName":"COM+BDG","infoNote":"Built in 1964","currentPoints":95},
    {"locationId":5,"latitude":42.02838,"longitude":-93.64248,"radius":5.0,"name":"Agronomy Hall","shortName":"AGRON","infoNote":"Built in 1952","currentPoints":20},
    {"locationId":11,"latitude":42.02824,"longitude":-93.64991,"radius":5.0,"name":"Atanasoff Hall","shortName":"ATANSFF","infoNote":"Built in 1969","currentPoints":40},
    {"locationId":13,"latitude":42.02623,"longitude":-93.64859,"radius":5.0,"name":"Beardshear Hall","shortName":"BDSHR","infoNote":"Built in 1906","currentPoints":50},
    {"locationId":17,"latitude":42.02612,"longitude":-93.6515,"radius":5.0,"name":"Black Engineering","shortName":"BLACK","infoNote":"Built in 1985","currentPoints":70},
    {"locationId":25,"latitude":42.02853,"longitude":-93.65104,"radius":5.0,"name":"Coover Hall","shortName":"COOVER","infoNote":"Built in 1950","currentPoints":100},
    {"locationId":15,"latitude":42.02591,"longitude":-93.65296,"radius":5.0,"name":"Beyer Hall","shortName":"BEYER","infoNote":"Built in 1964","currentPoints":60},
    {"locationId":19,"latitude":42.02544,"longitude":-93.64602,"radius":5.0,"name":"Campanile","shortName":"CAMPANI","infoNote":"Built in 1898","currentPoints":80},
    {"locationId":21,"latitude":42.028,"longitude":-93.64565,"radius":5.0,"name":"Catt Hall","shortName":"CATT","infoNote":"Built in 1893","currentPoints":90},
    {"locationId":18,"latitude":42.02236,"longitude":-93.64388,"radius":5.0,"name":"Buchanan Residence Hall","shortName":"BUCHAN","infoNote":"Built in 1964","currentPoints":75}]
```