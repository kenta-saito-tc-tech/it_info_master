package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Service.ManageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManageController {
    @Autowired
    private ManageService manageService;

    @Autowired
    private HttpSession session;

    @GetMapping("/admin_question_set")
    public String adminQuestionSet() {
        if (session.getAttribute("user") == null) { // セッションがない場合
            return "redirect:/index";
        }
        return "admin_question_set";
    }

    @GetMapping("/admin_game_set")
    public String adminGameSet() {
        if (session.getAttribute("user") == null) { // セッションがない場合
            return "redirect:/index";
        }
        return "admin_game_set";
    }
}
