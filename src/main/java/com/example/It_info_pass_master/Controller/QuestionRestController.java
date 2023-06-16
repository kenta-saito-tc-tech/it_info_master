package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.ageRecord;
import com.example.It_info_pass_master.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestionRestController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/ages")
    public List<ageRecord> ages(){

        var ageList = questionService.ageFindAll();
        for(var age : ageList){
            System.out.print(age);
        }
        return ageList;
    }
}
