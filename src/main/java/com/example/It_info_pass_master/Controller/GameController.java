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

//    // ゲットはメニュー画面で出来るからこのクラスでは行わない
//    @GetMapping("/game_main_view")
//    public String gameMain(Model model) {
//
//        return "game_main";
//    }

    @PostMapping("/game_main")
    public String gameMainPost(@RequestParam(name="age") int id, Model model) {
        // UserRecordからuseridを取得している
        var user = (UserRecord)session.getAttribute("user");
        int userid = user.id();
        var list = gameService.userGameAdd(userid, id);
        model.addAttribute("id", list.id());
        System.out.println(list.id());
        return "game_start";
    }

    @PostMapping("/game_start")
    public String gameStartPost(@RequestParam(name = "minutes")String m, @RequestParam(name = "seconds")String s, @RequestParam(name = "userGameId")String id, Model model) {
        var mI = Integer.parseInt(m);
        var sI = Integer.parseInt(s);
        var idI = Integer.parseInt(id);
        var falseSum = gameService.userGameDetial(idI);
        var addTime = falseSum.sum() * 10;
        mI += (addTime + sI) / 60;
        sI = (addTime + sI) % 60;
        model.addAttribute("falseSum", falseSum.sum());
        model.addAttribute("addTime", addTime);
        model.addAttribute("minutes", mI);
        model.addAttribute("seconds", sI);
        return "game_finish";
    }

    @GetMapping("/game_finish")
    public String gameFinishGet(Model model) {
        var time = gameService.gameScoreSelect(1).gameScore();
        var m = time / 60;
        var s = time % 60;
        model.addAttribute("minutes", m);
        model.addAttribute("seconds", s);
        System.out.println("test");
        return "game_finish";
    }
}
