package cyhunter.server.controller;

import cyhunter.server.businesslogic.IUserLogic;
import cyhunter.server.models.AddUserResult;
import cyhunter.server.models.LoginUserResult;
import cyhunter.server.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTests {

    private UserController controller;

    @Mock
    IUserLogic userLogic;

    @Before
    public void setupTest(){
        MockitoAnnotations.initMocks(this);
        controller = new UserController(userLogic);
    }

    @Test
    public void addUser_ExistingUser_ResultFalse(){
        // Arrange
        User request = new User();
        request.setUsername("bob");
        request.setPassword("123456");

        AddUserResult expected = new AddUserResult(false, "User " + request.getUsername() + " already exists");
        when(this.userLogic.addNewUser(request.getUsername(), request.getPassword())).thenReturn(expected);

        // Act
        AddUserResult result = this.controller.addUser(request);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void addUser_NewUser_ResultTrue(){
        // Arrange
        User request = new User();
        request.setUsername("tom");
        request.setPassword("123456");

        AddUserResult expected = new AddUserResult(true, "User " + request.getUsername() + " has been added succesfully");
        when(this.userLogic.addNewUser(request.getUsername(), request.getPassword())).thenReturn(expected);

        // Act
        AddUserResult result = this.controller.addUser(request);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void loginUser_NonExistingUser_ResultFalse(){
        // Arrange
        User request = new User();
        request.setUsername("bob");
        request.setPassword("123456");

        LoginUserResult expected = new LoginUserResult(false, String.format("User %s does not exist", request.getUsername()));
        when(this.userLogic.loginUser(request.getUsername(), request.getPassword())).thenReturn(expected);

        // Act
        LoginUserResult result = this.controller.loginUser(request);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void loginUser_ExistingUser_ResultTrue(){
        // Arrange
        User request = new User();
        request.setUsername("tom");
        request.setPassword("123456");

        LoginUserResult expected = new LoginUserResult(true, String.format("User %s has logged in succesfully", request.getUsername()));
        when(this.userLogic.loginUser(request.getUsername(), request.getPassword())).thenReturn(expected);

        // Act
        LoginUserResult result = this.controller.loginUser(request);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }
}