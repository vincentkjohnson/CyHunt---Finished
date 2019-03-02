package cyhunter.server.businesslogic;

import cyhunter.server.models.AddUserResult;

public interface IUserLogic {
    AddUserResult addNewUser(String username, String password);
}
