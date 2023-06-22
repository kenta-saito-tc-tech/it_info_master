package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManageController {
    @Autowired
    private ManageService manageService;

    @GetMapping("/admin_question_set")
    public String adminQuestionSet() {
        return "admin_question_set";
    }
}
