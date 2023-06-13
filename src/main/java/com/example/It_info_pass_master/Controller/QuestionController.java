package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
}
