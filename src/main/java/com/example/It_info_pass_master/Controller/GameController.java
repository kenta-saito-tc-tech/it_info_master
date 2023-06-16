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
//        var user = (UserRecord)session.getAttribute("user");
//        int userid = user.id();
        var list = gameService.userGameAdd(1, id);
        model.addAttribute("id", list);
        System.out.println(list);
        return "game_start";
    }
}
