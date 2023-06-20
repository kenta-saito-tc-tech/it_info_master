package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.GameAnswerRecord;
import com.example.It_info_pass_master.Entity.GameQuestionRecord;
import com.example.It_info_pass_master.Entity.GameSelectRecord;
import com.example.It_info_pass_master.Entity.GameTimeRecord;
import com.example.It_info_pass_master.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class GameRestController {
    @Autowired
    private GameService gameService;

    @GetMapping("/game_start")
    public GameQuestionRecord question(@RequestParam(name = "userGameId")int userGameId, @RequestParam(name = "i")int i) {
        var gameQuestion = gameService.gameAgeSelect(userGameId,i);
        return gameQuestion;
    }

    @GetMapping("/game_start/select")
    public List<GameSelectRecord> select(@RequestParam(name = "userGameId")int userGameId, @RequestParam(name = "i")int i) {
        var gameSelect = gameService.gameChoiceSelect(userGameId, i);
        return gameSelect;
    }

    @PostMapping("/game_start/answerInsert")
    public String answerInsert(@RequestBody GameAnswerRecord gameAnswerRecord) {
        gameService.gameAnswerAdd(gameAnswerRecord);
        return "ok";
    }

    @PostMapping("/game_start/timeInsert")
    public String timeInsert(@RequestBody GameTimeRecord gameTimeRecord) {
        var sumTime = gameTimeRecord.minutes() * 60 + gameTimeRecord.seconds();

        var falseSum = gameService.userGameDetial(gameTimeRecord.id());
        var addTime = falseSum.count() * 10; //1ミスで10秒追加
        var resultTime = sumTime + addTime;
        gameService.gameTimeAdd(gameTimeRecord.id(), resultTime);
        return "ok";
    }

}
