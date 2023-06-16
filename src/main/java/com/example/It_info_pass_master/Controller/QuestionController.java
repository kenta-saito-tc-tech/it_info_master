package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question_select")
        public String questionSelect(){
        return "/question_select";
    }
}
//テストプッシュ
