package cyhunter.server.controller;

import cyhunter.server.businesslogic.IGameLogic;
import cyhunter.server.businesslogic.GameLogic;
import cyhunter.server.models.LeaderBoardEntry;
import cyhunter.server.models.Objective;
import cyhunter.server.models.UpdateUserScoreRequest;
import cyhunter.server.models.UpdateUserScoreResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping(path="/game")
@Api(value="scores", description="Operations pertaining to Game play")
public class GameController {

    private IGameLogic scoreLogic;

    public GameController(){
        this.scoreLogic = new GameLogic();
    }

    public GameController(IGameLogic sl){
        this.scoreLogic = sl;
    }

    /***
     * Gets the top 10 Scores for the week, if the requesting User is not in the top 10 their weekly Score is also
     * returned.
     * @return A Set of LeaderBoardEntries
     */
    @GetMapping(path="/weeklyleaders", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Leader Board")
    })
    public Set<LeaderBoardEntry> getWeeklyLeaderBoard(){
        return this.scoreLogic.getWeeklyLeaderBoard();
    }

    /***
     * Gets the top 10 Scores for the day, if the requesting User is not in the top 10 their daily Score is also
     * returned.
     * @return A Set of LeaderBoardEntries
     */
    @GetMapping(path="/dailyleaders", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved Leader Board")
    })
    public Set<LeaderBoardEntry> getDailyLeaderBoard() {
        return this.scoreLogic.getDailyLeaderBoard();
    }

    /***
     * Gets the requesting User's daily Score
     * @param uId The Id of the User to get the daily Score for
     * @return An int
     */
    @GetMapping(path="/dailyuserscore/{uId}", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code=200, message="Succesfully retrieved score"),
            @ApiResponse(code=404, message = "Could not find User with that Id")
    })
    public int getDailyUserScore(@PathVariable int uId){

        try {
            int result = this.scoreLogic.getDailyUserScore(uId);
            return result;
        } catch (IllegalArgumentException iEx){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that Id could be found");
        }
    }

    /***
     * Gets the requesting User's weekly Score
     * @param uId The Id of the User requesting the weekly score
     * @return An int
     */
    @GetMapping(path="/weeklyuserscore/{uId}", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code=200, message="Succesfully retrieved score"),
            @ApiResponse(code=404, message = "Could not find User with that Id")
    })
    public int getWeeklyUserScore(@PathVariable int uId){
        return this.scoreLogic.getWeeklyUserScore(uId);
    }

    /***
     * Updates the requesting User's Score by completing an objective.
     * @param uus A JSON object { userId: int, locationId: int }
     * @return A JSON serialized object containing the result of the operation, the points earned, the User's daily
     * and weekly Scores and a response message.
     */
    @PutMapping(path="/updateuserscore", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code=200, message="Succesfully retrieved score"),
            @ApiResponse(code=404, message = "Could not find User with that Id")
    })
    public UpdateUserScoreResult updateUserScore(@RequestBody UpdateUserScoreRequest uus){
        return this.scoreLogic.updateUserScore(uus.getUserId(), uus.getLocationId());
    }

    /***
     * Gets the current Game's objectives along with their current point value
     * @return A Set of Objectives
     */
    @GetMapping(path="/objectives", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code=200, message="Succesfully retrieved objectives")
    })
    public Set<Objective> getGameObjectives(){
        return this.scoreLogic.getGameObjectives();
    }
}
