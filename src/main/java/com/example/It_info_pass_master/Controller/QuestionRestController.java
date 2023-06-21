package com.example.It_info_pass_master.Controller;

import com.example.It_info_pass_master.Entity.UserAgeRecord;
import com.example.It_info_pass_master.Entity.QuestionRecord;
import com.example.It_info_pass_master.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionRestController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/api/ages")
    public List<UserAgeRecord> ages(){

        var ageList = questionService.ageFindAll();
        for(var age : ageList){
            System.out.print(age);
        }
        return ageList;
    }

    @PostMapping("/api/selectQuestion")
    public List<QuestionRecord> selectQuestion(@RequestBody String[] ageCategory) {
        System.out.println("selectQuestion");
        var ageId = Integer.parseInt(ageCategory[0]);
        var categoryId = Integer.parseInt(ageCategory[1]);
        System.out.println("年代："+ageId+"カテゴリ："+categoryId);

        var questionList = questionService.selectQuestion(ageId, categoryId);
        System.out.print(questionList);
        return questionList;
    }

    @PutMapping("/api/user_check_complete")
    public int CheckComplete(@RequestBody String[] idUserId) {
        var id = Integer.parseInt(idUserId[0]);
        var userId = Integer.parseInt(idUserId[1]);
        System.out.println();
        System.out.println("問題id："+id+"userID："+userId);

        return questionService.checkComplete(id, userId);
    }

    @PutMapping("/api/user_check_not_complete")
    public int CheckNotComplete(@RequestBody String[] idUserId) {
        var id = Integer.parseInt(idUserId[0]);
        var userId = Integer.parseInt(idUserId[1]);
        System.out.println("問題id："+id+"userID："+userId);

        return questionService.checkNotComplete(id, userId);
    }

    @GetMapping("/api/check_complete_check")
    public int complete_check_check(@RequestParam(name = "idUserId")String[] idUserId){
        var id = Integer.parseInt(idUserId[0]);
        var userId = Integer.parseInt(idUserId[1]);
        var check = questionService.checkCompleteCheck(id, userId);
        return check;
    }


}
