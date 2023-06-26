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

    @PostMapping("/api/user_check_perfect_check")
    public Integer userCheckPerfectCheck(@RequestBody String[] userAgeQuestionId) {
        System.out.println("selectQuestion");
        var userId = Integer.parseInt(userAgeQuestionId[0]);
        var ageId = Integer.parseInt(userAgeQuestionId[1]);
        var questionId = Integer.parseInt(userAgeQuestionId[2]);
        System.out.println("userId："+userId+"ageId："+ageId+"questionId:"+questionId);

        var perfectCheck = questionService.selectPerfectCheck(userId, ageId, questionId);

        System.out.println("ControllerからperfectCheck"+perfectCheck);

        return perfectCheck;
    }

    @PutMapping("/api/user_check_complete")
    public int CheckComplete(@RequestBody String[] questionUserAgeId) {
        var id = Integer.parseInt(questionUserAgeId[0]);
        var userId = Integer.parseInt(questionUserAgeId[1]);
        var ageId = Integer.parseInt(questionUserAgeId[2]);
        System.out.println();
        System.out.println("問題id："+id+"userID："+userId);

        return questionService.checkComplete(id, userId, ageId);
    }

    @PutMapping("/api/user_check_not_complete")
    public int CheckNotComplete(@RequestBody String[] questionUserAgeId) {
        var id = Integer.parseInt(questionUserAgeId[0]);
        var userId = Integer.parseInt(questionUserAgeId[1]);
        var ageId = Integer.parseInt(questionUserAgeId[2]);
        System.out.println("問題id："+id+"userID："+userId);

        return questionService.checkNotComplete(id, userId, ageId);
    }

    @GetMapping("/api/check_complete_check")
    public int complete_check_check(@RequestParam(name = "questionUserAgeId")String[] questionUserAgeId){
        var id = Integer.parseInt(questionUserAgeId[0]);
        var userId = Integer.parseInt(questionUserAgeId[1]);
        var ageId = Integer.parseInt(questionUserAgeId[2]);
        var check = questionService.checkCompleteCheck(id, userId, ageId);
        return check;
    }

    @GetMapping("/api/check_look_check")
    public int complete_look_check(@RequestParam(name = "questionUserAgeId")String[] questionUserAgeId){
        var id = Integer.parseInt(questionUserAgeId[0]);
        var userId = Integer.parseInt(questionUserAgeId[1]);
        var ageId = Integer.parseInt(questionUserAgeId[2]);
        var check = questionService.checkLookCheck(id, userId, ageId);
        return check;
    }


}
