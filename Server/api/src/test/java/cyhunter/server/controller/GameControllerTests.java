package cyhunter.server.controller;

import cyhunter.server.businesslogic.IGameLogic;
import cyhunter.server.models.LeaderBoardEntry;
import cyhunter.server.models.Objective;
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

    @Test
    public void getObjective_NoInput_ResultSet(){
        // Arrange
        Set<Objective> expected = this.getMockedObjectives();
        when(this.scoreLogic.getGameObjectives()).thenReturn(expected);

        // Act
        Set<Objective> result = this.controller.getGameObjectives();

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

    /***
     * A mock result for testing
     * @return A fixed Set of 20 Objectives
     */
    private Set<Objective> getMockedObjectives(){
        Set<Objective> result = new HashSet<>();

        result.add(new Objective(1, 42.03469, -93.64558, 5.0, "Administrative Services Building", "ASB", "Built in 1998", 10));
        result.add(new Objective(4, 42.02992, -93.64016, 5.0, "Agronomy Greenhouse","AGRO+GH", "Built in 1985", 15));
        result.add(new Objective(5, 42.02838, -93.64248, 5.0, "Agronomy Hall","AGRON", "Built in 1952", 20));
        result.add(new Objective(6, 42.02992, -93.64203, 5.0, "Crop Genome Informatics Laboratory","CGIL", "Built in 1961", 25));
        result.add(new Objective(7, 42.02523, -93.64931, 5.0, "Enrollment Services Center","ENRL_SC", "Built in 1907", 30));
        result.add(new Objective(10, 42.02965, -93.65095, 5.0, "Armory","ARMORY", "Built in 1924", 35));
        result.add(new Objective(11, 42.02824, -93.64991, 5.0, "Atanasoff Hall","ATANSFF", "Built in 1969", 40));
        result.add(new Objective(12, 42.02439, -93.64154, 5.0, "Barton Residence Hall","BARTON", "Built in 1918", 45));
        result.add(new Objective(13, 42.02623, -93.64859, 5.0, "Beardshear Hall","BDSHR", "Built in 1906", 50));
        result.add(new Objective(14, 42.02856, -93.64467, 5.0, "Bessey Hall","BESSEY", "Built in 1967", 55));
        result.add(new Objective(15, 42.02591, -93.65296, 5.0, "Beyer Hall","BEYER", "Built in 1964", 60));
        result.add(new Objective(16, 42.02363, -93.64155, 5.0, "Birch Residence Hall","BIRCH", "Built in 1923", 65));
        result.add(new Objective(17, 42.02612, -93.6515, 5.0, "Black Engineering","BLACK", "Built in 1985", 70));
        result.add(new Objective(18, 42.02236, -93.64388, 5.0, "Buchanan Residence Hall","BUCHAN", "Built in 1964", 75));
        result.add(new Objective(19, 42.02544, -93.64602, 5.0, "Campanile","CAMPANI", "Built in 1898", 80));
        result.add(new Objective(20, 42.02531, -93.64839, 5.0, "Carver Hall","CARVER", "Built in 1969", 85));
        result.add(new Objective(21, 42.028, -93.64565, 5.0, "Catt Hall","CATT", "Built in 1893", 90));
        result.add(new Objective(24, 42.03109, -93.65119, 5.0, "Communications Building","COM+BDG", "Built in 1964", 95));
        result.add(new Objective(25, 42.02853, -93.65104, 5.0, "Coover Hall","COOVER", "Built in 1950", 100));

        return result;
    }
}
