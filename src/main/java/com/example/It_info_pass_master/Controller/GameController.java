package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.UserRecord;
import com.example.It_info_pass_master.Service.GameService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
}
