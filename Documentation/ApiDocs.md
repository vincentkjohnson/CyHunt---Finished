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


## ScoreController

Used for score centric calls.

Relative URL               | HTTP Method | Message Body                                       | Result
---------------------------|:-----------:|----------------------------------------------------|---
/score/dailyleaders        | GET         | N/A                                                | array of { position: _integer_, username: _username_, score:_score_ }
/score/weeklyleaders       | GET         | N/A                                                | array of { position: _integer_, username: _username_, score:_score_ }
/score/dailyuser/{userId}  | GET         | N/A                                                | int
/score/weeklyuser/{userId} | GET         | N/A                                                | int
/score/updateuser/         | PUT         | `{ "userId": _integer_, "locationId": "_integer_" } | { "result": _boolean_, "message": "_string_", "points-earned": _integer_, "daily-score": _integer_, "weekly-score": _integer_ }

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
    {
        "position": 1,
        "username": "bobby",
        "score": 25
    },
    {
        "position": 2,
        "username": "jane",
        "score": 30
    },
    {
        "position": 6,
        "username": "tom",
        "score": 50
    },
    {
        "position": 9,
        "username": "josh",
        "score": 65
    },
    {
        "position": 3,
        "username": "john",
        "score": 35
    },
    {
        "position": 4,
        "username": "janet",
        "score": 40
    },
    {
        "position": 7,
        "username": "sarah",
        "score": 55
    },
    {
        "position": 8,
        "username": "jessie",
        "score": 60
    },
    {
        "position": 5,
        "username": "user2",
        "score": 45
    },
    {
        "position": 10,
        "username": "tom2",
        "score": 70
    }
]
```