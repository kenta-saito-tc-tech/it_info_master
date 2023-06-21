package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.UserRecord;
import com.example.It_info_pass_master.Service.GameService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {
    @Autowired
    private GameService gameService;

    @Autowired
    private HttpSession session;

    @PostMapping("/game_main")
    public String gameMainPost(@RequestParam(name="age") int id) {
        // UserRecordからuseridを取得している
        var user = (UserRecord)session.getAttribute("user");
        int userid = user.id();
        var list = gameService.userGameAdd(userid, id);
        session.setAttribute("userGameId", list.id());
        return "game_start";
    }

    @GetMapping("/game_finish")
    public String gameFinishGet() {
        return "game_finish";
    }

    @PostMapping("/game_detail")
    public String gameDetailPost(@RequestParam(name="info")int questionId, Model model) {
        var question = gameService.gameDetailQuestion(questionId);
        var choice = gameService.gameDetailChoice(questionId);
        model.addAttribute("gameQ", question);
        model.addAttribute("gameC", choice);
        for(var choiced : choice){
            if(choiced.answer() == true) {
                model.addAttribute("answer", choiced.choiceText());
            }
        }
        return "game_detail";
    }
}
