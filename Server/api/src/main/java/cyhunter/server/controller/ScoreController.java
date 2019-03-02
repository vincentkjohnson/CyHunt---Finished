package cyhunter.server.controller;

import cyhunter.server.businesslogic.IScoreLogic;
import cyhunter.server.businesslogic.ScoreLogic;
import cyhunter.server.models.LeaderBoardEntry;
import cyhunter.server.models.UpdateUserScore;
import cyhunter.server.models.UpdateUserScoreResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path="/score")
public class ScoreController {

    private IScoreLogic scoreLogic;

    public ScoreController(){
        this.scoreLogic = new ScoreLogic();
    }

    public ScoreController(IScoreLogic sl){
        this.scoreLogic = sl;
    }

    @GetMapping(path="/weeklyleaders")
    public Set<LeaderBoardEntry> getWeeklyLeaderBoard(){
        return this.scoreLogic.getWeeklyLeaderBoard();
    }

    @GetMapping(path="/dailyleaders")
    public Set<LeaderBoardEntry> getDailyLeaderBoard() {
        return this.scoreLogic.getDailyLeaderBoard();
    }

    @GetMapping(path="/dailyuser/{uId}")
    public int getDailyUserScore(@PathVariable int uId){
        return this.scoreLogic.getDailyUserScore(uId);
    }

    @GetMapping(path="/weeklyuser/{uId}")
    public int getWeeklyUserScore(@PathVariable int uId){
        return this.scoreLogic.getWeeklyUserScore(uId);
    }

    @PutMapping(path="/updateuser")
    public UpdateUserScoreResult updateUserScore(@RequestBody UpdateUserScore uus){
        return this.scoreLogic.updateUserScore(uus.getUserId(), uus.getLocationId());
    }
}
