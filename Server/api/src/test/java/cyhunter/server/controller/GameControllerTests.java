package cyhunter.server.controller;

import cyhunter.server.businesslogic.IGameLogic;
import cyhunter.server.models.LeaderBoardEntry;
import cyhunter.server.models.UpdateUserScoreRequest;
import cyhunter.server.models.UpdateUserScoreResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameControllerTests {

    private GameController controller;

    @Mock
    IGameLogic scoreLogic;

    @Test
    public void getDailyLeaderBoard_NoInput_ReturnsStub() {
        // Arrange
        Set<LeaderBoardEntry> expected = this.getMockedData();
        when(scoreLogic.getDailyLeaderBoard()).thenReturn(expected);

        // Act
        Set<LeaderBoardEntry> result = controller.getDailyLeaderBoard();

        // Assert
        Assert.assertNotNull(result);
        Assert.assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    public void getWeeklyLeaderBoard_NoInput_ReturnsStub(){
        // Arrange
        Set<LeaderBoardEntry> expected = this.getMockedData();
        when(scoreLogic.getWeeklyLeaderBoard()).thenReturn(expected);

        // Act
        Set<LeaderBoardEntry> result = controller.getWeeklyLeaderBoard();

        // Assert
        Assert.assertNotNull(result);
        Assert.assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    public void updateUserScore_UserId2_ResultFalse(){
        // Arrange
        UpdateUserScoreResult expected = new UpdateUserScoreResult(false, "User Already Achieved Objective", 0, 10, 65);
        UpdateUserScoreRequest request = new UpdateUserScoreRequest();
        request.setUserId(2);
        request.setLocationId(1);
        when(this.scoreLogic.updateUserScore(request.getUserId(), request.getLocationId())).thenReturn(expected);

        // Act
        UpdateUserScoreResult result = this.controller.updateUserScore(request);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void updateUserScore_UserId1_ResultTrue(){
        // Arrange
        UpdateUserScoreResult expected = new UpdateUserScoreResult(true, "Score Updated Succesfully", 0, 10, 65);
        UpdateUserScoreRequest request = new UpdateUserScoreRequest();
        request.setUserId(1);
        request.setLocationId(1);
        when(this.scoreLogic.updateUserScore(request.getUserId(), request.getLocationId())).thenReturn(expected);

        // Act
        UpdateUserScoreResult result = this.controller.updateUserScore(request);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    @Before
    public void setupTest(){
        MockitoAnnotations.initMocks(this);
        controller = new GameController(scoreLogic);
    }

    /***
     * A Mock result for testing
     * @return A fixed Set of 10 LeaderBoardEntries
     */
    private Set<LeaderBoardEntry> getMockedData(){
        Set<LeaderBoardEntry> result = new HashSet<>();

        result.add(new LeaderBoardEntry(1, "bobby", 25));
        result.add(new LeaderBoardEntry(2, "jane", 30));
        result.add(new LeaderBoardEntry(3, "john", 35));
        result.add(new LeaderBoardEntry(4, "janet", 40));
        result.add(new LeaderBoardEntry(5, "user2", 45));
        result.add(new LeaderBoardEntry(6, "tom", 50));
        result.add(new LeaderBoardEntry(7, "sarah", 55));
        result.add(new LeaderBoardEntry(8, "jessie", 60));
        result.add(new LeaderBoardEntry(9, "josh", 65));
        result.add(new LeaderBoardEntry(10, "tom2", 70));

        return result;
    }
}
